package com.yra.springpr.model;

import java.util.Set;

public class Auditorium {
    private final int id;
    private final int seatsNumber;
    private final Set<Integer> vipSeats;
    private final int vipSeatPrice;

    public Auditorium(int id, int seatsNumber, Set<Integer> vipSeats,
            int vipSeatPrice) {
        this.id = id;
        this.seatsNumber = seatsNumber;
        this.vipSeats = vipSeats;
        this.vipSeatPrice = vipSeatPrice;
    }

    public int getVipSeatPrice() {
        return vipSeatPrice;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public Set<Integer> getVipSeats() {
        return vipSeats;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + seatsNumber;
        result = prime * result + vipSeatPrice;
        result = prime * result
                + ((vipSeats == null) ? 0 : vipSeats.hashCode());
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
        Auditorium other = (Auditorium) obj;
        if (id != other.id)
            return false;
        if (seatsNumber != other.seatsNumber)
            return false;
        if (vipSeatPrice != other.vipSeatPrice)
            return false;
        if (vipSeats == null) {
            if (other.vipSeats != null)
                return false;
        } else if (!vipSeats.equals(other.vipSeats))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Auditorium [id=" + id + ", seatsNumber=" + seatsNumber
                + ", vipSeats=" + vipSeats + ", vipSeatPrice=" + vipSeatPrice
                + "]";
    }

}
