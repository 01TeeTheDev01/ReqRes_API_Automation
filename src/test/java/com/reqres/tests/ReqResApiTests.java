package com.reqres.tests;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.reqres.base.ReqResBase;
import com.reqres.builder.ReqResRequestBuilder;
import com.reqres.common.ReqResHttpStatusCode;
import com.reqres.models.*;
import com.reqres.reponses.ReqResResponse;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import java.util.*;

import static org.hamcrest.Matchers.*;

@Feature("ReqRes-API")
public class ReqResApiTests {

    private Faker faker;
    private final Gson gson;
    private final int userId = 22;

    public ReqResApiTests(){
        faker = new Faker();
        gson = new Gson();
    }

    @Test
    @Story("CREATE SINGLE USER")
    @Description("AS AN API USER, CREATE A SINGLE USER.")
    @Severity(SeverityLevel.CRITICAL)
    public void POST_createUser(){

        faker = new Faker();

        var newUserRequest = new ReqResRequestBuilder()
                .createUserRequest(
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.job().position()
                );

        newUserRequest
                .then()
                .assertThat()
                .statusCode(ReqResHttpStatusCode.CREATED.getCode())
                .body("id", equalTo(ReqResResponse.id))
                .body("name", equalTo(ReqResResponse.name))
                .body("job", equalTo(ReqResResponse.job))
                .body("createdAt", equalTo(ReqResResponse.createdAt));
    }

    @Test(dependsOnMethods = {"POST_createUser"})
    @Story("CREATE SINGLE USER")
    @Description("AS AN API USER, CREATE A SINGLE USER.")
    @Severity(SeverityLevel.CRITICAL)
    public void PUT_updateUser(){

        faker = new Faker();

        var updateRequest = new ReqResRequestBuilder()
                .updateUserByIdRequest(
                        Integer.parseInt(ReqResResponse.id),
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.job().position()
                );

        updateRequest
                .then()
                .assertThat()
                .statusCode(ReqResHttpStatusCode.OK.getCode())
                .body("id", equalTo(ReqResResponse.id))
                .body("name", equalTo(ReqResResponse.name))
                .body("job", equalTo(ReqResResponse.job))
                .body("updatedAt", equalTo(ReqResResponse.createdAt));
    }

    @Test
    @Story("GET SINGLE USER NOT FOUND")
    @Description("AS AN API USER, TRY TO GET A SINGLE USER THAT DOES NOT EXIST.")
    @Severity(SeverityLevel.NORMAL)
    public void GET_SingleUserNotFound(){

        var getUserRequest = new ReqResRequestBuilder()
                .getUserByIdRequest(userId);

        getUserRequest
                .then()
                .assertThat()
                .statusCode(ReqResHttpStatusCode.NOT_FOUND.getCode())
                .body(notNullValue());
    }

