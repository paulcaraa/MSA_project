package com.example.onlinemarketapp.Model;

public class Users {
    private String username, name, surname, email, password;
    private String userType, phone, address;
    private boolean pending;

    public Users() {}

    public Users(String username, String name, String surname, String email, String password, String userType, String phone, String address, boolean pending) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.pending = pending;
        this.address = address;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }
}
