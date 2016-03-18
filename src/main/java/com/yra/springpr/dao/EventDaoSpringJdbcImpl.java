package com.yra.springpr.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.yra.springpr.model.Auditorium;
import com.yra.springpr.model.Event;
import com.yra.springpr.model.EventTimetable;
import com.yra.springpr.model.Rating;
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
		/*Map<String, Object> params = new HashMap<>();
		params.put("name", event.getName());
		params.put("rating", event.getRating());
		params.put("base_price", event.getBasePrice());*/
		SqlParameterSource namedParams = new BeanPropertySqlParameterSource(event);
		jdbcTemplate.update("insert into event(name,rating,base_price) values(:name,:rating,:base_price)", namedParams);
		
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
		Map<String, Object> params = Collections.singletonMap("id", event.getId());
		jdbcTemplate.update("delete from event where event_id = :id", params);		
		jdbcTemplate.update("delete from timetable where event_id = :id", params);
	}

	@Override
	public Event getByName(String name) {
		Map<String, Object> params = Collections.singletonMap("name", name);
		return jdbcTemplate.queryForObject("select * from event where name = :name", this::mapEvent, params);
	}

	@Override
	public List<Event> getAll() {
		return jdbcTemplate.query("select * from event", this::mapEvent, new Object[] {});
	}

	@Override
	public List<Event> getForDateRange(Date from, Date to) {
		Map<String, Object> params = new HashMap<>();
		params.put("from", from);
		params.put("to", to);
		return jdbcTemplate.query(
				"select distinct event_id, name, rating, base_price from event e inner join timetable t on e.event_id = t.event_id where event_date >= :from and event_date <= :to ",
				 this::mapEvent, params);
	}

	@Override
	public List<Event> getNextEvents(Date to) {
		Map<String, Object> params = Collections.singletonMap("to", to);
		return jdbcTemplate.query("select distinct event_id, name, rating, base_price from event e inner join timetable t on e.event_id = t.event_id where event_date <= :to",
				this::mapEvent, params);
	}

	@Override
	public void assignAuditorium(EventTimetable eventTimetable,
			Auditorium auditorium) {
		Map<String, Object> params = new HashMap<>();
		params.put("event_id", eventTimetable.getEvent().getId());
		params.put("date", eventTimetable.getDate());
		params.put("auditorium_id", auditorium.getId());
		jdbcTemplate.update("update timetable set auditorium_id = :auditorium_id where event_date = date and event_id = :event_id", params);
	}
	
	private Event mapEvent(ResultSet rs, int i) throws SQLException {
		return new Event(rs.getInt("id"), rs.getString("name"), 
				Rating.valueOf(rs.getString("rating")), rs.getDouble("base_price"));
	}

}
