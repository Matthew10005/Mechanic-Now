package com.example.mechanicnow;

public class RegistrationHelper {

    String name, date, email, phone, password;


    public RegistrationHelper(String full_name, String date, String email_address, String phone_number, String pass_word) {
        this.name = full_name;
        this.date = date;
        this.email = email_address;
        this.phone = phone_number;
        this.password = pass_word;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) { this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
