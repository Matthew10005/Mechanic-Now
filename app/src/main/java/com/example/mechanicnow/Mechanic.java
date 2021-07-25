package com.example.mechanicnow;

public class Mechanic {
    public String fullName, company, phone, email;

    public Mechanic(){

    }

    public Mechanic(String fullName, String company, String email,String phone) {
        this.fullName = fullName;
        this.company = company;
        this.email = email;
        this.phone = phone;
    }
}
