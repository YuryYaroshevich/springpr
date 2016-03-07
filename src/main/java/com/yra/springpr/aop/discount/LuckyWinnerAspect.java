package com.yra.springpr.aop.discount;

import java.util.Random;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.yra.springpr.dao.BookingDao;
import com.yra.springpr.model.Booking;
import com.yra.springpr.model.User;

@Aspect
public class LuckyWinnerAspect {
    private Random luckyDeterminer;
    private BookingDao bookingDao;
    
    public LuckyWinnerAspect(BookingDao bookingDao) {
        this.luckyDeterminer = new Random();
        this.bookingDao = bookingDao;
    }
    
    @Around("execution(* com.yra.springpr.service.BookingService.bookTicket(com.yra.springpr.model.User, com.yra.springpr.model.Booking))"
            + " && args(user, booking)")
    public void sellTicketForFree(ProceedingJoinPoint joinPoint, User user, Booking booking) {
        if (luckyDeterminer.nextBoolean()) {
            bookingDao.checkFreeSeats(booking.getEventTimetable(),
                    booking.getSeats());
            bookingDao.book(booking.getEventTimetable(), user, booking.getSeats());
        } else {
            try {
                joinPoint.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
}
