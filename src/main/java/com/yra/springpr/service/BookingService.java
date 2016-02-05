package com.yra.springpr.service;

import java.util.List;
import java.util.Set;

import com.yra.springpr.dao.BookingDao;
import com.yra.springpr.dao.EventDao;
import com.yra.springpr.model.Auditorium;
import com.yra.springpr.model.Booking;
import com.yra.springpr.model.EventTimetable;
import com.yra.springpr.model.Ticket;
import com.yra.springpr.model.User;
import com.yra.springpr.service.discount.DiscountService;

public class BookingService {
    private DiscountService discountService;
    private BookingDao bookingDao;
    private EventDao eventDao;
    
    public double getTicketPrice(Booking booking, User user) {
        EventTimetable eventTimetable = booking.getEventTimetable();
        Set<Integer> seats = booking.getSeats();
        bookingDao.checkFreeSeats(eventTimetable, seats);
        Auditorium auditorium = eventDao.getAuditorium(eventTimetable);
        double resultPrice = seats.size() * eventTimetable.getEvent().getBasePrice();
        seats.retainAll(auditorium.getVipSeats());
        resultPrice += auditorium.getVipSeatPrice() * seats.size();
        resultPrice *= 1 - (discountService.getDiscount(user, booking) / 100); 
        return resultPrice;
    }
     
    public void bookTicket(User user, Booking booking) {
        bookingDao.checkFreeSeats(booking.getEventTimetable(), booking.getSeats());
        bookingDao.book(booking.getEventTimetable(), user, booking.getSeats());
    }
    
    public List<Ticket> getTicketsForEvent(EventTimetable eventTimetable) {
        return bookingDao.getBooked(eventTimetable);
    }
}
