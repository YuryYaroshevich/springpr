package com.yra.springpr.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
		int auditoriumId = jdbcTemplate.queryForObject("select auditorium_id from timetable where id = ?", 
				Integer.class, eventTimetable.getEvent().getId());
		return auditoriumService.getAuditoriumById(auditoriumId);
	}

	@Override
	public void save(Event event, List<Date> dates) {
		Map<String, Object> params = new HashMap<>();
		params.put("name", event.getName());
		params.put("rating", event.getRating());
		params.put("base_price", event.getBasePrice());
		jdbcTemplate.update("insert into event(name,rating,base_price) values(:name,:rating,:base_price)", params);
		
		jdbcTemplate.batchUpdate("insert into timetable(event_id, event_date) values(?,?)",
				new BatchPreparedStatementSetter() {
					@Override
					public int getBatchSize() {
						return dates.size();
					}

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setInt(1, event.getId());
						ps.setDate(2, new java.sql.Date(dates.get(i).getTime()));
					}
		});
	}

	@Override
	public void remove(Event event) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", event.getId());
		jdbcTemplate.update("delete from event where event_id = :id", params);		
		jdbcTemplate.update("delete from timetable where event_id = :id", params);
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
