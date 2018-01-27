package live.soilandpimp.batch.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import live.soilandpimp.batch.dao.DAO;
import live.soilandpimp.batch.service.Service;

@Configuration
@EnableBatchProcessing(modular = true)
@ComponentScan(basePackageClasses = {DAO.class, Service.class})
@Import(value = {DataSourceConfiguration.class})
public class BatchConfiguration {

    @Bean
    public ApplicationContextFactory siteScraperContext() {
        return new GenericApplicationContextFactory(SiteEventConfiguration.class);
    }

    @Bean
    public ApplicationContextFactory emailContext() {
        return new GenericApplicationContextFactory(EmailConfiguration.class);
    }

    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }


}
