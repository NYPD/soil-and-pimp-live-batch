package live.soilandpimp.batch.processor;

import java.util.ArrayList;
import java.util.List;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.email.EmailPopulatingBuilder;
import org.simplejavamail.mailer.Mailer;
import org.springframework.batch.item.ItemProcessor;

import live.soilandpimp.batch.domain.EmailSubscription;
import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.domain.Schedule;
import live.soilandpimp.batch.repositories.EmailRepository;

public class EmailProcesscor implements ItemProcessor<Event, Event> {

    private List<EmailSubscription> emailSubscriptions = new ArrayList<>();
    private Mailer mailer;

    public EmailProcesscor(EmailRepository emailRepository, Mailer mailer) {
        emailRepository.findAll().forEach(emailSubscriptions::add);
        this.mailer = mailer;
    }

    @Override
    public Event process(Event newEvent) throws Exception {

        if (emailSubscriptions == null || emailSubscriptions.isEmpty()) return null;

        EmailPopulatingBuilder populatingBuilder = EmailBuilder.startingBlank()
                                                               .from("events@soilandpimp.live")
                                                               .withSubject("New SOIL & \"PIMP\" SESSIONS event");

        for (Schedule schedule : newEvent.getSchedules())
            populatingBuilder.appendTextHTML("<p>" + schedule.getPlace() + "</p>");

        for (EmailSubscription emailSubscription : emailSubscriptions) {

            Email email = EmailBuilder.copying(populatingBuilder)
                                      .to(emailSubscription.getEmailAddress())
                                      .buildEmail();

            mailer.sendMail(email);
        }

        newEvent.markAsBrodcast();
        return newEvent;
    }

}
