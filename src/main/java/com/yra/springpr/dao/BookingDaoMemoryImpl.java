package com.yra.springpr.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
 event_timetable: timetable_id, event id, date, auditorium id,
 booking: user_id, timetable_id; here event_holding_id it is foreign composite key(event id, date).
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
        return getTickets(bookingStorage.values());
    }

    @Override
    public List<Ticket> getBooked(User user) {
        Map<EventTimetable, Set<Integer>> userEventsToPlaces = bookingStorage.get(user);
        return userEventsToPlaces == null ? 
                Collections.<Ticket>emptyList() :
                    getTickets(Collections.singletonList(userEventsToPlaces));
    }
    
    private List<Ticket> getTickets(Collection<Map<EventTimetable, Set<Integer>>> eventsToPlaces) {
        List<Ticket> tickets = new ArrayList<>();
        for (Map<EventTimetable, Set<Integer>> eventToPlaces : eventsToPlaces) {
            for (EventTimetable eventTimetable : eventToPlaces.keySet()) {
                Set<Integer> places = eventToPlaces.get(eventTimetable);
                for (Integer placeId : places) {
                    tickets.add(new Ticket(eventTimetable, placeId));
                }
            }
        }
        return tickets;
    }
}
