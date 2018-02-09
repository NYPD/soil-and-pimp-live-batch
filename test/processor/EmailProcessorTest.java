package processor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;

import live.soilandpimp.batch.domain.EmailSubscription;
import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.processor.EmailProcessor;
import live.soilandpimp.batch.repositories.EmailRepository;

public class EmailProcessorTest {

    private static Mailer mailer;

    @BeforeClass
    public static void setup() {
        mailer = mock(Mailer.class);
    }

    @Test
    public void shouldNotSendEmailsDueToNoSubsciptions() throws Exception {

        Event mockEvent = mock(Event.class);
        EmailRepository emailRepository = mock(EmailRepository.class);
        when(emailRepository.findAll()).thenReturn(Collections.emptyList());

        EmailProcessor emailProcessor = new EmailProcessor(emailRepository, mailer);

        Event process = emailProcessor.process(mockEvent);

        assertThat(process, is(nullValue()));

    }

    @Test
    public void shouldSendEmailAndMarkEventAsBroadcast() throws Exception {

        EmailSubscription mockEmailSubscription = mock(EmailSubscription.class);
        when(mockEmailSubscription.getEmailAddress()).thenReturn("a@a.com");

        Event mockEvent = mock(Event.class);
        when(mockEvent.isScheduleChange()).thenReturn(true);

        EmailRepository emailRepository = mock(EmailRepository.class);
        when(emailRepository.findAll()).thenReturn(new ArrayList<EmailSubscription>(
                Arrays.asList(mockEmailSubscription, mockEmailSubscription)));

        EmailProcessor emailProcessor = new EmailProcessor(emailRepository, mailer);

        emailProcessor.process(mockEvent);

        verify(mailer, times(2)).sendMail(any(Email.class));
        verify(mockEvent, times(1)).markAsBrodcast();

    }

    @AfterClass
    public static void stopCassandraEmbedded() throws IOException {

        File directory = new File("target/embeddedCassandra");

        try {
            Thread.sleep(2000);
            FileUtils.deleteDirectory(directory);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
