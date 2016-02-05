package com.yra.springpr.service;

import java.util.Date;
import java.util.List;

import com.yra.springpr.dao.EventDao;
import com.yra.springpr.model.Auditorium;
import com.yra.springpr.model.Event;
import com.yra.springpr.model.EventTimetable;
import com.yra.springpr.model.Rating;

public class EventService {
    private EventDao eventDao;
/*
 * create - should create Event with name, air dates and times, base price for tickets, rating (high, mid, low)
remove, getByName, getAll
getForDateRange(from, to) - returns events for specified date range (OPTIONAL)
getNextEvents(to) - returns events from now till the ‘to’ date (OPTIONAL)
assignAuditorium(event, auditorium, date) - assign auditorium for event for specific date*/
    public Event create(String name, List<Date> dates, double price, Rating rating) {
        Event event = new Event(name, rating, price);
        eventDao.save(event, dates);
        return event;
    }
    
    public void remove(Event event) {
        eventDao.remove(event);
    }
    
    public Event getByName(String name) {
        return eventDao.getByName(name);
    }
    
    public List<Event> getAll() {
        return eventDao.getAll();
    }
    
    public List<Event> getForDateRange(Date from, Date to) {
        return eventDao.getForDateRange(from, to);
    }
    
    public List<Event> getNextEvents(Date to) {
        return eventDao.getNextEvents(to);
    }
    
    public void assignAuditorium(EventTimetable eventTimetable, Auditorium auditorium) {
        eventDao.assignAuditorium(eventTimetable, auditorium);
    }
    
}
