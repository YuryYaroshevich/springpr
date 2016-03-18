package com.yra.springpr.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yra.springpr.model.EventTimetable;
import com.yra.springpr.model.Ticket;
import com.yra.springpr.model.User;

public class BookingDaoSpringJdbcImpl implements BookingDao {
	private JdbcTemplate jdbcTemplate;
		
	public BookingDaoSpringJdbcImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void checkFreeSeats(EventTimetable eventTimetable, Set<Integer> seats) {
		String seatNumbers = makeCommaSeparatedList(seats);
		Map<String, Object> params = new HashMap<>();
		params.put("place_ids", seatNumbers);
		List<Integer> occupiedPlaces = jdbcTemplate.queryForList("select place_id from booking where place_id in (:place_ids)", 
			Integer.class, params);
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
		List<Integer> seatList = new ArrayList<>(seats);
		jdbcTemplate.batchUpdate("insert into booking(user_id, timetable_id, place_id) values(?,?,?)", new BatchPreparedStatementSetter() {
			@Override
			public int getBatchSize() {
				return seatList.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, user.getId());
				ps.setInt(2, eventTimetable.getId());
				ps.setInt(3, seatList.get(i));
			}
		});
	}
    
	@Override
    public List<Ticket> getBooked(EventTimetable eventTimetable) {
		Map<String, Object> params = new HashMap<>();
		params.put("timetable_id", eventTimetable.getId());
		List<Integer> places = jdbcTemplate.queryForList("select place_id from booking where timetable_id = :timetable_id", Integer.class, params);
		return places.stream().map(place -> new Ticket(eventTimetable, place)).collect(Collectors.toList());
	}

	@Override
    public List<Ticket> getBooked(User user) {
		Map<String, Object> params = new HashMap<>();
		params.put("user_id", user.getId());
		jdbcTemplate.queryForList("select * from booking b inner join timetable t on b.timetable_id = t.timetable_id inner join event e on t.event_id = e.event_id where b.user_id = :user_id", params);
		return null;
	}
}
