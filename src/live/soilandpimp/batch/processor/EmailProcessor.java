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

public class EmailProcessor implements ItemProcessor<Event, Event> {

    private List<EmailSubscription> emailSubscriptions = new ArrayList<>();
    private Mailer mailer;
    private String webappUrl;

    public EmailProcessor(EmailRepository emailRepository, Mailer mailer, String webappUrl) {
        emailRepository.findAll().forEach(emailSubscriptions::add);
        this.mailer = mailer;
        this.webappUrl = webappUrl;
    }

    @Override
    public Event process(Event newEvent) throws Exception {

        if (emailSubscriptions == null || emailSubscriptions.isEmpty()) return null;

        boolean scheduleChange = newEvent.isScheduleChange();

        String subject = scheduleChange? "SOIL & \"PIMP\" SESSIONS schedule update" : "New SOIL & \"PIMP\" SESSIONS event";

        EmailPopulatingBuilder populatingBuilder = EmailBuilder.startingBlank()
                                                               .from("events@soilandpimp.live")
                                                               .withSubject(subject);

        String eventMarkup = buildHtmlEventMarkup(newEvent);

        for (EmailSubscription emailSubscription : emailSubscriptions) {

            String unsubscribeEmailLink = buildUnsubscribeEmailLink(emailSubscription);

            populatingBuilder.withHTMLText(eventMarkup + unsubscribeEmailLink);
            
            Email email = EmailBuilder.copying(populatingBuilder)
                                      .to(emailSubscription.getEmailAddress())
                                      .buildEmail();

            mailer.sendMail(email);
        }

        newEvent.markAsBrodcast();
        return newEvent;
    }

    private String buildHtmlEventMarkup(Event event) {

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("<h1>" + event.getName() + "</h1>");
        stringBuffer.append("<hr>");

        String memo = event.getMemo();
        boolean hasMemo = !"".equals(memo);
        if (hasMemo) stringBuffer.append("<p>" + memo + "</p>");

        stringBuffer.append("<a href=\"" + event.getEventUrl() + "\">" + event.getEventUrl() + "</a><hr>");

        stringBuffer.append("<h2>Schedule</h2>");

        for (Schedule schedule : event.getSchedules()) {

            String place = schedule.getPlace();
            String prefecture = schedule.getPrefecture();
            String enterTime = schedule.getEnterTime();
            String startTime = schedule.getStartTime();
            String call = schedule.getCall();
            String link = schedule.getLink();

            stringBuffer.append("<ul>");
            stringBuffer.append("<li>" + schedule.getDate() + "</li>");

            boolean hasVenue = !"".equals(place);
            boolean hasPrefecture = !"".equals(prefecture);
            if (hasVenue) {
                stringBuffer.append("<li><strong>Venue: </strong>" + place);
                if (hasPrefecture) stringBuffer.append(" (" + prefecture + ")");
                stringBuffer.append("</li>");
            }

            boolean hasEnterTime = !"".equals(enterTime);
            if (hasEnterTime) stringBuffer.append("<li><strong>Enter Time: </strong>" + enterTime + "</li>");

            boolean hasStartTime = !"".equals(startTime);
            if (hasStartTime) stringBuffer.append("<li><strong>Start Time: </strong>" + startTime + "</li>");

            boolean hasCall = !"".equals(call);
            if (hasCall) stringBuffer.append("<li><strong>Inqueries: </strong>" + call + "</li>");

            boolean hasLink = !"".equals(link);
            if (hasLink) stringBuffer.append("<li><a href=\"" + link + "\">" + link + "<a/></li>");

            stringBuffer.append("</ul>");
        }

        return stringBuffer.toString();

    }

    private String buildUnsubscribeEmailLink(EmailSubscription emailSubscription) {

        String emailAddress = emailSubscription.getEmailAddress();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<hr>");
        stringBuffer.append("<p>");
        stringBuffer.append("You are recieving this email because ");
        stringBuffer.append(emailAddress);
        stringBuffer.append(" is signed up to recieve SOIL & \"PIMP\" SESSIONS event notifications.");
        stringBuffer.append(" If you dont want to recieve these emails anymore you may ");
        stringBuffer.append("<a href=\"");
        stringBuffer.append(webappUrl);
        stringBuffer.append("/unsubscribe-from-email?email=");
        stringBuffer.append(emailAddress);
        stringBuffer.append("\">unsubscribe.</a>");

        return stringBuffer.toString();
    }

}
