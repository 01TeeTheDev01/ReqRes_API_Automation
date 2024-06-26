package com.reqres.models;

import com.reqres.base.ReqResBase;

import java.util.List;

public class ReqResListUser extends ReqResBase {

    private final List<ReqResUserData> reqResUserCollection;

    public ReqResListUser (int page, int perPage, int total, int totalPages, List<ReqResUserData> reqResUserCollection){
        super(page, perPage, total, totalPages);
        this.reqResUserCollection = reqResUserCollection;
    }

    public List<ReqResUserData> getReqResList_UserCollection() {
        return reqResUserCollection;
    }
}