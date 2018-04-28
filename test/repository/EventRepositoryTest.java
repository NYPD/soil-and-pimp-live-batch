package repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.List;

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
import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.repositories.EventRepository;
import live.soilandpimp.batch.util.AppConstants;

@RunWith(SpringRunner.class)
@ActiveProfiles({AppConstants.TEST_PROFILE})
@ContextConfiguration(classes = {BatchConfiguration.class,
                                 EmbeddedDataSourceConfiguration.class,
                                 JvcJsonWebEventDaoConfiguration.class})
@Sql(scripts = "/setup/create-soil-and-pimp-schema.sql")
@Sql(scripts = "/setup/repository/insert-event-repository-test-info.sql")
@Sql(scripts = "/setup/drop-soil-and-pimp-schema.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void shoulFindByBroadcastIsFalse() {

        Iterable<Event> findAll = eventRepository.findAll();
        assertThat(((Collection<?>) findAll).size(), is(2));

        List<Event> findByBroadcastIsFalse = eventRepository.findByBroadcastIsFalse();
        assertThat(findByBroadcastIsFalse.size(), is(1));

    }

}
