package configuration;

import org.springframework.context.annotation.Configuration;

import live.soilandpimp.batch.annotation.TestProfile;
import live.soilandpimp.batch.configuration.CassandraConfiguration;

@Configuration
@TestProfile
public class EmbeddedCassandraConfiguration extends CassandraConfiguration {

}
