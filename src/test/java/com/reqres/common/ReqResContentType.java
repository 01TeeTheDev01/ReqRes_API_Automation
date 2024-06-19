package com.reqres.common;

import com.reqres.utils.ReqResContentTypeEnum;

import java.util.HashMap;
import java.util.Map;

public class ReqResContentType {

    private final Map<String, String> contentTypes = new HashMap<>();

    public ReqResContentType(){
        contentTypes.put("json","application/json");
        contentTypes.put("xml","application/xml");
    }

    public String getContentType(ReqResContentTypeEnum contentTypeEnum){
        switch (contentTypeEnum){
            case JSON -> {
                return String.valueOf(contentTypes.get("json"));
            }
            case XML -> {
                return String.valueOf(contentTypes.get("xml"));
            }
            default -> {
                return "Unable to determine content-type!";
            }
        }
    }
}
