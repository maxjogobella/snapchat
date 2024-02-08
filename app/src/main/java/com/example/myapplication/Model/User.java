package com.example.myapplication.Model;

public class User {

    private String id;
    private String age;
    private String userName;
    private boolean status;

    public User(String id, String age, String userName, boolean status) {
        this.id = id;
        this.age = age;
        this.userName = userName;
        this.status = status;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public String getAge() {
        return age;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isStatus() {
        return status;
    }
}
