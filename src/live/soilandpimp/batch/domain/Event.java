package live.soilandpimp.batch.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Event {

    private String titleForSns;
    @JsonAlias(value = {"live-event_nm"})
    private String eventName;

    public String getEventName() {
        return eventName;
    }


    public String getTitleForSns() {
        return titleForSns;
    }

    @Override
    public String toString() {
        return "Event [titleForSns=" + titleForSns + ", eventName=" + eventName + "]";
    }

}
