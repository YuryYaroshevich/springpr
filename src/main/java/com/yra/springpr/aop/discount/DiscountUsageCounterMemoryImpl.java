package com.yra.springpr.aop.discount;

import java.util.HashMap;
import java.util.Map;

import com.yra.springpr.aop.ObjectCounter;
import com.yra.springpr.model.User;
import com.yra.springpr.service.discount.DiscountStrategy;

public class DiscountUsageCounterMemoryImpl implements DiscountUsageCounter {
	private Map<User, ObjectCounter<Class<? extends DiscountStrategy>>> counter = new HashMap<>();
	
	@Override
	public void countUsage(User user, Class<? extends DiscountStrategy> clazz) {
	    ObjectCounter<Class<? extends DiscountStrategy>> userDiscountCounter = counter.get(user);
		if (userDiscountCounter == null) {
			userDiscountCounter = new ObjectCounter<Class<? extends DiscountStrategy>>();
			counter.put(user, userDiscountCounter);
		}
		userDiscountCounter.count(clazz);
	}

	@Override
	public Map<User, Map<Class<? extends DiscountStrategy>, Integer>> getCounter() {
	    Map<User, Map<Class<? extends DiscountStrategy>, Integer>> result = new HashMap<>();
	    counter.forEach((user, userDiscountCounter) -> {
	        result.put(user, userDiscountCounter.getCounter());
	    });
	    return result;
	}
}
