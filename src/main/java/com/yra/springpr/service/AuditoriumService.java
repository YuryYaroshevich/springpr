package com.yra.springpr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yra.springpr.model.Auditorium;

public class AuditoriumService {
    private Map<Integer, Auditorium> auditoriums;

    public AuditoriumService(Map<Integer, Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }
    
    public List<Auditorium> getAuditoriums() {
        return new ArrayList<Auditorium>(auditoriums.values());
    }
    
    public int getSeatsNumber(int id) {
        return auditoriums.get(id).getSeatsNumber();
    }
    
    public Set<Integer> getVipSeats(int id) {
        return auditoriums.get(id).getVipSeats();
    }
    
    public Auditorium getAuditoriumById(int id) {
    	return auditoriums.get(id);
    }
    
}
