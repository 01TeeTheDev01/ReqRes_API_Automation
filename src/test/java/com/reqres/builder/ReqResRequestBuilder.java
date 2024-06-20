package com.reqres.builder;

import com.reqres.common.ReqResBasePath;
import com.reqres.common.ReqResContentType;
import com.reqres.reponses.ReqResResponse;
import com.reqres.utils.ReqResContentTypeEnum;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ReqResRequestBuilder {

    private final ReqResContentType reqResContentType;

    private final int maxUserId = 12;

    public ReqResRequestBuilder(){
        reqResContentType = new ReqResContentType();
    }

    public Response createUserRequest(String firstName, String lastName, String job){
        var newUserBuilder = new ReqResPOSTPayloadBuilder();

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
                .post(String.format("%s/api/users",
                        ReqResBasePath.reqResBasePath))
                .then()
                .log()
                .body()
                .extract()
                .response();

        ReqResResponse.id = requestResponse.jsonPath().getString("id");
        ReqResResponse.name = requestResponse.jsonPath().getString("name");
        ReqResResponse.job = requestResponse.jsonPath().getString("job");
        ReqResResponse.createdAt = requestResponse.jsonPath().getString("createdAt");

        return requestResponse;
    }

    public Response getUserByIdRequest(int id){
        return given()
                .when()
                .get(String.format("%s/api/users/%s",
                        ReqResBasePath.reqResBasePath, id))
                .then()
                .log()
                .body()
                .extract()
                .response();
    }

    public Response getListUsers(int pageNum){
        return given()
                .when()
                .get(String.format("%s/api/users?page=%s",
                        ReqResBasePath.reqResBasePath, pageNum))
                .then()
                .log()
                .body()
                .extract()
                .response();
    }

    public Response getListUsers(){
        return given()
                .when()
                .get(String.format("%s/api/unknown",
                        ReqResBasePath.reqResBasePath))
                .then()
                .log()
                .body()
                .extract()
                .response();
    }

    public Response getSingleUserByResourceRequest(int id){
        if(id > 0 && id <= maxUserId){
            return given()
                    .when()
                    .get(String.format("%s/api/unknown/%s",
                            ReqResBasePath.reqResBasePath, id))
                    .then()
                    .log()
                    .body()
                    .extract()
                    .response();
        }

        return given()
                .when()
                .get(String.format("%s/api/unknown/%s",
                        ReqResBasePath.reqResBasePath, maxUserId))
                .then()
                .log()
                .body()
                .extract()
                .response();
    }

    public Response getSingleUserByResourceNotFoundRequest(int id){
        if(id > maxUserId){
            return given()
                    .when()
                    .get(String.format("%s/api/unknown/%s",
                            ReqResBasePath.reqResBasePath, id))
                    .then()
                    .log()
                    .body()
                    .extract()
                    .response();
        }

        return given()
                .when()
                .get(String.format("%s/api/unknown/%s",
                        ReqResBasePath.reqResBasePath, maxUserId + 1))
                .then()
                .log()
                .body()
                .extract()
                .response();
    }

    public Response updateUserByIdRequest(int id, String firstName, String lastName, String job){
        var updateUserBuilder = new ReqResPUTPayloadBuilder();

        var updateUserBody = updateUserBuilder
                .withFirstName(firstName)
                .withLastName(lastName)
                .withJob(job).build();

        if(updateUserBody == null)
            return null;

        var json = reqResContentType.getContentType(ReqResContentTypeEnum.JSON);

        var requestResponse =
                given()
                        .when()
                        .contentType(json)
                        .body(updateUserBody)
                        .log()
                        .body()
                        .put(String.format("%s/api/users/%s",
                                ReqResBasePath.reqResBasePath, id))
                        .then()
                        .log()
                        .body()
                        .extract()
                        .response();

        ReqResResponse.id = requestResponse.jsonPath().getString("id");
        ReqResResponse.name = requestResponse.jsonPath().getString("name");
        ReqResResponse.job = requestResponse.jsonPath().getString("job");
        ReqResResponse.createdAt = requestResponse.jsonPath().getString("updatedAt");

        return requestResponse;
    }

    public Response deleteUserByIdRequest(int id){
        return given()
                .when()
                .delete(String.format("%s/api/users/%s",
                        ReqResBasePath.reqResBasePath, id))
                .then()
                .log()
                .body()
                .extract()
                .response();
    }

    public Response registerRequest(String email, String password){
        var registerBuilder = new ReqResRegisterBuilder();

        var registerBody =
                registerBuilder
                        .withEmail(email)
                        .withPassword(password)
                        .build();

        if(registerBody == null)
            return null;

        var json = reqResContentType.getContentType(ReqResContentTypeEnum.JSON);

        var requestResponse = given()
                .when()
                .contentType(json)
                .body(registerBody)
                .log()
                .body()
                .post(String.format("%s/api/register",
                        ReqResBasePath.reqResBasePath))
                .then()
                .log()
                .body()
                .extract()
                .response();

        ReqResResponse.id = requestResponse.jsonPath().getString("id");
        ReqResResponse.token = requestResponse.jsonPath().getString("token");
        ReqResResponse.error = requestResponse.jsonPath().getString("error");

        return requestResponse;
    }

    public Response loginRequest(String email, String password){
        var loginBuilder = new ReqResLoginBuilder();

        var loginBody =
                loginBuilder
                        .withEmail(email)
                        .withPassword(password)
                        .build();

        if(loginBody == null)
            return null;

        var json = reqResContentType.getContentType(ReqResContentTypeEnum.JSON);

        var requestResponse = given()
                .when()
                .contentType(json)
                .body(loginBody)
                .log()
                .body()
                .post(String.format("%s/api/login",
                        ReqResBasePath.reqResBasePath))
                .then()
                .log()
                .body()
                .extract()
                .response();

        ReqResResponse.token = requestResponse.jsonPath().getString("token");
        ReqResResponse.error = requestResponse.jsonPath().getString("error");

        return requestResponse;
    }
}