    @Test(dependsOnMethods = {"GET_SingleUserNotFound"})
    @Story("GET SINGLE USER")
    @Description("AS AN API USER, GET A SINGLE USER")
    @Severity(SeverityLevel.NORMAL)
    public void GET_SingleUser(){

        var getUserRequest = new ReqResRequestBuilder()
                .getUserByIdRequest(2);

        var userData = getUserRequest.body().jsonPath().getMap("data");
        var userSupport = getUserRequest.body().jsonPath().getMap("support");

        var model = new ReqResUser(
                new ReqResUserData(String.valueOf(userData.get("id")),
                        String.valueOf(userData.get("email")),
                        String.valueOf(userData.get("first_name")),
                        String.valueOf(userData.get("last_name")),
                        String.valueOf(userData.get("avatar"))),
                new ReqResSupport(String.valueOf(userSupport.get("url")),
                        String.valueOf(userSupport.get("text"))));

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

    @Test(dependsOnMethods = {"GET_SingleUser"})
    @Story("GET SINGLE USER BY <RESOURCE>")
    @Description("AS AN API USER, GET A SINGLE USER BY <RESOURCE>")
    @Severity(SeverityLevel.MINOR)
    public void GET_SingleUserByResource(){

        var getUserRequest = new ReqResRequestBuilder()
                .getSingleUserByResourceRequest(8);

        var userData = getUserRequest.body().jsonPath().getMap("data");
        var userSupport = getUserRequest.body().jsonPath().getMap("support");

        var model = new ReqResUnknown(
                Integer.parseInt(userData.get("id").toString()),
                userData.get("name").toString(),
                Integer.parseInt(userData.get("year").toString()),
                userData.get("color").toString(),
                userData.get("pantone_value").toString(),
                new ReqResSupport(
                        userSupport.get("url").toString(),
                        userSupport.get("text").toString())
        );

        int expectedId = Integer.parseInt(String.valueOf(model.id()));
        String expectedName = model.name();
        int expectedYear = Integer.parseInt(String.valueOf(model.year()));
        String expectedColor = model.color();
        String expectedPantone = model.pantone_value();
        String expectedUrl = model.support().url();
        String expectedText = model.support().text();

        getUserRequest
                .then()
                .assertThat()
                .statusCode(ReqResHttpStatusCode.OK.getCode())
                .body("data.id", equalTo(expectedId))
                .body("data.name", equalTo(expectedName))
                .body("data.year", equalTo(expectedYear))
                .body("data.color", equalTo(expectedColor))
                .body("data.pantone_value", equalTo(expectedPantone))
                .body("support.url", equalTo(expectedUrl))
                .body("support.text", equalTo(expectedText));
    }

    @Test(dependsOnMethods = {"GET_SingleUserByResource"})
    @Story("GET SINGLE USER BY <RESOURCE> NOT FOUND")
    @Description("AS AN API USER, GET A SINGLE USER BY <RESOURCE> NOT FOUND")
    @Severity(SeverityLevel.MINOR)
    public void GET_SingleUserByResourceNotFound(){

        var getUserRequest = new ReqResRequestBuilder()
                .getSingleUserByResourceNotFoundRequest(12);

        var userData = getUserRequest.body().jsonPath().getMap("data");
        var userSupport = getUserRequest.body().jsonPath().getMap("support");

        if(userData == null || userData.isEmpty() ||
                userSupport == null || userSupport.isEmpty())
            getUserRequest
                    .then()
                    .assertThat()
                    .statusCode(ReqResHttpStatusCode.NOT_FOUND.getCode())
                    .log()
                    .body();
    }

    @Test(dependsOnMethods = {"GET_SingleUser"})
    @Story("LIST USERS BY PAGE NUMBER")
    @Description("AS AN API USER, GET A LIST OF USERS BY THE PAGE NUMBER.")
    @Severity(SeverityLevel.CRITICAL)
    public void GET_AllUsers(){

        var getUsersRequest = new ReqResRequestBuilder()
                .getListUsers(2);

        var userCollection = getUsersRequest
                .body()
                .jsonPath()
                .getList("data");

        List<ReqResUserData> reqResUserData = new ArrayList<>();

        for(var userObject : gson.toJsonTree(userCollection).getAsJsonArray()){
            var userMap = userObject.getAsJsonObject().asMap();
            reqResUserData.add(
                    new ReqResUserData(
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
                reqResUserData
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

    @Test(dependsOnMethods = {"GET_AllUsers"})
    @Story("GET USER LIST <RESOURCE>")
    @Description("AS AN API USER, GET A LIST OF USERS BY RESOURCE.")
    @Severity(SeverityLevel.MINOR)
    public void GET_AllUsersByResource(){

        var getUsersRequest = new ReqResRequestBuilder()
                .getListUsers();

        var userCollection = getUsersRequest
                .body()
                .jsonPath()
                .getList("data");

        List<ReqResUnknown> reqResUnknownUserData = new ArrayList<>();

        for(var userObject : gson.toJsonTree(userCollection).getAsJsonArray()){
            var userMap = userObject.getAsJsonObject().asMap();
            reqResUnknownUserData.add(
                    new ReqResUnknown(
                            Integer.parseInt(userMap.get("id").getAsString()),
                            userMap.get("name").getAsString(),
                            Integer.parseInt(userMap.get("year").getAsString()),
                            userMap.get("color").getAsString(),
                            userMap.get("pantone_value").getAsString(),
                            null));
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

    @Test(dependsOnMethods = {"PUT_updateUser"})
    @Story("DELETE SINGLE USER")
    @Description("AS AN API USER, DELETE SINGLE USER")
    @Severity(SeverityLevel.NORMAL)
    public void DELETE_SingleUser(){

        var deleteUserRequest = new ReqResRequestBuilder()
                .deleteUserByIdRequest(userId);

        deleteUserRequest
                .then()
                .assertThat()
                .statusCode(ReqResHttpStatusCode.NO_CONTENT.getCode())
                .body(emptyOrNullString());
    }
}