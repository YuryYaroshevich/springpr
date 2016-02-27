package com.yra.springpr.aop.event;

import java.util.HashMap;
import java.util.Map;

import com.yra.springpr.model.Event;

public class EventRequestCounter {
    private Map<Event, Integer> eventByNameCounter = new HashMap<>();

    public void countRequest(Event event) {
        Integer count = eventByNameCounter.get(event);
        if (count == null) {
            eventByNameCounter.put(event, 1);
        } else {
            eventByNameCounter.put(event, count + 1);
        }
    }

    public Map<Event, Integer> getCounter() {
        return eventByNameCounter;
    }
}
