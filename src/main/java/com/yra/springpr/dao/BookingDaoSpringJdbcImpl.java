package com.yra.springpr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.yra.springpr.model.Event;
import com.yra.springpr.model.EventTimetable;
import com.yra.springpr.model.Rating;
import com.yra.springpr.model.Ticket;
import com.yra.springpr.model.User;

public class BookingDaoSpringJdbcImpl implements BookingDao {
	private NamedParameterJdbcTemplate jdbcTemplate;
		
	public BookingDaoSpringJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void checkFreeSeats(EventTimetable eventTimetable, Set<Integer> seats) {
		String seatNumbers = makeCommaSeparatedList(seats);
		Map<String, Object> params = Collections.singletonMap("place_ids", seatNumbers);
		List<Integer> occupiedPlaces = jdbcTemplate.queryForList("select place_id from booking where place_id in (:place_ids)", 
			params, Integer.class);
		if (!occupiedPlaces.isEmpty()) {
			throw new RuntimeException("Places " + 	makeCommaSeparatedList(occupiedPlaces) +
					" are already occupied");
		}
	}
	
	private String makeCommaSeparatedList(Collection<?> collection) {
		return collection.stream().map(o -> o.toString()).collect(Collectors.joining(", "));
	}
    
	@Override
    public void book(EventTimetable eventTimetable, User user, Set<Integer> seats) {
	    MapSqlParameterSource[] namedParams = new MapSqlParameterSource[seats.size()];
	    Iterator<Integer> iter = seats.iterator();
	    for (int i = 0; i < namedParams.length; i++) {
	        namedParams[i] = new MapSqlParameterSource();
	        namedParams[i].addValue("user_id", user.getId());
	        namedParams[i].addValue("timetable_id", eventTimetable.getId());
	        namedParams[i].addValue("place_id", iter.next());
	    }
		jdbcTemplate.batchUpdate("insert into booking(user_id, timetable_id, place_id) values(:user_id,:timetable_id,:place_id)",
		       namedParams);
	}
    
	@Override
    public List<Ticket> getBooked(EventTimetable eventTimetable) {
		Map<String, Object> params = Collections.singletonMap("timetable_id", eventTimetable.getId());
		List<Integer> places = jdbcTemplate.queryForList("select place_id from booking where timetable_id = :timetable_id", params, Integer.class);
		return places.stream().map(place -> new Ticket(eventTimetable, place)).collect(Collectors.toList());
	}

	@Override
    public List<Ticket> getBooked(User user) {
		Map<String, Object> params = Collections.singletonMap("user_id", user.getId());
		return jdbcTemplate.query("select e.event_id,e.name,e.rating,e.base_price,t.event_date, b.place_id from booking b inner join timetable t on b.timetable_id = t.timetable_id inner join event e on t.event_id = e.event_id where b.user_id = :user_id", 
				params, this::mapTicket);
	}

	private Ticket mapTicket(ResultSet rs, int i) throws SQLException {
		Event event = new Event(rs.getInt("event_id"), rs.getString("name"), 
				Rating.valueOf(rs.getString("rating")), rs.getDouble("base_price"));
		EventTimetable eventTimetable = new EventTimetable(event, rs.getDate("event_date"));
		return new Ticket(eventTimetable, rs.getInt("place_id"));
	}
}
