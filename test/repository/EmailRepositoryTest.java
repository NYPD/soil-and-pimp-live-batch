package repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

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
import live.soilandpimp.batch.domain.EmailSubscription;
import live.soilandpimp.batch.repositories.EmailRepository;
import live.soilandpimp.batch.util.AppConstants;

@RunWith(SpringRunner.class)
@ActiveProfiles({AppConstants.TEST_PROFILE})
@ContextConfiguration(classes = {BatchConfiguration.class,
                                 EmbeddedDateSourceConfiguration.class,
                                 JvcJsonWebEventDaoConfiguration.class})
public class EmailRepositoryTest {

    @Autowired
    private EmailRepository emailRepository;

    @Test
    public void shoulFindAllEmailSubscriptions() {


        Iterable<EmailSubscription> findAll = emailRepository.findAll();
        assertThat(((Collection<?>) findAll).size(), is(3));

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
