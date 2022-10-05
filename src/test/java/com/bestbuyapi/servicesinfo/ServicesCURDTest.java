package com.bestbuyapi.servicesinfo;


import com.bestbuyapi.constants.EndPoints;
import com.bestbuyapi.model.ServicesPojo;
import com.bestbuyapi.testbase.TestBase;
import com.bestbuyapi.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;



public class ServicesCURDTest extends TestBase {


   static String name = "sap"+TestUtils.getRandomValue();
   static  int serviceID;

    @Title("This is will get all information of all services")
    @Test
    public void test001() {
        SerenityRest. given().log().all()
                .when().get()
                .then()
                .log().all()
                .statusCode(200);
    }
   @Title("This will create a new services")
    @Test
    public void test002(){
       ServicesPojo servicesPojo = new ServicesPojo();

       servicesPojo.setName(name);
       SerenityRest.given().
               log().all()
               .contentType(ContentType.JSON)
               .body(servicesPojo)
               .when()
               .post()
               .then().log().all().statusCode(201);
   }
    @Title("verify if services was created")
    @Test
    public void test003(){
        String p1="data.findAll{it.name='";
        String p2="'}.get(0)";
        HashMap<String,Object> servimap =SerenityRest.given().log().all()
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path(p1+name+p2);
        Assert.assertThat(servimap,hasValue(name));
        serviceID= (int) servimap.get("id");
    }


    @Title("update services with name and id")
    @Test
    public void test004(){
        name = name + "updated";

      ServicesPojo servicesPojo = new ServicesPojo();

      servicesPojo.setName(name);
      SerenityRest.given().log().all()
              .header("Content-Type","application/json; charset=UTF-8")
              .pathParam("serviceID",serviceID)
              .body(servicesPojo)
              .when()
              .put(EndPoints.UPDATE_SINGLE_SERVICES_BY_ID)
              .then().log().all().statusCode(200);
  }
    @Title("Delete the services and verify if the services is deleted")
    @Test
    public void test005(){
        SerenityRest. given()
                .pathParam("serviceID",serviceID)
                .when()
                .delete(EndPoints.DELETE_SINGLE_SERVICES_BY_ID)
                .then().statusCode(200);

        SerenityRest .given().log().all()
                .pathParam("serviceID",serviceID)
                .when()
                .get(EndPoints.GET_SINGLE_SERVICES_BY_ID)
                .then() .statusCode(404);

    }

}
