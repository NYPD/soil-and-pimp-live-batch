package live.soilandpimp.batch.processor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;

import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.repositories.EventRepository;
import live.soilandpimp.batch.util.UtilityBelt;

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
        eventRepository.findAll().forEach(event -> pastEventsByKey.put(event.getEventKey(), event));
    }

    @Override
    public Event process(Event event) throws Exception {

        Event pastEvent = pastEventsByKey.get(event.getEventKey());
        boolean isNewEvent = pastEvent == null;
        boolean newMemo = !isNewEvent && !StringUtils.equalsIgnoreCase(event.getMemo(), pastEvent.getMemo());
        boolean newSchedules = !isNewEvent && !UtilityBelt.compareListContents(event.getSchedules(), pastEvent.getSchedules());

        if (newSchedules) event.markAsScheduledChange();

        return (isNewEvent || newMemo || newSchedules)? event : null;
    }

}
