package com.yra.springpr.aop.discount;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.yra.springpr.aop.UsageCounter;
import com.yra.springpr.model.User;

@Aspect
public class DiscountCounterAspect {
    private UsageCounter<Class<?>> discountTotalUsageCounter = new UsageCounter<>();
    private Map<User, UsageCounter<Class<?>>> discountUserUsageCounter = new HashMap<>();
    
    @Around("execution(* com.yra.springpr.service.discount.DiscountStrategy.count(..))")
    public double countDiscountTypeUsage(ProceedingJoinPoint joinPoint) {
        try {
            double discount = (double) joinPoint.proceed();
            Class<?> discountStrategyClass = joinPoint.getTarget().getClass();
            if (discount > 0) {
                discountTotalUsageCounter.countObjectUsage(discountStrategyClass);                
            }    
            return discount;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    @Around("execution(* com.yra.springpr.service.discount.DiscountStrategy.count(com.yra.springpr.model.User, *))"
            + " && args(user, *)")
    public double countDiscountForSpecificUser(ProceedingJoinPoint joinPoint, User user) {
        try {
            double discount = (double) joinPoint.proceed();
            Class<?> discountStrategyClass = joinPoint.getTarget().getClass();
            if (discount > 0) {
                UsageCounter<Class<?>> discountCounterForUser = discountUserUsageCounter.get(user);
                if (discountCounterForUser == null) {
                    discountCounterForUser = new UsageCounter<Class<?>>();
                    discountUserUsageCounter.put(user, discountCounterForUser);
                }
                discountCounterForUser.countObjectUsage(discountStrategyClass);
            } 
            return discount;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    public Map<Class<?>, Integer> getTotalUsageCounter() {
        return discountTotalUsageCounter.getCounter();
    }
    
    public Map<User, UsageCounter<Class<?>>> getDiscountUserUsageCounter() {
        return discountUserUsageCounter;
    }
}