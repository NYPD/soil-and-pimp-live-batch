package live.soilandpimp.batch.configuration;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import live.soilandpimp.batch.annotation.SoilAndPimpDataSource;
import live.soilandpimp.batch.domain.Domain;
import live.soilandpimp.batch.repositories.Repository;
import live.soilandpimp.batch.util.AppConstants;

@Configuration
@Import(value = {DatasourceConfiguration.class})
@EnableJpaRepositories(basePackageClasses = {Repository.class})
@EnableTransactionManagement
public class JpaConfiguration {

    @Autowired
    @SoilAndPimpDataSource
    private DataSource dataSource;

    @Autowired
    private Environment springEnvironment;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        List<String> profiles = Arrays.asList(springEnvironment.getActiveProfiles());
        boolean isProduction = profiles.contains(AppConstants.PRODUCTION_PROFILE);
        boolean isTest = profiles.contains(AppConstants.TEST_PROFILE);

        //        boolean isDevelopment = Arrays.stream(activeProfiles)
        //                                      .filter(x -> AppConstants.DEV_PROFILE.equals(x))
        //                                      .findAny()
        //                                      .orElse(null) != null;

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setDatabase(isTest? Database.HSQL : Database.MYSQL);
        vendorAdapter.setShowSql(isProduction? false : true);

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
