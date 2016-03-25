package com.yra.springpr.aop.discount;

import java.util.HashMap;
import java.util.Map;

import com.yra.springpr.aop.UsageCounterMemoryImpl;
import com.yra.springpr.model.User;
import com.yra.springpr.service.discount.DiscountStrategy;

public class DiscountStatistic {
	private DiscountUsageCounter discountUsageCounter;
	
	public DiscountStatistic(DiscountUsageCounter discountUsageCounter) {
		this.discountUsageCounter = discountUsageCounter;
	}

	public Map<Class<? extends DiscountStrategy>, Integer> getTotalUsageCounter() {
		Map<User, Map<Class<? extends DiscountStrategy>, Integer>> counter = discountUsageCounter.getCounter();
		Map<Class<? extends DiscountStrategy>, Integer> result = new HashMap<>();
		counter.forEach((user, map) -> {
			
		});
        return result;
    }
    
    public Map<User, UsageCounterMemoryImpl<Class<?>>> getDiscountUserUsageCounter() {
        return null;
    }
}
