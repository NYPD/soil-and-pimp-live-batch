package live.soilandpimp.batch.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.datastax.driver.core.AuthProvider;

import live.soilandpimp.batch.annotation.DevelopmentProfile;
import live.soilandpimp.batch.annotation.ProductionProfile;
import live.soilandpimp.batch.domain.Domain;
import live.soilandpimp.batch.repositories.Repository;
import live.soilandpimp.batch.util.AppConstants;

@Configuration
@DevelopmentProfile
@ProductionProfile
@PropertySource(value = {"classpath:resource/cassandra.properties"})
@EnableCassandraRepositories(basePackageClasses = {Repository.class})
public class CassandraConfiguration extends AbstractCassandraConfiguration {

    @Value("${cassandra.contanct_points}")
    private String cassandraContactPoints;

    @Override
    protected String getKeyspaceName() {
        return AppConstants.CASSANDRA_KEYSPACE;
    }

    @Override
    protected String getContactPoints() {
        return cassandraContactPoints;
    }

    @Override
    protected AuthProvider getAuthProvider() {
        return super.getAuthProvider();
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] {Domain.class.getPackage().getName()};
    }

}
