package com.yra.springpr.aop;

import java.util.Map;

import com.yra.springpr.model.User;

public interface UsageCounter<T> {
	void countObjectUsage(User user, T o);
	
	Map<User, Map<T, Integer>> getCounter();
}
