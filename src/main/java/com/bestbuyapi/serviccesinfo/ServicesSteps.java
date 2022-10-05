package com.bestbuyapi.serviccesinfo;


import com.bestbuyapi.constants.EndPoints;
import com.bestbuyapi.model.ServicesPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ServicesSteps {
     static int serviceID;
    @Step("creating serbices with name :{0},id :{1}")
    public ValidatableResponse createservices(String name, int serviceID) {
       ServicesPojo servicesPojo = new ServicesPojo();

        servicesPojo.setName(name);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(servicesPojo)
                .when()
                .post()
                .then().log().all().statusCode(201);
    }

    @Step ("getting services info by name:{0}")
    public HashMap<String,Object> getservicesInfoByName(String name){
        String p1="data.findAll{it.name='";
        String p2="'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path(p1+name+p2);
    }
    @Step("update services with name :{0},serviceID :{1}")
    public ValidatableResponse updateservices(String name, int serviceID) {
        ServicesPojo servicesPojo = new ServicesPojo();

        servicesPojo.setName(name);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json; charset=UTF-8")
                .pathParam("serviceID", serviceID)
                .body(servicesPojo)
                .when()
                .put(EndPoints.UPDATE_SINGLE_SERVICES_BY_ID)
                .then();
    }
    @Step("Delete services with serviceID :{0} ")
    public ValidatableResponse deleteservice(int serviceID){
        return    SerenityRest. given()
                .pathParam("serviceID",serviceID)
                .when()
                .delete(EndPoints.DELETE_SINGLE_SERVICES_BY_ID)
                .then();


    }
    @Step("Verify services has been deleted for serviceID: {0}")
    public ValidatableResponse verifyservicesDeleted(int serviceID){
        return SerenityRest .given().log().all()
                .pathParam("serviceID",serviceID)
                .when()
                .get(EndPoints.GET_SINGLE_SERVICES_BY_ID)
                .then();


    }
    }
