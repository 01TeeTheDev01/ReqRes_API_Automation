package com.reqres.builder;

import com.reqres.models.ReqResLogin;

public class ReqResRegisterBuilder {

    private final String email, password;

    public ReqResRegisterBuilder() {
        email = null;
        password = null;
    }

    public ReqResRegisterBuilder(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public ReqResRegisterBuilder withEmail(String email){
        return new ReqResRegisterBuilder(email, password);
    }

    public ReqResRegisterBuilder withPassword(String password){
        return new ReqResRegisterBuilder(email, password);
    }

    public ReqResLogin build(){
        return new ReqResLogin(email, password);
    }
}