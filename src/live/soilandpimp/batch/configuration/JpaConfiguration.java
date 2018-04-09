package live.soilandpimp.batch.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import live.soilandpimp.batch.annotation.DevelopmentProfile;
import live.soilandpimp.batch.annotation.ProductionProfile;
import live.soilandpimp.batch.annotation.SoilAndPimpDataSource;
import live.soilandpimp.batch.domain.Domain;
import live.soilandpimp.batch.repositories.Repository;

@Configuration
@DevelopmentProfile
@ProductionProfile
@PropertySource(value = {"classpath:resources/jpa.properties"})
@EnableJpaRepositories(basePackageClasses = {Repository.class})
@EnableTransactionManagement
public class JpaConfiguration {

    @Autowired
    @SoilAndPimpDataSource
    private DataSource dataSource;

    @Autowired
    private Environment environment;


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        String domainPackage = Domain.class.getPackage().getName();

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPackagesToScan(domainPackage);
        factory.setDataSource(dataSource);
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setJpaProperties(hibernateProps());
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }

    private Properties hibernateProps() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        return properties;
    }


}
