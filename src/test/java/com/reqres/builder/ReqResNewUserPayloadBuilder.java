package com.reqres.builder;

import com.reqres.models.ReqResNewUser;

import java.util.Objects;

public class ReqResNewUserPayloadBuilder {
    private final String firstName, lastName, job;

    public ReqResNewUserPayloadBuilder(){
        this.firstName = null;
        this.lastName = null;
        this.job = null;
    }

    public ReqResNewUserPayloadBuilder(String name, String lastName, String job){
        this.firstName = name;
        this.lastName = lastName;
        this.job = job;
    }

    public ReqResNewUserPayloadBuilder withFirstName(String firstName){
        return new ReqResNewUserPayloadBuilder(firstName, lastName, job);
    }

    public ReqResNewUserPayloadBuilder withLastName(String lastName){
        return new ReqResNewUserPayloadBuilder(firstName, lastName, job);
    }

    public ReqResNewUserPayloadBuilder withJob(String job){
        return new ReqResNewUserPayloadBuilder(firstName, lastName, job);
    }

    public ReqResNewUser build(){
        return new ReqResNewUser(firstName, lastName, job);
    }
}