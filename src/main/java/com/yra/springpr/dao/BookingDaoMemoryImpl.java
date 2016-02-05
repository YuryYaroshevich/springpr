package com.yra.springpr.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.yra.springpr.model.EventTimetable;
import com.yra.springpr.model.Ticket;
import com.yra.springpr.model.User;

/*
 * user:,
 event:,
 event_timetable: event id, date, auditorium id,
 booking: user_id, event_holding_id, place_id; here event_holding_id it is foreign composite key(event id, date).
 * */

public class BookingDaoMemoryImpl implements BookingDao {
    private Map<User, Map<EventTimetable, Set<Integer>>> bookingStorage = new HashMap<>();
    
    @Override
    public void checkFreeSeats(EventTimetable eventTimetable,
            Set<Integer> seats) {
        for (Map<EventTimetable, Set<Integer>> eventsToPlaces : bookingStorage.values()) {
            Set<Integer> bookedPlaces = eventsToPlaces.get(eventTimetable);
            if (!Sets.intersection(seats, bookedPlaces).isEmpty()) {
                throw new RuntimeException("Seats are busy");
            }
        }
    }

    @Override
    public void book(EventTimetable eventTimetable, User user,
            Set<Integer> seats) {
        if (bookingStorage.containsKey(user)) {
            Map<EventTimetable, Set<Integer>> eventsToPlaces = bookingStorage.get(user);
            if (eventsToPlaces.containsKey(eventTimetable)) {
                eventsToPlaces.get(eventTimetable).addAll(seats);
            } else {
                eventsToPlaces.put(eventTimetable, seats);
            }
        } else {
            Map<EventTimetable, Set<Integer>> eventsToPlaces = new HashMap<>();
            eventsToPlaces.put(eventTimetable, seats);
            bookingStorage.put(user, eventsToPlaces);
        }
    }

    @Override
    public List<Ticket> getBooked(EventTimetable eventTimetable) {
        List<Ticket> tickets = new ArrayList<>();
        for (Map<EventTimetable, Set<Integer>> eventsToPlaces : bookingStorage.values()) {
            Set<Integer> places = eventsToPlaces.get(eventTimetable);
            if (!places.isEmpty()) {
                for (Integer placeId : places) {
                    tickets.add(new Ticket(eventTimetable, placeId));
                }
            }
        }
        return tickets;
    }

    @Override
    public List<Ticket> getBooked(User user) {
        return null;
    }
}
