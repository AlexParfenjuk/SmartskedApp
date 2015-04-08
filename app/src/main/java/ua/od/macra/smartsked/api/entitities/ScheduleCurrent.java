package ua.od.macra.smartsked.api.entitities;

import com.google.gson.annotations.Expose;

/**
 * Created by Roodie on 08.04.2015.
 */
public class ScheduleCurrent {
    @Expose
    private String datetime;
    @Expose
    private Integer dayOfWeek;
    @Expose
    private Integer weekOfMonth;
    @Expose
    private EventCurrent event;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getWeekOfMonth() {
        return weekOfMonth;
    }

    public void setWeekOfMonth(Integer weekOfMonth) {
        this.weekOfMonth = weekOfMonth;
    }

    public EventCurrent getEvent() {
        return event;
    }

    public void setEvent(EventCurrent event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "ScheduleCurrent{" +
                "datetime='" + datetime + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", weekOfMonth=" + weekOfMonth +
                ", event=" + event +
                '}';
    }
}
