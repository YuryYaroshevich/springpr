package com.yra.springpr.aop.event;

import java.util.Map;

import com.yra.springpr.model.Event;

public interface EventUsageCounter {
	void countEvent(Event event, EventRequestType type);
	
	Map<Event, Map<EventRequestType, Integer>> getCounter();
}
