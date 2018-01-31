package live.soilandpimp.batch.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Soil and Pimp Event
 * 
 * @author NYPD
 *
 */
@Table
public class Event {

    private String eventKey;

    private String name;
    @JsonProperty(value = "title_for_sns")
    private String socialNetworkingTitle;

    private String memo;

    @JsonProperty(value = "link_url")
    private String eventUrl;

    private String jvcUrl;

    @JsonProperty(value = "open_dt")
    private String openDate;

    List<Schedule> schedules;

    /**
     * Uses the {@link Event}'s name and jvcurl dates to construct a unique using with the help of
     * an MD5 hashing computation. <br>
     * <br>
     * This should pretty much guarantee us a unique key due to the significant low volume of Soil
     * and "Pimp" Sessions events.
     * 
     */
    public Event(@JsonProperty(value = "live-event_nm") String name, @JsonProperty(value = "url") String jvcUrl) {

        this.name = name;
        this.jvcUrl = jvcUrl;

        String tempCompositeKey = name + jvcUrl;
        // This exception should never happen, MD5 should always be present
        try {

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(tempCompositeKey.getBytes());
            byte[] digest = messageDigest.digest();

            String eventKeyHash = DatatypeConverter.printHexBinary(digest);

            this.eventKey = eventKeyHash;
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }

    }

    // Modified Accessors *********************************************

    // Default Accessors *********************************************
    public String getEventKey() {
        return eventKey;
    }
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
        return "Event [eventKey=" + eventKey + ", name=" + name + ", schedules=" + schedules + "]";
    }

}
