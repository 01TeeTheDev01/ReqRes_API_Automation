package com.reqres.models;

public class ReqResNewUser {
    private final String name, job;

    public ReqResNewUser(String firstName, String lastName, String job){
        this.name = String.join(", ", firstName, lastName);
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}


