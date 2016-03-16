package com.yra.springpr.dao;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.yra.springpr.model.Auditorium;
import com.yra.springpr.model.Event;
import com.yra.springpr.model.EventTimetable;
import com.yra.springpr.service.AuditoriumService;

public class EventDaoSpringJdbcImpl implements EventDao {
	private JdbcTemplate jdbcTemplate;
	private AuditoriumService auditoriumService;
	
	public EventDaoSpringJdbcImpl(JdbcTemplate jdbcTemplate,
			AuditoriumService auditoriumService) {
		this.jdbcTemplate = jdbcTemplate;
		this.auditoriumService = auditoriumService;
	}

	@Override
	public Auditorium getAuditorium(EventTimetable eventTimetable) {
		
		return null;
	}

	@Override
	public void save(Event event, List<Date> dates) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Event getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getForDateRange(Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getNextEvents(Date to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assignAuditorium(EventTimetable eventTimetable,
			Auditorium auditorium) {
		// TODO Auto-generated method stub
		
	}

}
