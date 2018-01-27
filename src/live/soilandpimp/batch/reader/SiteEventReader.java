package live.soilandpimp.batch.reader;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import live.soilandpimp.batch.dao.JVCMusicJsonDao;
import live.soilandpimp.batch.domain.Event;

public class SiteEventReader implements ItemReader<Event> {

    private List<Event> events;
    private int nextEvent = 0;

    public SiteEventReader(JVCMusicJsonDao jvcMusicJsonDao) {
        this.events = jvcMusicJsonDao.getCurrentSiteEvents();
    }

    @Override
    public Event read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        // No more events to read
        if (nextEvent >= events.size()) return null;

        Event event = events.get(nextEvent);
        nextEvent++;
        return event;
    }

}
