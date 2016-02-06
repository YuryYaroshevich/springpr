package com.yra.springpr.service;

import java.util.List;

import com.yra.springpr.dao.BookingDao;
import com.yra.springpr.dao.UserDao;
import com.yra.springpr.model.Ticket;
import com.yra.springpr.model.User;

public class UserService {
    private UserDao userDao;
    private BookingDao bookingDao;

    public UserService(UserDao userDao, BookingDao bookingDao) {
        this.userDao = userDao;
        this.bookingDao = bookingDao;
    }

    public void register(User user) {
        userDao.save(user);
    }

    public void remove(User user) {
        userDao.remove(user);
    }

    public User getById(int id) {
        return userDao.get(id);
    }

    public User getUserByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public List<Ticket> getBookedTickets(User user) {
        return bookingDao.getBooked(user);
    }
}
