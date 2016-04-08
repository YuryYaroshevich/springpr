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
import com.yra.springpr.aop.discount.DiscountStatistics;
import com.yra.springpr.aop.event.EventRequestType;
import com.yra.springpr.aop.event.EventStatistics;
import com.yra.springpr.dao.util.DaoCleaner;
import com.yra.springpr.model.Auditorium;
import com.yra.springpr.model.Booking;
import com.yra.springpr.model.Event;
import com.yra.springpr.model.EventTimetable;
import com.yra.springpr.model.Rating;
import com.yra.springpr.model.Ticket;
import com.yra.springpr.model.User;
import com.yra.springpr.service.AuditoriumService;
import com.yra.springpr.service.BookingService;
import com.yra.springpr.service.EventService;
import com.yra.springpr.service.TimetableService;
import com.yra.springpr.service.UserService;


public class Runner {
	private static DateFormat dateFormatter = new SimpleDateFormat("M/dd/yyyy");
	private static DateFormat dateTimeFormatter = new SimpleDateFormat("M/dd/yyyy hh:mm:ss");
	
    public static void main(String[] args) throws ParseException {
    	System.setProperty("spring.profiles.active", "jdbcDbStorage"); // "inMemoryStorage" "jdbcDbStorage"
		ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		if ("jdbcDbStorage".equals(System.getProperty("spring.profiles.active"))) {
			DaoCleaner daoCleaner = ctx.getBean(DaoCleaner.class);
	        List<String> tables = Lists.newArrayList("booking", "timetable", "event_statistics", "discount_statistics", "event", "user");
	        daoCleaner.cleanTables(tables);
		}		
		
		UserService userService = ctx.getBean("userService", UserService.class);
		User user = new User("Batman Batmanovich", "batman@epam.com", dateFormatter.parse("03/07/1992"), 23.5);
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
		TimetableService timetableService = ctx.getBean(TimetableService.class);
		EventTimetable eventTimetable = null;
		for (EventTimetable et : timetableService.getTimetable(event)) {
		    if (et.getDate().equals(dateTimeFormatter.parse("02/05/2016 19:00:00"))) {
		        eventTimetable = et;
		        break;
		    }
		}
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
		EventStatistics eventStatistics = ctx.getBean(EventStatistics.class);
		System.out.println(eventStatistics.getEventUsageForRequesType(EventRequestType.BY_NAME));
		System.out.println(eventStatistics.getEventUsageForRequesType(EventRequestType.TICKET_PRICE));
		System.out.println(eventStatistics.getEventUsageForRequesType(EventRequestType.BOOK_TICKET));
		
		DiscountStatistics discountStatistics = ctx.getBean(DiscountStatistics.class);
		System.out.println(discountStatistics.getTotalUsageCounter());
		System.out.println(discountStatistics.getDiscountUserUsageCounter());
		
		ctx.close();
	}
}
