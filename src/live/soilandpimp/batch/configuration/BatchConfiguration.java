package live.soilandpimp.batch.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import live.soilandpimp.batch.annotation.DevelopmentProfile;
import live.soilandpimp.batch.annotation.ProductionProfile;
import live.soilandpimp.batch.dao.DAO;
import live.soilandpimp.batch.service.Service;
import live.soilandpimp.batch.util.EventContentProvidor;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackageClasses = {DAO.class, Service.class})
@Import(value = {JpaConfiguration.class, JobConfiguration.class, MailerConfiguration.class, LogbackConfiguration.class})
public class BatchConfiguration {

    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

    @Bean
    @DevelopmentProfile
    @ProductionProfile
    public EventContentProvidor eventContentProvidor() {
        return new EventContentProvidor();
    }

}
