package com.yra.springpr.service;

import java.util.List;

import com.yra.springpr.model.Booking;
import com.yra.springpr.model.EventTimetable;
import com.yra.springpr.model.Ticket;
import com.yra.springpr.model.User;

public interface BookingService {

    double getTicketPrice(Booking booking, User user);

    void bookTicket(User user, Booking booking);

    List<Ticket> getTicketsForEvent(EventTimetable eventTimetable);

}