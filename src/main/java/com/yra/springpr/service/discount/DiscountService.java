package com.yra.springpr.service.discount;

import java.util.List;

import com.yra.springpr.model.Booking;
import com.yra.springpr.model.User;

public class DiscountService {
    private List<DiscountStrategy> discountStrategies;

    public DiscountService(List<DiscountStrategy> discountStrategies) {
        this.discountStrategies = discountStrategies;
    }

    public double getDiscount(User user, Booking booking) {
        double discount = 0;
        for (DiscountStrategy discountStrategy : discountStrategies) {
            discount += discountStrategy.count(user, booking);
        }
        return discount;
    }
}
