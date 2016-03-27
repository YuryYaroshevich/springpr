package com.yra.springpr.aop.discount;

import java.util.HashMap;
import java.util.Map;

import com.yra.springpr.model.User;
import com.yra.springpr.service.discount.DiscountStrategy;

public class DiscountStatistics {
	private DiscountUsageCounter discountUsageCounter;
	
	public DiscountStatistics(DiscountUsageCounter discountUsageCounter) {
		this.discountUsageCounter = discountUsageCounter;
	}

	public Map<Class<? extends DiscountStrategy>, Integer> getTotalUsageCounter() {
		Map<User, Map<Class<? extends DiscountStrategy>, Integer>> counter = discountUsageCounter.getCounter();
		Map<Class<? extends DiscountStrategy>, Integer> result = new HashMap<>();		
		counter.forEach((user, discountMap) -> {
			result.putAll(discountMap);
		});
        return result;
    }
    
    public Map<User, Map<Class<? extends DiscountStrategy>, Integer>> getDiscountUserUsageCounter() {
        return discountUsageCounter.getCounter();
    }
}
