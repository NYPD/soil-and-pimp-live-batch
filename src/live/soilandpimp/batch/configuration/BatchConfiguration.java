package live.soilandpimp.batch.configuration;

import org.simplejavamail.mailer.Mailer;
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
import live.soilandpimp.batch.processor.EmailProcesscor;
import live.soilandpimp.batch.processor.NewEventProccessor;
import live.soilandpimp.batch.reader.NewEventReader;
import live.soilandpimp.batch.reader.SiteEventReader;
import live.soilandpimp.batch.repositories.EmailRepository;
import live.soilandpimp.batch.repositories.EventRepository;
import live.soilandpimp.batch.service.Service;
import live.soilandpimp.batch.writer.EventWriter;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackageClasses = {DAO.class, Service.class})
@Import(value = {CassandraConfiguration.class, MailerConfiguration.class, LogbackConfiguration.class})
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JvcMusicJsonDao jvcMusicJsonDao;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private Mailer mailer;

    @Bean
    public Job addNewEventsJob() {
        return this.jobBuilderFactory.get("checkForNewEvents")
                                     .start(addNewEvents())
                                     .next(emailNewEvents())
                                     .build();
    }

    @Bean
    public Step addNewEvents() {
        return this.stepBuilderFactory.get("addNewEvents")
                                      .<Event, Event>chunk(1)
                                      .reader(new SiteEventReader(jvcMusicJsonDao))
                                      .processor(new NewEventProccessor(eventRepository))
                                      .writer(new EventWriter(eventRepository))
                                      .build();
    }

    @Bean
    public Step emailNewEvents() {
        return this.stepBuilderFactory.get("emailNewEvents")
                                      .<Event, Event>chunk(10)
                                      .reader(new NewEventReader(eventRepository))
                                      .processor(new EmailProcesscor(emailRepository, mailer))
                                      .writer(new EventWriter(eventRepository))
                                      .build();
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
