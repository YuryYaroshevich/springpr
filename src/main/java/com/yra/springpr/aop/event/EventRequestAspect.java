package com.yra.springpr.aop.event;

import java.util.EnumMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.yra.springpr.model.Event;

@Aspect
public class EventRequestAspect {
    private Map<EventRequestType, EventRequestCounter> eventRequestStatistics;
    
    public EventRequestAspect() {
        eventRequestStatistics = new EnumMap<>(EventRequestType.class);
        for (EventRequestType type : EventRequestType.values()) {
            eventRequestStatistics.put(type, new EventRequestCounter());
        }
    }
    
    @Around("execution(* com.yra.springpr.service.EventService.getByName(..))")
    public Event countEventByNameRequest(ProceedingJoinPoint joinPoint) {
        return countEventRequest(joinPoint, EventRequestType.BY_NAME);
    }
    
    @Around("execution(* com.yra.springpr.service)")
    
    private Event countEventRequest(ProceedingJoinPoint joinPoint, EventRequestType type) {
        try {
            Event event = (Event) joinPoint.proceed();
            eventRequestStatistics.get(type).countRequest(event);
            return event;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } 
    }

    public Map<Event, Integer> getCounter(EventRequestType type) {
        return eventRequestStatistics.get(type).getCounter();
    }
}
