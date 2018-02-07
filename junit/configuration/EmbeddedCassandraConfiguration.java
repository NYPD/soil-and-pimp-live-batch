package configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import live.soilandpimp.batch.annotation.TestProfile;
import live.soilandpimp.batch.domain.Domain;
import live.soilandpimp.batch.repositories.Repository;
import live.soilandpimp.batch.util.AppConstants;

@Configuration
@TestProfile
@EnableCassandraRepositories(basePackageClasses = {Repository.class})
public class EmbeddedCassandraConfiguration extends AbstractCassandraConfiguration {

    private static final String SCHEDULE_TYPE = "CREATE TYPE IF NOT EXISTS soilandpimp.schedule (date date,enter_time text,start_time text,prefecture text,place text,memo text,link text);";
    private static final String EVENT_TABLE = "CREATE TABLE IF NOT EXISTS soilandpimp.events (event_key text,name text,broadcast boolean, event_url text,jvc_url text,memo text,open_date timestamp, schedule_change boolean,schedules set<frozen<schedule>>,social_networking_title text,PRIMARY KEY (event_key, name));";
    private static final String EMAIL_SUBSCRIPTIONS_TABLE = "CREATE TABLE IF NOT EXISTS  soilandpimp.email_subscriptions (email_address text PRIMARY KEY);";
    private static final String EVENT_TABLE1 = " CREATE INDEX IF NOT EXISTS  events_schedule_change_idx ON soilandpimp.events (schedule_change);";
    private static final String EVENT_TABLE_INDEX2 = "CREATE INDEX IF NOT EXISTS  events_broadcast_idx ON soilandpimp.events (broadcast);";

    @Override
    protected String getKeyspaceName() {
        return AppConstants.CASSANDRA_KEYSPACE;
    }

    //    @Override
    //    public CassandraSessionFactoryBean session() throws ClassNotFoundException {
    //
    //        Cluster cluster = Cluster.builder()
    //                                 .addContactPoints(getContactPoints())
    //                                 .build();
    //
    //        CassandraSessionFactoryBean cassandraSessionFactoryBean = new CassandraSessionFactoryBean();
    //        cassandraSessionFactoryBean.setCluster(cluster);
    //        return cassandraSessionFactoryBean;
    //    }

    @Override
    protected String getContactPoints() {
        return "127.0.0.1";
    }

    @Override
    protected int getPort() {
        return 9152;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return Arrays.asList((CreateKeyspaceSpecification.createKeyspace("soilandpimp")));
    }

    @Override
    protected List<String> getStartupScripts() {
        return Arrays.asList(SCHEDULE_TYPE, EVENT_TABLE, EMAIL_SUBSCRIPTIONS_TABLE, EVENT_TABLE1, EVENT_TABLE_INDEX2);
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.NONE;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] {Domain.class.getPackage().getName()};
    }

}
