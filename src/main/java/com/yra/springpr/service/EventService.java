package com.yra.springpr.service;

import java.util.Date;
import java.util.List;

import com.yra.springpr.model.Auditorium;
import com.yra.springpr.model.Event;
import com.yra.springpr.model.EventTimetable;
import com.yra.springpr.model.Rating;

public interface EventService {

    Event create(String name, List<Date> dates, double price, Rating rating);

    void remove(Event event);

    Event getByName(String name);

    List<Event> getAll();

    List<Event> getForDateRange(Date from, Date to);

    List<Event> getNextEvents(Date to);

    void assignAuditorium(EventTimetable eventTimetable, Auditorium auditorium);

}