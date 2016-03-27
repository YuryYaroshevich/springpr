package com.yra.springpr.aop.event;

import java.util.HashMap;
import java.util.Map;

import com.yra.springpr.aop.ObjectCounter;
import com.yra.springpr.model.Event;

public class EventUsageCounterMemoryImpl implements EventUsageCounter {
	private Map<Event, ObjectCounter<EventRequestType>> counter = new HashMap<>();

	@Override
	public void countEvent(Event event, EventRequestType type) {
		ObjectCounter<EventRequestType> eventCounter = counter.get(event);
		if (eventCounter == null) {
			eventCounter = new ObjectCounter<>();
			counter.put(event, eventCounter);
		}
		eventCounter.count(type);
	}

	@Override
	public Map<Event, Map<EventRequestType, Integer>> getCounter() {
	    Map<Event, Map<EventRequestType, Integer>> result = new HashMap<>();
	    counter.forEach((event, eventCounter) -> {
	        result.put(event, eventCounter.getCounter());
	    });
		return result;
	}
}
