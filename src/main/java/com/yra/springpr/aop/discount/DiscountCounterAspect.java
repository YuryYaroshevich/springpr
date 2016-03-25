package com.yra.springpr.aop.discount;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.yra.springpr.model.User;
import com.yra.springpr.service.discount.DiscountStrategy;

@Aspect
public class DiscountCounterAspect {    
    private DiscountUsageCounter discountUsageCounter;
    
    public DiscountCounterAspect(DiscountUsageCounter discountUsageCounter) {
    	this.discountUsageCounter = discountUsageCounter;
    }
   
    @Around("execution(* com.yra.springpr.service.discount.DiscountStrategy.count(com.yra.springpr.model.User, *))"
            + " && args(user, *)")
    public double countDiscountForSpecificUser(ProceedingJoinPoint joinPoint, User user) {
        try {
            double discount = (double) joinPoint.proceed();
            Class<? extends DiscountStrategy> discountStrategyClass = (Class<? extends DiscountStrategy>) joinPoint.getTarget().getClass();
            if (discount > 0) {
            	discountUsageCounter.countUsage(user, discountStrategyClass);
            }
            return discount;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
