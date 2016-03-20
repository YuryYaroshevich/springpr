package com.yra.springpr.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.yra.springpr.dao.BookingDao;
import com.yra.springpr.dao.EventDao;
import com.yra.springpr.dao.UserDao;
import com.yra.springpr.model.Auditorium;
import com.yra.springpr.model.Booking;
import com.yra.springpr.model.EventTimetable;
import com.yra.springpr.model.Ticket;
import com.yra.springpr.model.User;
import com.yra.springpr.service.discount.DiscountService;

public class BookingServiceImpl implements BookingService {
    private DiscountService discountService;
    private BookingDao bookingDao;
    private EventDao eventDao;
    private UserDao userDao;

    public BookingServiceImpl(DiscountService discountService,
            BookingDao bookingDao, EventDao eventDao, UserDao userDao) {
        this.discountService = discountService;
        this.bookingDao = bookingDao;
        this.eventDao = eventDao;
        this.userDao = userDao;
    }

    @Override
    public double getTicketPrice(Booking booking, User user) {
        EventTimetable eventTimetable = booking.getEventTimetable();
        Set<Integer> seats = new HashSet<>(booking.getSeats());
        bookingDao.checkFreeSeats(eventTimetable, seats);
        Auditorium auditorium = eventDao.getAuditorium(eventTimetable);
        double resultPrice = seats.size()
                * eventTimetable.getEvent().getBasePrice();
        seats.retainAll(auditorium.getVipSeats());
        resultPrice += auditorium.getVipSeatPrice() * seats.size();
        resultPrice *= 1 - discountService.getDiscount(user, booking);
        return resultPrice;
    }

    @Override
    public void bookTicket(User user, Booking booking) {
        bookingDao.checkFreeSeats(booking.getEventTimetable(), booking.getSeats());
        user.setBalance(user.getBalance() - getTicketPrice(booking, user));
        userDao.update(user);
        bookingDao.book(booking.getEventTimetable(), user, booking.getSeats());
    }

    @Override
    public List<Ticket> getTicketsForEvent(EventTimetable eventTimetable) {
        return bookingDao.getBooked(eventTimetable);
    }
}
