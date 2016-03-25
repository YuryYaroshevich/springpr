package com.yra.springpr.aop.discount;

import java.util.HashMap;
import java.util.Map;

import com.yra.springpr.model.User;
import com.yra.springpr.service.discount.DiscountStrategy;

public class DiscountUsageCounterMemoryImpl implements DiscountUsageCounter {
	private Map<User, Map<Class<? extends DiscountStrategy>, Integer>> counter = new HashMap<>();
	
	@Override
	public void countUsage(User user, Class<? extends DiscountStrategy> clazz) {
		Map<Class<? extends DiscountStrategy>, Integer> userCounter = counter.get(user);
		if (userCounter == null) {
			userCounter = new HashMap<>();
		}
		Integer count = userCounter.get(clazz);
		if (count == null) {
			userCounter.put(clazz, 1);
		} else {
			userCounter.put(clazz, count + 1);
		}
	}

	@Override
	public Map<User, Map<Class<? extends DiscountStrategy>, Integer>> getCounter() {
		return counter;
	}
}
