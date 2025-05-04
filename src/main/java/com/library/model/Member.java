package com.library.model;

import java.sql.Date;

/**
 * Represents a library member
 */
public class Member {
    private int id;
    private String name;
    private Date dateRegister;
    
    // Default constructor
    public Member() {
    }
    
    // Parameterized constructor
    public Member(int id, String name, Date dateRegister) {
        this.id = id;
        this.name = name;
        this.dateRegister = dateRegister;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Date getDateRegister() {
        return dateRegister;
    }
    
    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }
    
    @Override
    public String toString() {
        return "Member{" + "id=" + id + ", name=" + name + ", dateRegister=" + dateRegister + '}';
    }
}