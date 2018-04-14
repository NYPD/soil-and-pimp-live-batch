package configuration;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import live.soilandpimp.batch.annotation.SoilAndPimpDataSource;
import live.soilandpimp.batch.annotation.TestProfile;

@Configuration
@TestProfile
public class EmbeddedDataSourceConfiguration {

    @Bean
    @SoilAndPimpDataSource
    public DataSource getMoeSoundsDataSource() throws NamingException {

        EmbeddedDatabase datasource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();

        return datasource;
    }

}