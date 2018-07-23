package repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

import configuration.EmbeddedDataSourceConfiguration;
import configuration.JvcJsonWebEventDaoConfiguration;
import live.soilandpimp.batch.configuration.BatchConfiguration;
import live.soilandpimp.batch.domain.EmailSubscription;
import live.soilandpimp.batch.repositories.EmailRepository;
import live.soilandpimp.batch.util.AppConstants;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BatchConfiguration.class,
                                 EmbeddedDataSourceConfiguration.class,
                                 JvcJsonWebEventDaoConfiguration.class})
@ActiveProfiles({AppConstants.TEST_PROFILE})
@Sql(scripts = "/setup/create-soil-and-pimp-schema.sql")
@Sql(scripts = "/setup/repository/insert-email-repository-test-info.sql")
@Sql(scripts = "/setup/drop-soil-and-pimp-schema.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class EmailRepositoryTest {

    @Autowired
    private EmailRepository emailRepository;

    @Test
    public void shouldFindAllEmailSubscriptions() {

        Iterable<EmailSubscription> findAll = emailRepository.findAll();
        assertThat(((Collection<?>) findAll).size(), is(3));

    }

    @Test
    public void shouldFindAllVerifiedEmailSubscriptions() {

        Iterable<EmailSubscription> allVerifiiedEmails = emailRepository.findByVerifiedTrue();
        assertThat(((Collection<?>) allVerifiiedEmails).size(), is(2));

    }

}
