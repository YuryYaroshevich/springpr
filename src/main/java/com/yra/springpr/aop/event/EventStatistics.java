package com.yra.springpr.aop.event;

import java.util.HashMap;
import java.util.Map;

import com.yra.springpr.model.Event;

public class EventStatistics {
	private EventUsageCounter eventUsageCounter;

	public EventStatistics(EventUsageCounter eventUsageCounter) {
		this.eventUsageCounter = eventUsageCounter;
	}
	
	public Map<Event, Map<EventRequestType, Integer>> getAll() {
		return eventUsageCounter.getCounter();
	}
	
	public Map<Event, Integer> getEventUsageForRequesType(EventRequestType requestType) {
		Map<Event, Map<EventRequestType, Integer>> eventsStat = eventUsageCounter.getCounter();
		Map<Event, Integer> result = new HashMap<>();
		eventsStat.forEach((event, map) -> {
			Integer count = map.get(requestType);
			if (count != null) {
				result.put(event, count);
			}
		});
		return result;
	}
}
