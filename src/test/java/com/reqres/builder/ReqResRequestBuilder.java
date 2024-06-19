package com.reqres.builder;

import com.reqres.common.ReqResBasePath;
import com.reqres.common.ReqResContentType;
import com.reqres.reponses.ReqResPostResponse;
import com.reqres.utils.ReqResContentTypeEnum;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ReqResRequestBuilder {
    private final ReqResContentType reqResContentType;

    public ReqResRequestBuilder(){
        reqResContentType = new ReqResContentType();
    }

    public Response createNewUserRequest(String firstName, String lastName, String job){
        var newUserBuilder = new ReqResNewUserPayloadBuilder();

        var newUserBody = newUserBuilder.withFirstName(firstName)
                                        .withLastName(lastName)
                                        .withJob(job)
                                        .build();

        if(newUserBody == null)
            return null;

        var json = reqResContentType.getContentType(ReqResContentTypeEnum.JSON);

        var requestResponse = given()
                .when()
                .contentType(json)
                .body(newUserBody)
                .post(String.format("%s/api/users", ReqResBasePath.reqResBasePath))
                .then()
                .log()
                .body()
                .extract()
                .response();

        ReqResPostResponse.id = requestResponse.jsonPath().getString("id");
        ReqResPostResponse.name = requestResponse.jsonPath().getString("name");
        ReqResPostResponse.job = requestResponse.jsonPath().getString("job");
        ReqResPostResponse.createdAt = requestResponse.jsonPath().getString("createdAt");

        return requestResponse;
    }

    public Response getUserByIdRequest(String id){
        return given()
                .when()
                .get(String.format("%s/api/users/%s", ReqResBasePath.reqResBasePath, id))
                .then()
                .log()
                .body()
                .extract()
                .response();
    }

    public Response getListUsers(int pageNum){
        return given()
                .when()
                .get(String.format("%s/api/users?page=%s", ReqResBasePath.reqResBasePath, pageNum))
                .then()
                .log()
                .body()
                .extract()
                .response();
    }

    public Response getListUsers(){
        return given()
                .when()
                .get(String.format("%s/api/unknown", ReqResBasePath.reqResBasePath))
                .then()
                .log()
                .body()
                .extract()
                .response();
    }
}