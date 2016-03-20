package com.yra.springpr.dao;

import com.yra.springpr.model.User;

public interface UserDao {
    void save(User user);

    void remove(User user);

    User get(int id);

    User getByEmail(String email);
    
    void update(User user);
}
