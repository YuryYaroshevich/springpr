package com.yra.springpr.aop.event;

import java.util.HashMap;
import java.util.Map;

import com.yra.springpr.model.Event;

public class EventUsageCounterMemoryImpl implements EventUsageCounter {
	Map<Event, Map<EventRequestType, Integer>> counter = new HashMap<>();

	@Override
	public void countEvent(Event event, EventRequestType type) {
		Map<EventRequestType, Integer> eventCounter = counter.get(event);
		if (eventCounter == null) {
			eventCounter = new HashMap<>();
		}
		Integer count = eventCounter.get(type);
        if (count == null) {
        	eventCounter.put(type, 1);
        } else {
        	eventCounter.put(type, count + 1);
        }		
	}

	@Override
	public Map<Event, Map<EventRequestType, Integer>> getCounter() {
		return counter;
	}
}
