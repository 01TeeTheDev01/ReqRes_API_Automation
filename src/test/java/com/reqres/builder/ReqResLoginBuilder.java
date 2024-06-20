package com.reqres.builder;

import com.reqres.models.ReqResLogin;

public class ReqResLoginBuilder {

    private final String email, password;

    public ReqResLoginBuilder() {
        email = null;
        password = null;
    }

    public ReqResLoginBuilder(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public ReqResLoginBuilder withEmail(String email){
        return new ReqResLoginBuilder(email, password);
    }

    public ReqResLoginBuilder withPassword(String password){
        return new ReqResLoginBuilder(email, password);
    }

    public ReqResLogin build(){
        return new ReqResLogin(email, password);
    }
}