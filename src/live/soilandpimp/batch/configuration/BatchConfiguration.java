package live.soilandpimp.batch.configuration;

import java.util.Arrays;

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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import live.soilandpimp.batch.annotation.DevelopmentProfile;
import live.soilandpimp.batch.annotation.ProductionProfile;
import live.soilandpimp.batch.dao.DAO;
import live.soilandpimp.batch.dao.JvcJsonWebEventDao;
import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.processor.EmailProcessor;
import live.soilandpimp.batch.processor.NewEventProccessor;
import live.soilandpimp.batch.reader.NewEventReader;
import live.soilandpimp.batch.reader.SiteEventReader;
import live.soilandpimp.batch.repositories.EmailRepository;
import live.soilandpimp.batch.repositories.EventRepository;
import live.soilandpimp.batch.service.Service;
import live.soilandpimp.batch.util.AppConstants;
import live.soilandpimp.batch.util.EventContentProvidor;
import live.soilandpimp.batch.writer.EventWriter;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackageClasses = {DAO.class, Service.class})
@Import(value = {CassandraConfiguration.class, MailerConfiguration.class, LogbackConfiguration.class})
@PropertySource(value = {"classpath:resources/mailer.properties"}, ignoreResourceNotFound = true )
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JvcJsonWebEventDao jvcMusicJsonDao;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private Mailer mailer;
    @Autowired
    private Environment springEnvironment;

    @Bean
    public Job addAndEmailEventsJob() {
        return this.jobBuilderFactory.get("addAndEmailEvents")
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

        String[] activeProfiles = springEnvironment.getActiveProfiles();
        boolean isDevelopment = Arrays.stream(activeProfiles).filter(x -> AppConstants.DEV_PROFILE.equals(x))
                .findAny().orElse(null) != null;

        String webappUrl = isDevelopment? springEnvironment.getProperty("mailer.dev.webapp.domain")
                : springEnvironment.getProperty("mailer.prod.webapp.domain");

        return this.stepBuilderFactory.get("emailNewEvents")
                                      .<Event, Event>chunk(10)
                                      .reader(new NewEventReader(eventRepository))
                                      .processor(new EmailProcessor(emailRepository, mailer, webappUrl))
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

    @Bean
    @DevelopmentProfile
    @ProductionProfile
    public EventContentProvidor eventContentProvidor() {
        return new EventContentProvidor();
    }

}
