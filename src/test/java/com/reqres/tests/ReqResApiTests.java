package com.reqres.tests;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.reqres.base.ReqResBase;
import com.reqres.builder.ReqResRequestBuilder;
import com.reqres.common.ReqResHttpStatusCode;
import com.reqres.models.*;
import com.reqres.reponses.ReqResPostResponse;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import java.util.*;

import static org.hamcrest.Matchers.*;

@Feature("ReqRes-API")
public class ReqResApiTests {
    private Faker faker;

    @Test
    @Story("CREATE SINGE USER WITH MISSING NAME")
    @Description("AS AN API USER, CREATE USER WITH MISSING NAME.")
    @Severity(SeverityLevel.CRITICAL)
    public void createNewUserWithEmptyNameTest(){
        faker = new Faker();

        var newUserRequest = new ReqResRequestBuilder()
                .createNewUserRequest(
                        null,
                        null,
                        faker.job().position()
                );

        newUserRequest
                .then()
                .assertThat()
                .statusCode(ReqResHttpStatusCode.CREATED.getCode())
                .body("id", equalTo(ReqResPostResponse.id))
                .body("name", equalTo(ReqResPostResponse.name))
                .body("job", equalTo(ReqResPostResponse.job))
                .body("createdAt", equalTo(ReqResPostResponse.createdAt));
    }

    @Test(dependsOnMethods = {"createNewUserWithEmptyNameTest"})
    @Story("CREATE SINGLE USER")
    @Description("AS AN API USER, CREATE A SINGLE USER.")
    @Severity(SeverityLevel.CRITICAL)
    public void createNewUserTest(){
        faker = new Faker();

        var newUserRequest = new ReqResRequestBuilder()
                .createNewUserRequest(
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.job().position()
                );

        newUserRequest
                .then()
                .assertThat()
                .statusCode(ReqResHttpStatusCode.CREATED.getCode())
                .body("id", equalTo(ReqResPostResponse.id))
                .body("name", equalTo(ReqResPostResponse.name))
                .body("job", equalTo(ReqResPostResponse.job))
                .body("createdAt", equalTo(ReqResPostResponse.createdAt));
    }

    @Test
    @Story("GET SINGLE USER NOT FOUND")
    @Description("AS AN API USER, TRY TO GET A SINGLE USER THAT DOES NOT EXIST.")
    @Severity(SeverityLevel.CRITICAL)
    public void getSingleUserNotFound(){

        var getUserRequest = new ReqResRequestBuilder()
                .getUserByIdRequest("100");

        getUserRequest
                .then()
                .assertThat()
                .statusCode(ReqResHttpStatusCode.NOT_FOUND.getCode())
                .body(notNullValue());
    }

    @Test(dependsOnMethods = {"getSingleUserNotFound"})
    @Story("GET SINGLE USER")
    @Description("AS AN API USER, GET A SINGLE USER")
    @Severity(SeverityLevel.CRITICAL)
    public void getSingleUser(){

        var getUserRequest = new ReqResRequestBuilder()
                .getUserByIdRequest("2");

        var model = new ReqResUser(
                new ReqResData(String.valueOf(getUserRequest.body().jsonPath().getMap("data").get("id")),
                        String.valueOf(getUserRequest.body().jsonPath().getMap("data").get("email")),
                        String.valueOf(getUserRequest.body().jsonPath().getMap("data").get("first_name")),
                        String.valueOf(getUserRequest.body().jsonPath().getMap("data").get("last_name")),
                        String.valueOf(getUserRequest.body().jsonPath().getMap("data").get("avatar"))),
                new ReqResSupport(String.valueOf(getUserRequest.body().jsonPath().getMap("support").get("url")),
                        String.valueOf(getUserRequest.body().jsonPath().getMap("support").get("text"))));

        int expectedId = Integer.parseInt(model.getReqResData().getId());
        String expectedEmail = model.getReqResData().getEmail();
        String expectedFirstName = model.getReqResData().getFirst_name();
        String expectedLastName = model.getReqResData().getLast_name();
        String expectedAvatar = model.getReqResData().getAvatar();
        String expectedUrl = model.getReqResSupport().url();
        String expectedText = model.getReqResSupport().text();

        getUserRequest
                .then()
                .assertThat()
                .statusCode(ReqResHttpStatusCode.OK.getCode())
                .body("data.id", equalTo(expectedId))
                .body("data.email", equalTo(expectedEmail))
                .body("data.first_name", equalTo(expectedFirstName))
                .body("data.last_name", equalTo(expectedLastName))
                .body("data.avatar", equalTo(expectedAvatar))
                .body("support.url", equalTo(expectedUrl))
                .body("support.text", equalTo(expectedText));
    }

