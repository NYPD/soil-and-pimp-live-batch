package live.soilandpimp.batch.key;

import java.io.Serializable;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class EventScheduleKey implements Serializable {

    private static final long serialVersionUID = 7682051233872728116L;

    @PrimaryKeyColumn(name = "event_key", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String eventKey;

    @PrimaryKeyColumn(name = "date", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String date;

    public EventScheduleKey(String eventKey, String date) {
        this.eventKey = eventKey;
        this.date = date;
    }

    // Default Accessors *********************************************
    public String getEventKey() {
        return eventKey;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "EventScheduleKey [eventKey=" + eventKey + ", date=" + date + "]";
    }

}
