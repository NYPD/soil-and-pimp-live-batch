package live.soilandpimp.batch.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import live.soilandpimp.batch.domain.Domain;
import live.soilandpimp.batch.repositories.Repository;
import live.soilandpimp.batch.util.AppConstants;

@Configuration
@EnableCassandraRepositories(basePackageClasses = {Repository.class})
public class CassandraConfiguration extends AbstractCassandraConfiguration {

    private static final String SERVER_IP = "192.168.0.101";

    @Override
    protected String getKeyspaceName() {
        return AppConstants.CASSANDRA_KEYSPACE;
    }

    @Override
    protected String getContactPoints() {
        return SERVER_IP;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] {Domain.class.getPackage().getName()};
    }

}
