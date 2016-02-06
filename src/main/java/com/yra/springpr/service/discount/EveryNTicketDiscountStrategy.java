package com.yra.springpr.service.discount;

import com.yra.springpr.dao.BookingDao;
import com.yra.springpr.model.Booking;
import com.yra.springpr.model.User;

public class EveryNTicketDiscountStrategy implements DiscountStrategy {
    private BookingDao bookingDao;
    private int N;
    private double discount;
    
    public EveryNTicketDiscountStrategy(BookingDao bookingDao, int N,
            double discount) {
        this.bookingDao = bookingDao;
        this.N = N;
        this.discount = discount;
    }
    
    @Override
    public double count(User user, Booking booking) {
        int alreadyBookedNum = bookingDao.getBooked(user).size();
        int fuck = (alreadyBookedNum % N) + booking.getSeats().size();
        int numOfDiscountTickets = fuck / N;
        int percentsPerTicket = 100 / booking.getSeats().size();
        return (percentsPerTicket * numOfDiscountTickets) * discount / 100;
    }
}
