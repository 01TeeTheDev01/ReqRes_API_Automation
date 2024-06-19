package com.reqres.models;

import com.reqres.base.ReqResBase;

import java.util.List;

public class ReqResUnknownList extends ReqResBase {

    private final List<ReqResUnknown> reqResUserCollection;

    public ReqResUnknownList (int page, int perPage, int total, int totalPages, List<ReqResUnknown> reqResUserCollection){
        super(page, perPage, total, totalPages);
        this.reqResUserCollection = reqResUserCollection;
    }

    public List<ReqResUnknown> getReqResUnknownUserCollection() {
        return reqResUserCollection;
    }
}