    @Test(dependsOnMethods = {"getSingleUser"})
    @Story("LIST USERS BY PAGE NUMBER")
    @Description("AS AN API USER, GET A LIST OF USERS BY THE PAGE NUMBER.")
    @Severity(SeverityLevel.CRITICAL)
    public void getAllUsers(){

        var getUsersRequest = new ReqResRequestBuilder()
                .getListUsers(2);

        var userCollection = getUsersRequest
                .body()
                .jsonPath()
                .getList("data");

        List<ReqResData> reqResUserData = new ArrayList<>();

        for(var userObject : new Gson().toJsonTree(userCollection).getAsJsonArray()){
            var userMap = userObject.getAsJsonObject().asMap();
            reqResUserData.add(
                    new ReqResData(
                            userMap.get("id").getAsString(),
                            userMap.get("email").getAsString(),
                            userMap.get("first_name").getAsString(),
                            userMap.get("last_name").getAsString(),
                            userMap.get("avatar").getAsString()));
        }

        var page = getUsersRequest.body().jsonPath().get("page").toString();
        var per_page = getUsersRequest.body().jsonPath().get("per_page").toString();
        var total = getUsersRequest.body().jsonPath().get("total").toString();
        var total_pages = getUsersRequest.body().jsonPath().get("total_pages").toString();

        var model = new ReqResListUser(
                Integer.parseInt(page),
                Integer.parseInt(per_page),
                Integer.parseInt(total),
                Integer.parseInt(total_pages),
                reqResUserData);

        var base = (ReqResBase)model;

        getUsersRequest
                .then()
                .assertThat()
                .statusCode(ReqResHttpStatusCode.OK.getCode())
                .body("page", equalTo(base.getPage()))
                .body("per_page", equalTo(base.getPer_page()))
                .body("total", equalTo(base.getTotal()))
                .body("total_pages", equalTo(base.getTotal_pages()))
                .body("data", not(0));
    }

    @Test(dependsOnMethods = {"getAllUsers"})
    @Story("GET USER LIST <RESOURCE>")
    @Description("AS AN API USER, GET A LIST OF USERS BY RESOURCE.")
    @Severity(SeverityLevel.CRITICAL)
    public void getAllUsersByResource(){

        var getUsersRequest = new ReqResRequestBuilder()
                .getListUsers();

        var userCollection = getUsersRequest
                .body()
                .jsonPath()
                .getList("data");

        List<ReqResUnknown> reqResUnknownUserData = new ArrayList<>();

        for(var userObject : new Gson().toJsonTree(userCollection).getAsJsonArray()){
            var userMap = userObject.getAsJsonObject().asMap();
            reqResUnknownUserData.add(
                    new ReqResUnknown(
                            Integer.parseInt(userMap.get("id").getAsString()),
                            userMap.get("name").getAsString(),
                            Integer.parseInt(userMap.get("year").getAsString()),
                            userMap.get("color").getAsString(),
                            userMap.get("pantone_value").getAsString()));
        }

        var page = getUsersRequest.body().jsonPath().get("page").toString();
        var per_page = getUsersRequest.body().jsonPath().get("per_page").toString();
        var total = getUsersRequest.body().jsonPath().get("total").toString();
        var total_pages = getUsersRequest.body().jsonPath().get("total_pages").toString();

        var model = new ReqResUnknownList(
                Integer.parseInt(page),
                Integer.parseInt(per_page),
                Integer.parseInt(total),
                Integer.parseInt(total_pages),
                reqResUnknownUserData
        );

        var base = (ReqResBase)model;

        getUsersRequest
                .then()
                .assertThat()
                .statusCode(ReqResHttpStatusCode.OK.getCode())
                .body("page", equalTo(base.getPage()))
                .body("per_page", equalTo(base.getPer_page()))
                .body("total", equalTo(base.getTotal()))
                .body("total_pages", equalTo(base.getTotal_pages()))
                .body("data", not(0));
    }
}