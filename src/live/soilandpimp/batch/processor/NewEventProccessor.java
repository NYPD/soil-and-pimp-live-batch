package live.soilandpimp.batch.processor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.repositories.EventRepository;

/**
 * Processor checking to see if the {@link Event} passed in already exists in cassandra or not. <br>
 * <br>
 * If it does exist return null, otherwise let it through.
 * 
 * @author NYPD
 *
 */
public class NewEventProccessor implements ItemProcessor<Event, Event> {

    private Map<String, Event> pastEventsByKey = new HashMap<>();

    public NewEventProccessor(EventRepository eventRepository) {

        Iterable<Event> findAll = eventRepository.findAll();

        for (Event event : findAll)
            pastEventsByKey.put(event.getEventKey(), event);
    }

    @Override
    public Event process(Event event) throws Exception {

        boolean isNewEvent = pastEventsByKey.get(event.getEventKey()) == null;
        return isNewEvent? event : null;
    }

}
