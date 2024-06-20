package com.reqres.models;

public class ReqResToken {

    private final int id;
    private final String token;

    public ReqResToken(int id, String token) {
        this.id = id;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
