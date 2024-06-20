package com.reqres.models;

public class ReqResUser {

    private final ReqResUserData reqResUserData;
    private final ReqResSupport reqResSupport;

    public ReqResUser(ReqResUserData data, ReqResSupport support){
        this.reqResUserData = data;
        this.reqResSupport = support;
    }

    public ReqResUserData getReqResData() {
        return reqResUserData;
    }

    public ReqResSupport getReqResSupport() {
        return reqResSupport;
    }
}