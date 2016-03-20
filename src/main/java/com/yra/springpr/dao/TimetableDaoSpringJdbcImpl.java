package com.yra.springpr.dao;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.yra.springpr.model.Event;
import com.yra.springpr.model.EventTimetable;

public class TimetableDaoSpringJdbcImpl implements TimetableDao {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public TimetableDaoSpringJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<EventTimetable> getEventTimetables(Event event) {
        return jdbcTemplate.getJdbcOperations()
                .query("select timetable_id, event_date from timetable where event_id = ?",
                        (ResultSet rs, int i) -> {
                            return new EventTimetable(rs.getInt("timetable_id"),
                                    event, new Date(rs.getTimestamp("event_date").getTime()));
                        }, event.getId());
    }
}
