package configuration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StreamUtils;

import live.soilandpimp.batch.annotation.TestProfile;
import live.soilandpimp.batch.util.EventContentProvidor;

@Configuration
public class JvcJsonWebEventDaoConfiguration {

    @Bean
    @TestProfile
    public EventContentProvidor eventContentProvidor() throws IOException {

        EventContentProvidor contentProviderMock = mock(EventContentProvidor.class);
        InputStream soilJson = null;

        soilJson = this.getClass().getClassLoader().getResourceAsStream("resources/soil.json");
        byte[] soilJsonContent = StreamUtils.copyToByteArray(soilJson);

        when(contentProviderMock.getContent()).thenReturn(soilJsonContent);

        soilJson.close();

        return contentProviderMock;
    }
}
