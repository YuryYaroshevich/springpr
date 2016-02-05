package com.yra.springpr.dao;

import java.util.List;
import java.util.Set;

import com.yra.springpr.model.EventTimetable;
import com.yra.springpr.model.Ticket;
import com.yra.springpr.model.User;

public interface BookingDao {
    void checkFreeSeats(EventTimetable eventTimetable, Set<Integer> seats);
    
    void book(EventTimetable eventTimetable, User user, Set<Integer> seats);
    
    List<Ticket> getBooked(EventTimetable eventTimetable);

    List<Ticket> getBooked(User user);
}
