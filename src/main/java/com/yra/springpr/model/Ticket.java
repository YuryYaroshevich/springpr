package com.yra.springpr.model;

public class Ticket {
    private final EventTimetable eventTimetable;
    private final int placeId;

    public Ticket(EventTimetable eventTimetable, int placeId) {
        this.eventTimetable = eventTimetable;
        this.placeId = placeId;
    }

    public EventTimetable getEventTimetable() {
        return eventTimetable;
    }

    public int getPlaceId() {
        return placeId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((eventTimetable == null) ? 0 : eventTimetable.hashCode());
        result = prime * result + placeId;
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
        Ticket other = (Ticket) obj;
        if (eventTimetable == null) {
            if (other.eventTimetable != null)
                return false;
        } else if (!eventTimetable.equals(other.eventTimetable))
            return false;
        if (placeId != other.placeId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Ticket [eventTimetable=" + eventTimetable + ", placeId="
                + placeId + "]";
    }

}
