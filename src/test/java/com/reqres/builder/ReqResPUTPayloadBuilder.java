package com.reqres.builder;

import com.reqres.models.ReqResNewUser;

public class ReqResPUTPayloadBuilder {

    private final String firstName, lastName, job;

    public ReqResPUTPayloadBuilder(){
        this.firstName = null;
        this.lastName = null;
        this.job = null;
    }

    public ReqResPUTPayloadBuilder(String name, String lastName, String job){
        this.firstName = name;
        this.lastName = lastName;
        this.job = job;
    }

    public ReqResPUTPayloadBuilder withFirstName(String firstName){
        return new ReqResPUTPayloadBuilder(firstName, lastName, job);
    }

    public ReqResPUTPayloadBuilder withLastName(String lastName){
        return new ReqResPUTPayloadBuilder(firstName, lastName, job);
    }

    public ReqResPUTPayloadBuilder withJob(String job){
        return new ReqResPUTPayloadBuilder(firstName, lastName, job);
    }

    public ReqResNewUser build(){
        return new ReqResNewUser(firstName, lastName, job);
    }
}