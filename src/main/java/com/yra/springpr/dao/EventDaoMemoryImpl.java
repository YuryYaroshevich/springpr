package com.yra.springpr.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.yra.springpr.model.Auditorium;
import com.yra.springpr.model.Event;
import com.yra.springpr.model.EventTimetable;

public class EventDaoMemoryImpl implements EventDao, TimetableDao {
    private Map<Long, Event> eventStorage = new HashMap<>();
    private Map<EventTimetable, Auditorium> timetable = new HashMap<>();

    @Override
    public Auditorium getAuditorium(EventTimetable eventTimetable) {
        return timetable.get(eventTimetable);
    }

    @Override
    public void save(Event event, List<Date> dates) {
        eventStorage.put(event.getId(), event);
        for (Date date : dates) {
            EventTimetable eventTimetable = new EventTimetable(event, date);
            timetable.put(eventTimetable, null);
        }
    }

    @Override
    public void remove(Event event) {
        eventStorage.remove(event.getId());
        for (Iterator<EventTimetable> iter = timetable.keySet().iterator(); iter.hasNext();) {
            EventTimetable eventTimetable = iter.next();
            if (event.equals(eventTimetable.getEvent())) {
                iter.remove();
            }
        }
    }

    @Override
    public Event getByName(String name) {
        for (Event event : eventStorage.values()) {
            if (name.equals(event.getName())) {
                return event;
            }
        }
        return null;
    }

    @Override
    public List<Event> getAll() {
        return new ArrayList<>(eventStorage.values());
    }

    @Override
    public List<Event> getForDateRange(Date from, Date to) {
        List<Event> events = new ArrayList<>();
        for (EventTimetable eventTimetable : timetable.keySet()) {
            if (eventTimetable.getDate().after(from) && eventTimetable.getDate().before(to)) {
                events.add(eventTimetable.getEvent());
            }
        }
        return events;
    }

    @Override
    public List<Event> getNextEvents(Date to) {
        return getForDateRange(new Date(), to);
    }

    @Override
    public void assignAuditorium(EventTimetable eventTimetable,
            Auditorium auditorium) {
        timetable.put(eventTimetable, auditorium);
    }

	@Override
	public List<EventTimetable> getEventTimetables(Event event) {
		return timetable.keySet().stream().filter(eventTimetable -> {
			return eventTimetable.getEvent().equals(event);
		}).collect(Collectors.toList());
	}
}
