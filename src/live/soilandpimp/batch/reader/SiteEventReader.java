package live.soilandpimp.batch.reader;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import live.soilandpimp.batch.dao.JvcJsonWebEventDao;
import live.soilandpimp.batch.domain.Event;

public class SiteEventReader implements ItemReader<Event> {

    private List<Event> events;

    public SiteEventReader(JvcJsonWebEventDao jvcMusicJsonDao) {
        this.events = jvcMusicJsonDao.getCurrentPostedEvents();
    }

    @Override
    public Event read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (events == null || events.isEmpty()) return null;

        return events.remove(0);

    }

}