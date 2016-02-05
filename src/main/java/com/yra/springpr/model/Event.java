package com.yra.springpr.model;

import java.util.UUID;


public class Event {
    private final String id = UUID.randomUUID().toString();
    private final String name;
    private final Rating rating;
    private final double basePrice;

    public Event(String name, Rating rating, double basePrice) {
        super();
        this.name = name;
        this.rating = rating;
        this.basePrice = basePrice;
    }

    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public Rating getRating() {
        return rating;
    }

    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(basePrice);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((rating == null) ? 0 : rating.hashCode());
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
        Event other = (Event) obj;
        if (Double.doubleToLongBits(basePrice) != Double
                .doubleToLongBits(other.basePrice))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (rating != other.rating)
            return false;
        return true;
    }
    
    
}
