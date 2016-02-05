package com.yra.springpr.service.discount;

import java.util.Calendar;

import com.yra.springpr.model.Booking;
import com.yra.springpr.model.User;

public class BirthdayDiscountStrategy implements DiscountStrategy {
    private double discount;
    
    public BirthdayDiscountStrategy(double discount) {
        this.discount = discount;
    }

    @Override
    public double count(User user, Booking booking) {
        Calendar today = Calendar.getInstance();
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(user.getDateOfBirth());
        
        int currentMonth = today.get(Calendar.MONTH);
        int birthdayMonth = birthday.get(Calendar.MONTH);
        if (currentMonth != birthdayMonth) {
            return 0;
        }
        
        int currentDay = today.get(Calendar.DAY_OF_MONTH);
        int birthdayDay = birthday.get(Calendar.DAY_OF_MONTH);
        if (currentDay != birthdayDay) {
            return 0;
        }
        return discount;
    }

}
