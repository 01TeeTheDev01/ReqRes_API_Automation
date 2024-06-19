package com.reqres.models;

public class ReqResUser {
    private final ReqResData reqResData;
    private final ReqResSupport reqResSupport;

    public ReqResUser(ReqResData data, ReqResSupport support){
        this.reqResData = data;
        this.reqResSupport = support;
    }

    public ReqResData getReqResData() {
        return reqResData;
    }

    public ReqResSupport getReqResSupport() {
        return reqResSupport;
    }
}