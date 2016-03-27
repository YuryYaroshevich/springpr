package com.yra.springpr.aop.event;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.yra.springpr.model.Booking;
import com.yra.springpr.model.Event;

@Aspect
public class EventRequestAspect {
    private EventUsageCounter eventUsageCounter;
    
    public EventRequestAspect(EventUsageCounter eventUsageCounter) {
    	this.eventUsageCounter = eventUsageCounter;
    }
    
    @Around("execution(* com.yra.springpr.service.EventService.getByName(..))")
    public Event countEventByNameRequest(ProceedingJoinPoint joinPoint) {
    	try {
            Event event = (Event) joinPoint.proceed();
            eventUsageCounter.countEvent(event, EventRequestType.BY_NAME);
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
    	eventUsageCounter.countEvent(event, type);
    }
}
