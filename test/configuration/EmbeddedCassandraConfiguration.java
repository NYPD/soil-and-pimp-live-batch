package configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.datastax.driver.core.Cluster;

import info.archinnov.achilles.embedded.CassandraEmbeddedServerBuilder;
import info.archinnov.achilles.embedded.CassandraShutDownHook;
import live.soilandpimp.batch.annotation.TestProfile;
import live.soilandpimp.batch.domain.Domain;
import live.soilandpimp.batch.repositories.Repository;
import live.soilandpimp.batch.util.AppConstants;

@Configuration
@TestProfile
@EnableCassandraRepositories(basePackageClasses = {Repository.class})
public class EmbeddedCassandraConfiguration extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return AppConstants.CASSANDRA_KEYSPACE;
    }

    @Override
    public CassandraSessionFactoryBean session() throws ClassNotFoundException {

        Cluster buildNativeCluster = CassandraEmbeddedServerBuilder.builder()
                                                                   .withKeyspaceName(AppConstants.CASSANDRA_KEYSPACE)
                                                                   .withScript("setup/soilandpimp.cql")
                                                                   .withBroadcastAddress("127.0.0.1")
                                                                   .withListenAddress("127.0.0.1")
                                                                   .withRpcAddress("127.0.0.1")
                                                                   .withBroadcastRpcAddress("127.0.0.1")
                                                                   .useUnsafeCassandraDeamon()
                                                                   .withShutdownHook(new CassandraShutDownHook())
                                                                   .buildNativeCluster();

        CassandraSessionFactoryBean cassandraSessionFactoryBean = new CassandraSessionFactoryBean();
        cassandraSessionFactoryBean.setCluster(buildNativeCluster);
        cassandraSessionFactoryBean.setKeyspaceName(AppConstants.CASSANDRA_KEYSPACE);
        cassandraSessionFactoryBean.setConverter(super.cassandraConverter());

        return cassandraSessionFactoryBean;
    }

    @Override
    protected String getContactPoints() {
        return "127.0.0.1";
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] {Domain.class.getPackage().getName()};
    }

}