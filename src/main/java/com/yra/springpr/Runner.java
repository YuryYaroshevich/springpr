package com.yra.springpr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yra.springpr.aop.discount.DiscountCounterAspect;
import com.yra.springpr.aop.event.EventRequestAspect;
import com.yra.springpr.aop.event.EventRequestType;
import com.yra.springpr.model.Auditorium;
import com.yra.springpr.model.Booking;
import com.yra.springpr.model.Event;
import com.yra.springpr.model.EventTimetable;
import com.yra.springpr.model.Purse;
import com.yra.springpr.model.Rating;
import com.yra.springpr.model.Ticket;
import com.yra.springpr.model.User;
import com.yra.springpr.service.AuditoriumService;
import com.yra.springpr.service.BookingService;
import com.yra.springpr.service.EventService;
import com.yra.springpr.service.UserService;


public class Runner {
	private static DateFormat dateFormatter = new SimpleDateFormat("M/dd/yyyy");
	private static DateFormat dateTimeFormatter = new SimpleDateFormat("M/dd/yyyy hh:mm:ss");
	
	public static void main(String[] args) throws ParseException {	    
		ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		UserService userService = ctx.getBean("userService", UserService.class);
		User user = new User("Batman Batmanovich", "batman@epam.com", dateFormatter.parse("03/07/1992"), new Purse(23.5));
		userService.register(user);
		
		EventService eventService = ctx.getBean("eventService", EventService.class);
		List<Date> dates = Lists.newArrayList(
				dateTimeFormatter.parse("02/05/2016 15:00:00"),
				dateTimeFormatter.parse("02/05/2016 17:00:00"),
				dateTimeFormatter.parse("02/05/2016 19:00:00"),
				dateTimeFormatter.parse("02/05/2016 21:00:00"));
		eventService.create("Survivor", dates, 10.5, Rating.MID);
		
		BookingService bookingService = ctx.getBean("bookingService", BookingService.class);
		Event event = eventService.getByName("Survivor");
		EventTimetable eventTimetable = new EventTimetable(event, dateTimeFormatter.parse("02/05/2016 19:00:00"));
		AuditoriumService auditoriumService = ctx.getBean("auditoriumService", AuditoriumService.class);
		Auditorium auditorium = auditoriumService.getAuditoriumById(1);
		eventService.assignAuditorium(eventTimetable, auditorium);
		
		Booking booking = new Booking(eventTimetable, Sets.newHashSet(19,20));
		double ticketPrice = bookingService.getTicketPrice(booking, user);
		System.out.println(ticketPrice);
		bookingService.bookTicket(user, booking);
		List<Ticket> tickets = bookingService.getTicketsForEvent(eventTimetable);
		System.out.println(tickets);
		List<Ticket> userTickets = userService.getBookedTickets(user);
		System.out.println(userTickets);
		
		System.out.println("---- AOP OUTPUT ----");
		EventRequestAspect eventRequestAspect = ctx.getBean(EventRequestAspect.class);
		System.out.println(eventRequestAspect.getCounter(EventRequestType.BY_NAME));
		System.out.println(eventRequestAspect.getCounter(EventRequestType.TICKET_PRICE));
		System.out.println(eventRequestAspect.getCounter(EventRequestType.BOOK_TICKET));
		
		DiscountCounterAspect discountCounterAspect = ctx.getBean(DiscountCounterAspect.class);
		System.out.println(discountCounterAspect.getTotalUsageCounter());
		System.out.println(discountCounterAspect.getDiscountUserUsageCounter());
		
		ctx.close();
	}
}
