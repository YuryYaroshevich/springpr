package com.yra.springpr.dao;

import java.util.HashMap;
import java.util.Map;

import com.yra.springpr.model.User;

public class UserDaoMemoryImpl implements UserDao {
    private Map<Long, User> storage = new HashMap<>();

    @Override
    public void save(User user) {
        storage.put(user.getId(), user);
    }

    @Override
    public void remove(User user) {
        storage.remove(user.getId());
    }

    @Override
    public User get(int id) {
        return storage.get(id);
    }

    @Override
    public User getByEmail(String email) {
        for (User user : storage.values()) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void update(User user) {
        // TODO Auto-generated method stub
        
    }
}
