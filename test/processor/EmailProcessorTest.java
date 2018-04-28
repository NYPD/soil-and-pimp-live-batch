package processor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
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

        EmailProcessor emailProcessor = new EmailProcessor(emailRepository, mailer, "localhost:8080");

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

        EmailProcessor emailProcessor = new EmailProcessor(emailRepository, mailer, "localhost:8080");

        ArgumentCaptor<Email> argument = ArgumentCaptor.forClass(Email.class);

        emailProcessor.process(mockEvent);

        verify(mailer, times(2)).sendMail(argument.capture());

        Email emailSent = argument.getValue();

        String subject = emailSent.getSubject();
        String htmlText = emailSent.getHTMLText();

        assertThat(subject, is("SOIL & \"PIMP\" SESSIONS schedule update"));
        assertThat(htmlText, is(notNullValue()));
        verify(mockEvent, times(1)).markAsBrodcast();

    }

}
