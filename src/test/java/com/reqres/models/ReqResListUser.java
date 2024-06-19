package com.reqres.models;

import com.reqres.base.ReqResBase;

import java.util.List;

public class ReqResListUser extends ReqResBase {
    private final List<ReqResData> reqResUserCollection;

    public ReqResListUser (int page, int perPage, int total, int totalPages, List<ReqResData> reqResUserCollection){
        super(page, perPage, total, totalPages);
        this.reqResUserCollection = reqResUserCollection;
    }

    public List<ReqResData> getReqResList_UserCollection() {
        return reqResUserCollection;
    }
}