package com.yra.springpr.aop.event;

import java.util.HashMap;
import java.util.Map;

public class EventUsageCounterMemoryImpl implements EventUsageCounter {
	Map<EventRequestType, Integer> counter = new HashMap<>();

	@Override
	public void countEvent(EventRequestType type) {
		Integer count = counter.get(type);
        if (count == null) {
        	counter.put(type, 1);
        } else {
        	counter.put(type, count + 1);
        }		
	}

	@Override
	public Map<EventRequestType, Integer> getCounter() {
		return counter;
	}

}
