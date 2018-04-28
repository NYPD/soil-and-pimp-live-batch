package live.soilandpimp.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.repositories.EventRepository;

public class EventWriter implements ItemWriter<Event> {

    private EventRepository eventRepository;

    public EventWriter(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void write(List<? extends Event> events) throws Exception {
        this.eventRepository.saveAll(events);
    }

}
