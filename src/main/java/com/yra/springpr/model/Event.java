package com.yra.springpr.model;


public class Event {
    private long id;
    private final String name;
    private final Rating rating;
    private final double basePrice;

    public Event(String name, Rating rating, double basePrice) {
        this.name = name;
        this.rating = rating;
        this.basePrice = basePrice;
    }

    public Event(long id, String name, Rating rating, double basePrice) {
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.basePrice = basePrice;
	}
    
	public long getId() {
        return id;
    }
	
	public void setId(long id) {
        this.id = id;   
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
        result = prime * result + (int) (id ^ (id >>> 32));
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
        if (id != other.id)
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

    @Override
    public String toString() {
        return "Event [id=" + id + ", name=" + name + ", rating=" + rating
                + ", basePrice=" + basePrice + "]";
    }
}
