package live.soilandpimp.batch.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Soil and Pimp Event
 * 
 * @author NYPD
 *
 */
public class Event {

    @JsonProperty(value = "live-event_nm")
    private String name;
    @JsonProperty(value = "title_for_sns")
    private String socialNetworkingTitle;

    private String memo;

    @JsonProperty(value = "link_url")
    private String eventUrl;
    @JsonProperty(value = "url")
    private String jvcUrl;

    @JsonProperty(value = "open_dt")
    private String openDate;

    List<Schedule> schedules;

    // Default Accessors *********************************************
    public String getName() {
        return name;
    }
    public String getSocialNetworkingTitle() {
        return socialNetworkingTitle;
    }
    public String getMemo() {
        return memo;
    }
    public String getEventUrl() {
        return eventUrl;
    }
    public String getJvcUrl() {
        return jvcUrl;
    }
    public String getOpenDate() {
        return openDate;
    }
    public List<Schedule> getSchedules() {
        return schedules;
    }


    @Override
    public String toString() {
        return "Event [name=" + name + ", schedules=" + schedules + "]";
    }
}
