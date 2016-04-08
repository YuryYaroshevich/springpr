package com.yra.springpr.aop.event;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.yra.springpr.model.Event;
import com.yra.springpr.model.Rating;

public class EventUsageCounterSpringJdbcImpl implements EventUsageCounter {
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    public EventUsageCounterSpringJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void countEvent(Event event, EventRequestType type) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("event_id", event.getId());
        params.addValue("request_type", type.toString());
        jdbcTemplate.update("call add_to_event_statistics(:event_id, :request_type)", params);
    }

    @Override
    public Map<Event, Map<EventRequestType, Integer>> getCounter() {
        return jdbcTemplate.query("select e.event_id, name, rating, base_price, request_type, count from event_statistics es inner join event e on e.event_id = es.event_id",
                (ResultSet rs) -> {
                    Map<Event, Map<EventRequestType, Integer>> result = new HashMap<>();
                    while (rs.next()) {
                        Event event = new Event(rs.getLong("event_id"), rs.getString("name"),
                                Rating.valueOf(rs.getString("rating")), rs.getDouble("base_price"));
                        Map<EventRequestType, Integer> eventCounter = result.get(event);
                        if (eventCounter == null) {
                            eventCounter = new HashMap<>();
                            result.put(event, eventCounter);
                        }
                        eventCounter.put(EventRequestType.valueOf(rs.getString("request_type")), rs.getInt("count"));
                    }
                    return result;            
                });
    }

}
