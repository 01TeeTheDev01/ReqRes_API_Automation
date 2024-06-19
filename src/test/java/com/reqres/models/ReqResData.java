package com.reqres.models;

public class ReqResData {
    private final String id;
    private final String email;
    private final String first_name;
    private final String last_name;
    private final String avatar;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public ReqResData(String id, String email, String firstName, String lastName, String avatar){
        this.id = id;
        this.email = email;
        this.first_name = firstName;
        this.last_name = lastName;
        this.avatar = avatar;
    }
}