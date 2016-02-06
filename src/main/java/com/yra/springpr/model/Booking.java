package com.yra.springpr.model;

import java.util.Set;

public class Booking {
    private EventTimetable eventTimetable;
    private Set<Integer> seats;

    public Booking(EventTimetable eventTimetable, Set<Integer> seats) {
        this.eventTimetable = eventTimetable;
        this.seats = seats;
    }

    public EventTimetable getEventTimetable() {
        return eventTimetable;
    }

    public void setEventTimetable(EventTimetable eventTimetable) {
        this.eventTimetable = eventTimetable;
    }

    public Set<Integer> getSeats() {
        return seats;
    }

    public void setSeats(Set<Integer> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Booking [eventTimetable=" + eventTimetable + ", seats=" + seats
                + "]";
    }

}
