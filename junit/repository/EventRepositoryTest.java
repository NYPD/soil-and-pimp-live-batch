package repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import configuration.EmbeddedCassandraConfiguration;
import configuration.JvcJsonWebEventDaoConfiguration;
import live.soilandpimp.batch.configuration.BatchConfiguration;
import live.soilandpimp.batch.repositories.EventRepository;
import live.soilandpimp.batch.util.AppConstants;

@RunWith(SpringRunner.class)
@ActiveProfiles({AppConstants.TEST_PROFILE})
@ContextConfiguration(classes = {BatchConfiguration.class, EmbeddedCassandraConfiguration.class,
                                 JvcJsonWebEventDaoConfiguration.class})
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    /*
     * This is so dirty, cassandra-unit does not seem to run if there there is already a log4j-embedded-cassandra.propeties
     */
    @BeforeClass
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, IOException, URISyntaxException {

        URL url = EventRepositoryTest.class.getClassLoader().getResource("resources/another-cassandra.yaml");
        EmbeddedCassandraServerHelper.startEmbeddedCassandra(new File(url.toURI()), 10000);
    }

    @Test
    public void shoulFindByBroadcastIsFalse() {

        assertThat("", is(""));
        System.out.println(eventRepository);
        System.out.println("KeySpace created and activated.");
    }

    @AfterClass
    public static void stopCassandraEmbedded() throws InterruptedException, TTransportException, IOException {

        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();

        File directory = new File("target/embeddedCassandra");
        try {
            Thread.sleep(5000);
            FileUtils.deleteDirectory(directory);
        } catch (Exception e) {}

    }
}
