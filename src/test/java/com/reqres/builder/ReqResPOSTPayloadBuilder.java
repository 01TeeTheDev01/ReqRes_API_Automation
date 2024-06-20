package com.reqres.builder;

import com.reqres.models.ReqResNewUser;

public class ReqResPOSTPayloadBuilder {

    private final String firstName, lastName, job;

    public ReqResPOSTPayloadBuilder(){
        this.firstName = null;
        this.lastName = null;
        this.job = null;
    }

    public ReqResPOSTPayloadBuilder(String name, String lastName, String job){
        this.firstName = name;
        this.lastName = lastName;
        this.job = job;
    }

    public ReqResPOSTPayloadBuilder withFirstName(String firstName){
        return new ReqResPOSTPayloadBuilder(firstName, lastName, job);
    }

    public ReqResPOSTPayloadBuilder withLastName(String lastName){
        return new ReqResPOSTPayloadBuilder(firstName, lastName, job);
    }

    public ReqResPOSTPayloadBuilder withJob(String job){
        return new ReqResPOSTPayloadBuilder(firstName, lastName, job);
    }

    public ReqResNewUser build(){
        return new ReqResNewUser(firstName, lastName, job);
    }
}