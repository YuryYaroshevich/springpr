package com.yra.springpr.model;

import java.util.Date;

public class User {
    private int id;
    private final String name;
    private final String email;
    private final Date dateOfBirth;
    private Purse purse;

    public User(String name, String email, Date dateOfBirth, Purse purse) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.purse = purse;
    }
    
    public User(int id, String name, String email, Date dateOfBirth, Purse purse) {
        this.id = id;
    	this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.purse = purse;
    }

    public int getId() {
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

    public Purse getPurse() {
        return purse;
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((purse == null) ? 0 : purse.hashCode());
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
		if (purse == null) {
			if (other.purse != null)
				return false;
		} else if (!purse.equals(other.purse))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email
                + ", dateOfBirth=" + dateOfBirth + ", purse=" + purse + "]";
    }
}
