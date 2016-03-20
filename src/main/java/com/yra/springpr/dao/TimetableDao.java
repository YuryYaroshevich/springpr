package com.yra.springpr.dao;

import java.util.List;

import com.yra.springpr.model.Event;
import com.yra.springpr.model.EventTimetable;

public interface TimetableDao {
    List<EventTimetable> getEventTimetables(Event event);
}
