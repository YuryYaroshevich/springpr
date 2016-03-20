package com.yra.springpr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.yra.springpr.model.Auditorium;
import com.yra.springpr.model.Event;
import com.yra.springpr.model.EventTimetable;
import com.yra.springpr.model.Rating;
import com.yra.springpr.service.AuditoriumService;

public class EventDaoSpringJdbcImpl implements EventDao {
	private NamedParameterJdbcTemplate jdbcTemplate;
	private AuditoriumService auditoriumService;
	
	public EventDaoSpringJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate,
			AuditoriumService auditoriumService) {
		this.jdbcTemplate = jdbcTemplate;
		this.auditoriumService = auditoriumService;
	}

	@Override
	public Auditorium getAuditorium(EventTimetable eventTimetable) {		
	    Map<String, Object> param = Collections.singletonMap("timetable_id", eventTimetable.getId());
		int auditoriumId = jdbcTemplate.queryForObject("select auditorium_id from timetable where timetable_id = :timetable_id", 
		        param, Integer.class);
		return auditoriumService.getAuditoriumById(auditoriumId);
	}

	@Override
	public void save(Event event, List<Date> dates) {
	    BeanPropertySqlParameterSource eventParams = new BeanPropertySqlParameterSource(event);
	    eventParams.registerSqlType("rating", Types.VARCHAR);
	    GeneratedKeyHolder eventIdHolder = new GeneratedKeyHolder();
		jdbcTemplate.update("insert into event(name,rating,base_price) values(:name,:rating,:basePrice)", eventParams, eventIdHolder);
		event.setId((Long) eventIdHolder.getKey());
		
		MapSqlParameterSource[] timetableParams = new MapSqlParameterSource[dates.size()];
		for (int i = 0; i < dates.size(); i++) {
		    timetableParams[i] = new MapSqlParameterSource();
		    timetableParams[i].addValue("event_id", event.getId());
		    timetableParams[i].addValue("event_date", new java.sql.Date(dates.get(i).getTime()), Types.TIMESTAMP);
		}
		jdbcTemplate.batchUpdate("insert into timetable(event_id, event_date) values(:event_id, :event_date)",
		       timetableParams);
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
		return jdbcTemplate.queryForObject("select * from event where name = :name", params, this::mapEvent);
	}

	@Override
	public List<Event> getAll() {
		return jdbcTemplate.query("select * from event", this::mapEvent);
	}

	@Override
	public List<Event> getForDateRange(Date from, Date to) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("from", new java.sql.Date(from.getTime()), Types.TIMESTAMP);
		params.addValue("to", new java.sql.Date(to.getTime()), Types.TIMESTAMP);
		return jdbcTemplate.query(
				"select distinct event_id, name, rating, base_price from event e inner join timetable t on e.event_id = t.event_id where event_date >= :from and event_date <= :to ",
				 params, this::mapEvent);
	}

	@Override
	public List<Event> getNextEvents(Date to) {
		Map<String, Object> params = Collections.singletonMap("to", to);
		return jdbcTemplate.query("select distinct event_id, name, rating, base_price from event e inner join timetable t on e.event_id = t.event_id where event_date <= :to",
		        params, this::mapEvent);
	}

	@Override
	public void assignAuditorium(EventTimetable eventTimetable,
			Auditorium auditorium) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("event_id", eventTimetable.getEvent().getId());
		params.addValue("date", new java.sql.Date(eventTimetable.getDate().getTime()), Types.TIMESTAMP);
		params.addValue("auditorium_id", auditorium.getId());
		jdbcTemplate.update("update timetable set auditorium_id = :auditorium_id where event_date = :date and event_id = :event_id",
		       params);
	}
	
	private Event mapEvent(ResultSet rs, int i) throws SQLException {
		return new Event(rs.getInt("event_id"), rs.getString("name"), 
				Rating.valueOf(rs.getString("rating")), rs.getDouble("base_price"));
	}
}
