package live.soilandpimp.batch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import live.soilandpimp.batch.dao.JvcMusicJsonDao;
import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.reader.SiteEventReader;
import live.soilandpimp.batch.service.Service;
import live.soilandpimp.batch.writer.EventWriter;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackageClasses = {DAO.class, Service.class})
@Import(value = {DataSourceConfiguration.class})
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JvcMusicJsonDao jvcMusicJsonDao;

    @Bean
    public Job addNewEventsJob() {
        return this.jobBuilderFactory.get("addNewEvents")
                                     .start(addNewEvents())
                                     .next(emailNewEvents())
                                     .build();
    }

    @Bean
    public Step addNewEvents() {
        return this.stepBuilderFactory.get("addNewEvents")
                                      .<Event, Event>chunk(1)
                                      .reader(new SiteEventReader(jvcMusicJsonDao))
                                      .writer(new EventWriter())
                                      .build();
    }

    @Bean
    public Step emailNewEvents() {
        return null;
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
