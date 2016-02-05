package com.yra.springpr.service.discount;

import com.yra.springpr.model.Booking;
import com.yra.springpr.model.User;

public interface DiscountStrategy {
    double count(User user, Booking booking);
}
