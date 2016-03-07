package com.yra.springpr.model;

public class Purse {
    private double money;

    public Purse(double money) {
        this.money = money;
    }

    public double getBalance() {
        return money;
    }

    public void increase(double amount) {
        money += amount;
    }

    public void decrease(double amount) {
        if (money - amount < 0) {
            throw new RuntimeException("You haven't enough money!");
        }
        money -= amount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(money);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Purse other = (Purse) obj;
        if (Double.doubleToLongBits(money) != Double
                .doubleToLongBits(other.money))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Purse [money=" + money + "]";
    }

}
