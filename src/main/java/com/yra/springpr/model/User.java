package com.yra.springpr.model;

import java.util.Date;

public class User {
    private long id;
    private final String name;
    private final String email;
    private final Date dateOfBirth;
    private double balance;

    public User(String name, String email, Date dateOfBirth, double balance) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.balance = balance;
    }
    
    public User(long id, String name, String email, Date dateOfBirth, double balance) {
        this.id = id;
    	this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.balance = balance;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(balance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result
                + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        User other = (User) obj;
        if (Double.doubleToLongBits(balance) != Double
                .doubleToLongBits(other.balance))
            return false;
        if (dateOfBirth == null) {
            if (other.dateOfBirth != null)
                return false;
        } else if (!dateOfBirth.equals(other.dateOfBirth))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email
                + ", dateOfBirth=" + dateOfBirth + ", balance=" + balance + "]";
    }    
}
