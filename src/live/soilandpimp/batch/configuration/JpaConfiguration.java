package live.soilandpimp.batch.configuration;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
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

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setShowSql(true);

        String domainPackage = Domain.class.getPackage().getName();

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPackagesToScan(domainPackage);
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(dataSource);

        return jpaTransactionManager;
    }

}
