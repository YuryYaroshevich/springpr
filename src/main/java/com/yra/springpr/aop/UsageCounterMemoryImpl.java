package com.yra.springpr.aop;

import java.util.HashMap;
import java.util.Map;

import com.yra.springpr.model.User;

public class UsageCounterMemoryImpl<T> implements UsageCounter<T> {
    private Map<User, Map<T, Integer>> usageCounter = new HashMap<>();

    @Override
    public void countObjectUsage(User user, T o) {
    	Map<T, Integer> userCounter = usageCounter.get(user);
    	if (userCounter == null) {
    		userCounter = new HashMap<>();
    		usageCounter.put(user, userCounter);
    	}
        Integer count = userCounter.get(o);
        if (count == null) {
        	userCounter.put(o, 1);
        } else {
        	userCounter.put(o, count + 1);
        }
    }

    @Override
    public Map<User, Map<T, Integer>> getCounter() {
        return usageCounter;
    }

    @Override
    public String toString() {
        return "UsageCounter [usageCounter=" + usageCounter + "]";
    }

}
