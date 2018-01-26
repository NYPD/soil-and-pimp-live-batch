package live.soilandpimp.batch.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableBatchProcessing(modular = true)
@Import(value = {DataSourceConfiguration.class})
public class BatchConfiguration {

    @Bean
    public ApplicationContextFactory siteScraperContext() {
        return new GenericApplicationContextFactory(SiteScraperConfiguration.class);
    }

    @Bean
    public ApplicationContextFactory emailContext() {
        return new GenericApplicationContextFactory(EmailConfiguration.class);
    }



}
