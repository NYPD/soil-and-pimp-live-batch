package live.soilandpimp.batch.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Represents a single Soil and "Pimp" sessions event
 * 
 * @author NYPD
 *
 */
@Table(value = "events")
public class Event {

    @PrimaryKey(value = "event_key")
    private String eventKey;

    private String name;

    @JsonProperty(value = "title_for_sns")
    @Column("social_networking_title")
    private String socialNetworkingTitle;

    private String memo;

    @JsonProperty(value = "link_url")
    @Column("event_url")
    private String eventUrl;

    @Column("jvc_url")
    private String jvcUrl;

    @JsonProperty(value = "open_dt")
    @Column("open_date")
    private Date openDate;

    private List<Schedule> schedules;

    // Cassandra constructor
    protected Event() {};

    /**
     * Uses the {@link Event}'s name and jvcurl dates to construct a unique event key with the help
     * of an MD5 hashing computation. <br>
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

    // Cassandra Accessors *********************************************
    @JsonSetter("open_dt")
    protected void setOpenDate(String openDate) {

    }

    public String getScheduleHash() {
        return null; // TODO finish
    }

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

    public Date getOpenDate() {
        return openDate;
    }

    public List<Schedule> geEventSchedules() {
        return schedules;
    }

    @Override
    public String toString() {
        return "Event [eventKey=" + eventKey + ", name=" + name + ", schedules=" + schedules + "]";
    }

}
