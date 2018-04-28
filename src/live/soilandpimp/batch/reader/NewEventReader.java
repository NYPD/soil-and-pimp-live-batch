package live.soilandpimp.batch.reader;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.repositories.EventRepository;

public class NewEventReader implements ItemReader<Event> {

    private List<Event> newEvents;

    public NewEventReader(EventRepository eventRepository) {
        this.newEvents = eventRepository.findByBroadcastIsFalse();
    }

    @Override
    public Event read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (newEvents == null || newEvents.isEmpty()) return null;

        return newEvents.remove(0);
    }

}
