package repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import configuration.EmbeddedCassandraConfiguration;
import live.soilandpimp.batch.configuration.BatchConfiguration;
import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.repositories.EventRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BatchConfiguration.class, EmbeddedCassandraConfiguration.class})
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;
    private static Event event = null;

    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

    public static final String KEYSPACE_ACTIVATE_QUERY = "USE testKeySpace;";
    //
    //    @Before
    //    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException {
    //
    //        System.out.println("Server Started at 127.0.0.1:9142... ");
    //        Session session = cluster.connect("soilandpimp");
    //        //session.execute(KEYSPACE_CREATION_QUERY);
    //        //session.execute(KEYSPACE_ACTIVATE_QUERY);
    //
    //        session.execute("CREATE TABLE myTable(id varchar,value varchar,PRIMARY KEY(id));");
    //
    //        session.execute("INSERT INTO myTable(id, value) values('myKey01','myValue01');");
    //        ResultSet execute = session.execute("SELECT * FROM myTable;");
    //        System.out.println(execute.iterator().next().getString("value"));
    //        System.out.println("KeySpace created and activated.");
    //        Thread.sleep(5000);
    //    }

    @Test
    public void shoulFindByBroadcastIsFalse() {
        System.out.println(eventRepository);
        System.out.println("KeySpace created and activated.");
    }

}
