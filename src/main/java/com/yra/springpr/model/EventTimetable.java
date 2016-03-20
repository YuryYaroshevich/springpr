package com.yra.springpr.model;

import java.util.Date;

public class EventTimetable {
    private int id;
    private Event event;
    private Date date;

    public EventTimetable(Event event, Date date) {
        this.event = event;
        this.date = date;
    }

    public EventTimetable(int id, Event event, Date date) {
        this.id = id;
        this.event = event;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((event == null) ? 0 : event.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EventTimetable other = (EventTimetable) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (event == null) {
            if (other.event != null)
                return false;
        } else if (!event.equals(other.event))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EventTimetable [event=" + event + ", date=" + date + "]";
    }

}
