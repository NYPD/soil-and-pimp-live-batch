package configuration;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import live.soilandpimp.batch.annotation.TestProfile;
import live.soilandpimp.batch.repositories.EmailRepository;
import live.soilandpimp.batch.repositories.EventRepository;

@Configuration
@TestProfile
public class FakeCassandraServerConfiguration {

    @Bean
    public EventRepository eventRepository() {
        return mock(EventRepository.class);
    }

    @Bean
    public EmailRepository emailRepository() {
        return mock(EmailRepository.class);
    }
}
