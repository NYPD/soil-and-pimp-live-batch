package live.soilandpimp.batch.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import live.soilandpimp.batch.annotation.DevelopmentProfile;
import live.soilandpimp.batch.annotation.ProductionProfile;
import live.soilandpimp.batch.annotation.SoilAndPimpDataSource;
import live.soilandpimp.batch.repositories.Repository;

@Configuration
@DevelopmentProfile
@ProductionProfile
@PropertySource(value = {"classpath:resources/mysql.properties"})
@EnableJpaRepositories(basePackageClasses = {Repository.class})
@EnableTransactionManagement
public class JpaConfiguration {

    @Autowired
    @SoilAndPimpDataSource
    public DataSource dataSource;


    @Bean
    public EntityManagerFactory entityManagerFactory() {

        String repositoryPackage = Repository.class.getPackage().getName();

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(repositoryPackage);
        factory.setDataSource(dataSource);
        return factory.getNativeEntityManagerFactory();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }


}
