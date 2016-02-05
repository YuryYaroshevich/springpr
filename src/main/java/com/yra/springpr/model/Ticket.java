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
}
