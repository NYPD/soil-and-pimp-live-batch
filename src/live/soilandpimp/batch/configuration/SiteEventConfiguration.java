package live.soilandpimp.batch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import live.soilandpimp.batch.dao.JVCMusicJsonDao;
import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.reader.SiteEventReader;
import live.soilandpimp.batch.writer.EventWriter;

@Configuration
public class SiteEventConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JVCMusicJsonDao jvcMusicJsonDao;

    @Bean
    public Job addNewEventsJob() {
        return this.jobBuilderFactory.get("addNewEvents").start(readSite())
                .next(getPreviousEvents())
                .next(checkForNewEvents())
                .build();
    }

    @Bean
    public Step readSite() {
        return this.stepBuilderFactory.get("readSite").<Event, Event>chunk(1)
                .reader(new SiteEventReader(jvcMusicJsonDao))
                .writer(new EventWriter())
                .build();
    }

    @Bean
    public Step getPreviousEvents() {
        return null;
    }

    @Bean
    public Step checkForNewEvents() {
        return null;
    }

}