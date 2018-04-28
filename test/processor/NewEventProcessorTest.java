package processor;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.domain.Schedule;
import live.soilandpimp.batch.processor.NewEventProccessor;
import live.soilandpimp.batch.repositories.EventRepository;

public class NewEventProcessorTest {

    @Test
    public void shouldNotProcessOldEvent() throws Exception {

        Event mockEvent = mock(Event.class);
        when(mockEvent.getEventKey()).thenReturn("1");

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findAll()).thenReturn(Arrays.asList(mockEvent));

        NewEventProccessor newEventProccessor = new NewEventProccessor(eventRepository);

        Event eventToProcess = newEventProccessor.process(mockEvent);

        assertThat(eventToProcess, is(nullValue()));

    }

    @Test
    public void shouldProcessOldEventWithNewSchedule() throws Exception {

        Schedule oldSchedule = mock(Schedule.class);

        Event oldEvent = mock(Event.class);
        when(oldEvent.getEventKey()).thenReturn("a");
        when(oldEvent.getSchedules()).thenReturn(Arrays.asList(oldSchedule));

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findAll()).thenReturn(Arrays.asList(oldEvent));

        NewEventProccessor newEventProccessor = new NewEventProccessor(eventRepository);

        Schedule newSchedule = mock(Schedule.class);

        Event oldEventPassedInWithNewSchedule = mock(Event.class);
        when(oldEventPassedInWithNewSchedule.getEventKey()).thenReturn("a");
        when(oldEventPassedInWithNewSchedule.getSchedules()).thenReturn(Arrays.asList(newSchedule));

        Event eventToProcess = newEventProccessor.process(oldEventPassedInWithNewSchedule);

        verify(eventToProcess, times(1)).markAsScheduledChange();
        assertThat(eventToProcess, is(oldEventPassedInWithNewSchedule));

    }

    @Test
    public void shouldProcessNewEvent() throws Exception {

        Event event = mock(Event.class);

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findAll()).thenReturn(Collections.emptyList());

        NewEventProccessor newEventProccessor = new NewEventProccessor(eventRepository);

        Event eventToProcess = newEventProccessor.process(event);

        assertThat(event, is(eventToProcess));

    }
}
