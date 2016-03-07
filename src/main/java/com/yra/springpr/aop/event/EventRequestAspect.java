package com.yra.springpr.aop.event;

import java.util.EnumMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.yra.springpr.aop.UsageCounter;
import com.yra.springpr.model.Booking;
import com.yra.springpr.model.Event;

@Aspect
public class EventRequestAspect {
    private Map<EventRequestType, UsageCounter<Event>> eventRequestStatistics;
    
    public EventRequestAspect() {
        eventRequestStatistics = new EnumMap<>(EventRequestType.class);
        for (EventRequestType type : EventRequestType.values()) {
            eventRequestStatistics.put(type, new UsageCounter<Event>());
        }
    }
    
    @Around("execution(* com.yra.springpr.service.EventService.getByName(..))")
    public Event countEventByNameRequest(ProceedingJoinPoint joinPoint) {
    	try {
            Event event = (Event) joinPoint.proceed();
            eventRequestStatistics.get(EventRequestType.BY_NAME).countObjectUsage(event);
            return event;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    @Before("execution(* com.yra.springpr.service.BookingService.getTicketPrice(com.yra.springpr.model.Booking, *)) && args(booking, *)")
    public void countEventPriceRequest(Booking booking) {
        countEventRequest(booking, EventRequestType.TICKET_PRICE);
    }
    
    @Before("execution(* com.yra.springpr.service.BookingService.bookTicket(*, com.yra.springpr.model.Booking)) && args(*, booking)")
    public void countEventBookingRequest(Booking booking) {
    	countEventRequest(booking, EventRequestType.BOOK_TICKET);
    }
    
    private void countEventRequest(Booking booking, EventRequestType type) {
    	Event event = booking.getEventTimetable().getEvent();
        eventRequestStatistics.get(type).countObjectUsage(event);
    }

    public Map<Event, Integer> getCounter(EventRequestType type) {
        return eventRequestStatistics.get(type).getCounter();
    }
}
