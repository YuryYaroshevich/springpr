package com.yra.springpr.aop.discount;

import java.util.Map;

import com.yra.springpr.model.User;
import com.yra.springpr.service.discount.DiscountStrategy;

public interface DiscountUsageCounter {
	void countUsage(User user, Class<? extends DiscountStrategy> clazz);
	
	Map<User, Map<Class<? extends DiscountStrategy>, Integer>> getCounter();
}
