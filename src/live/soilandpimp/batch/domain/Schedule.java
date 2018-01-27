package live.soilandpimp.batch.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Schedule for a Soil and Pimp Event
 * 
 * @author NYPD
 *
 */
public class Schedule {

    private String date;
    @JsonProperty(value = "enter_tm")
    private String enterTime;
    @JsonProperty(value = "start_tm")
    private String startTime;
    @JsonProperty(value = "prefecture_nm")
    private String prefecture;
    private String place;
    private String memo;
    @JsonProperty(value = "link_url")
    private String link;
    private String openDate;


    // Default Accessors *********************************************
    public String getDate() {
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
    public String getOpenDate() {
        return openDate;
    }

    @Override
    public String toString() {
        return "Schedule [date=" + date + ", prefecture=" + prefecture + ", place=" + place + "]";
    }

}
