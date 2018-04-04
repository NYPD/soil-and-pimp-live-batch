package repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import configuration.EmbeddedDateSourceConfiguration;
import configuration.JvcJsonWebEventDaoConfiguration;
import live.soilandpimp.batch.configuration.BatchConfiguration;
import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.repositories.EventRepository;
import live.soilandpimp.batch.util.AppConstants;

@RunWith(SpringRunner.class)
@ActiveProfiles({AppConstants.TEST_PROFILE})
@ContextConfiguration(classes = {BatchConfiguration.class,
                                 EmbeddedDateSourceConfiguration.class,
                                 JvcJsonWebEventDaoConfiguration.class})
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

    @AfterClass
    public static void stopCassandraEmbedded() throws IOException {

        File directory = new File("target/embeddedCassandra");

        try {
            Thread.sleep(2000);
            FileUtils.deleteDirectory(directory);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
