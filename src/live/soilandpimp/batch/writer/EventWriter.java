package live.soilandpimp.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import live.soilandpimp.batch.domain.Event;

public class EventWriter implements ItemWriter<Event> {

    @Override
    public void write(List<? extends Event> events) throws Exception {
        // TODO Auto-generated method stub

    }

}
