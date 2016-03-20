package com.yra.springpr.service;

import java.util.List;

import com.yra.springpr.model.Event;
import com.yra.springpr.model.EventTimetable;

public interface TimetableService {
    List<EventTimetable> getTimetable(Event event);
}
