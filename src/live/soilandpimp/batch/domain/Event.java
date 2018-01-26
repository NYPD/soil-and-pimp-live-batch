package live.soilandpimp.batch.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Event {

    @JsonProperty(value = "title_for_sns")
    private String title;
    @JsonProperty(value = "live-event_nm")
    private String eventName;

    public String getEventName() {
        return eventName;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Event [title=" + title + ", eventName=" + eventName + "]";
    }

}
