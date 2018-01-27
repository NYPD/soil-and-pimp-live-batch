package live.soilandpimp.batch.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Soil and Pimp Event
 * 
 * @author NYPD
 *
 */
public class Event {

    private String eventKey;

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

    // Modified Accessors *********************************************
    /**
     * Uses the {@link Event}'s name and {@link Schedule} dates to construct a unique using with the
     * help of an MD5 hashing computation. <br>
     * <br>
     * This should pretty much guarantee us a unique key due to the significant low volume of Soil
     * and "Pimp" Sessions events.
     * 
     * @return A unique {@link String} key for this event
     */
    public String getEventKey() {

        if (this.eventKey != null) return this.eventKey;

        StringBuffer stringBuffer = new StringBuffer(this.name);
        for (Schedule schedule : schedules)
            stringBuffer.append(schedule.getDate()).append(schedule.getPlace());

        // This exception should never happen, MD5 should always be present
        try {

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(stringBuffer.toString().getBytes());
            byte[] digest = messageDigest.digest();

            String eventKeyHash = DatatypeConverter.printHexBinary(digest);

            return eventKeyHash;
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }

    }

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
        return "Event [eventKey=" + getEventKey() + ", name=" + name + ", schedules=" + schedules + "]";
    }
}
