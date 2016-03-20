package com.yra.springpr.service;

import java.util.List;

import com.yra.springpr.dao.TimetableDao;
import com.yra.springpr.model.Event;
import com.yra.springpr.model.EventTimetable;

public class TimetableServiceImpl implements TimetableService {
    private TimetableDao timetableDao;

    public TimetableServiceImpl(TimetableDao timetableDao) {
        this.timetableDao = timetableDao;
    }

    @Override
    public List<EventTimetable> getTimetable(Event event) {
        return timetableDao.getEventTimetables(event);
    }

}
