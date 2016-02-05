package com.yra.springpr.dao;

import java.util.Date;
import java.util.List;

import com.yra.springpr.model.Auditorium;
import com.yra.springpr.model.Event;
import com.yra.springpr.model.EventTimetable;

public interface EventDao {
    Auditorium getAuditorium(EventTimetable eventTimetable);

    void save(Event event, List<Date> dates);

    void remove(Event event);

    Event getByName(String name);

    List<Event> getAll();

    List<Event> getForDateRange(Date from, Date to);

    List<Event> getNextEvents(Date to);

    void assignAuditorium(EventTimetable eventTimetable, Auditorium auditorium);
}
