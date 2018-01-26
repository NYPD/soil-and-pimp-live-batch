package live.soilandpimp.batch.reader;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import live.soilandpimp.batch.domain.Event;

public class SiteEventReader implements ItemReader<Event> {

    private List<Event> events;
    private int nextEvent = 0;

    public SiteEventReader() {
        initialize();
    }

    @Override
    public Event read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (nextEvent >= events.size()) return null;

        Event event = events.get(nextEvent);
        nextEvent++;
        return event;
    }

    private void initialize() {

        try {
            Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
