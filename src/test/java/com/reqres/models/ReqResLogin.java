package com.reqres.models;

public class ReqResLogin {

    private final String email, password;

    public ReqResLogin(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}