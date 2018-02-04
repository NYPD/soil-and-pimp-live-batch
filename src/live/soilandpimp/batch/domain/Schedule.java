package live.soilandpimp.batch.domain;

import java.time.LocalDate;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Represents a single performance in a Soil and "Pimp" Sessions {@link Event}
 * 
 * @author NYPD
 *
 */
@UserDefinedType("schedule")
public class Schedule {

    private LocalDate date;
    @JsonProperty(value = "enter_tm")
    @Column("enter_time")
    private String enterTime;
    @JsonProperty(value = "start_tm")
    @Column("start_time")
    private String startTime;
    @JsonProperty(value = "prefecture_nm")
    private String prefecture;
    private String place;
    private String memo;
    @JsonProperty(value = "link_url")
    private String link;

    // Modified Accessors *********************************************
    @JsonSetter
    protected void setDate(String date) {
        this.date = LocalDate.parse(date);
    }

    // Default Accessors *********************************************
    public LocalDate getDate() {
        return date;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public String getPlace() {
        return place;
    }

    public String getMemo() {
        return memo;
    }

    public String getLink() {
        return link;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null)? 0 : date.hashCode());
        result = prime * result + ((enterTime == null)? 0 : enterTime.hashCode());
        result = prime * result + ((link == null)? 0 : link.hashCode());
        result = prime * result + ((memo == null)? 0 : memo.hashCode());
        result = prime * result + ((place == null)? 0 : place.hashCode());
        result = prime * result + ((prefecture == null)? 0 : prefecture.hashCode());
        result = prime * result + ((startTime == null)? 0 : startTime.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Schedule [startTime=" + startTime + ", prefecture=" + prefecture + ", place=" + place + "]";
    }

}
