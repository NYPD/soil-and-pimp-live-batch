package live.soilandpimp.batch.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Represents a single Soil and "Pimp" sessions event
 * 
 * @author NYPD
 *
 */
@Entity
@Table(name = "events")
public class Event {

    @Id
    @Column(name = "event_key")
    private String eventKey;

    private String name;

    @JsonProperty(value = "title_for_sns")
    @Column(name = "social_networking_title")
    private String socialNetworkingTitle;

    private String memo;

    @JsonProperty(value = "link_url")
    @Column(name = "event_url")
    private String eventUrl;

    @JsonProperty(value = "url")
    @Column(name = "jvc_url")
    private String jvcUrl;

    @Column(name = "open_date")
    private LocalDateTime openDate;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "event_key", nullable = false)
    private List<Schedule> schedules;

    @Column(name = "schedule_change")
    private boolean scheduleChange;
    private boolean broadcast;

    // Cassandra constructor
    protected Event() {};

    /**
     * Uses the {@link Event}'s name and jvcurl dates to construct a unique event key with the help of an MD5 hashing
     * computation. <br>
     * <br>
     * This should pretty much guarantee us a unique key due to the significant low volume of Soil and "Pimp" Sessions
     * events.
     * 
     * Ms. Jackson only constructer
     */
    protected Event(@JsonProperty(value = "live-event_nm") String name) {

        if (name == null) throw new IllegalArgumentException("name can not be null");

        this.name = name;

        // This exception should never happen, MD5 should always be present
        try {

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(this.name.getBytes());
            byte[] digest = messageDigest.digest();

            String eventKeyHash = DatatypeConverter.printHexBinary(digest);

            eventKey = eventKeyHash;
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }

    }

    // Modified Accessors *********************************************
    @JsonSetter("open_dt")
    protected void setOpenDate(String openDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.openDate = LocalDateTime.parse(openDate, formatter);
    }

    /*
     * We set broadcast back to false since due to schedule change, we need to re-broadcast this event to users
     */
    public void markAsScheduledChange() {
        scheduleChange = true;
        broadcast = false;
    }

    public void markAsBrodcast() {
        broadcast = true;
        scheduleChange = false;
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

    public LocalDateTime getOpenDate() {
        return openDate;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public boolean isScheduleChange() {
        return scheduleChange;
    }

    public boolean isBroadcast() {
        return broadcast;
    }

    @Override
    public String toString() {
        return "Event [eventKey=" + eventKey + ", name=" + name + ", schedules=" + schedules + "]";
    }

}
