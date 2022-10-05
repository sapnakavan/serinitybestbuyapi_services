package com.bestbuyapi.servicesinfo;

import com.bestbuyapi.serviccesinfo.ServicesSteps;
import com.bestbuyapi.testbase.TestBase;
import com.bestbuyapi.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class ServicesCURDTestWithSteps extends TestBase {

    static String name = "sap"+TestUtils.getRandomValue();
    static int serviceID;
    @Steps
    ServicesSteps servicesSteps ;
    @Title("This will create a new services")
    @Test
    public void test001(){
        ValidatableResponse response = servicesSteps.createservices(name,serviceID);
        response.log().all().statusCode(201);
    }


    @Title("verify if services was created")
    @Test
    public void test002(){
        HashMap<String,Object> servimap = servicesSteps.getservicesInfoByName(name);
        Assert.assertThat(servimap,hasValue(name));
         serviceID= (int) servimap.get("id");
        System.out.println(serviceID);
    }

    @Title("update services with name and id")
    @Test
    public void test003(){
        name = name + "updated";
        servicesSteps.updateservices(name,serviceID);
        HashMap<String,Object> servimap = servicesSteps.getservicesInfoByName(name);
        Assert.assertThat(servimap,hasValue(name));

    }


    @Title("Delete the services and verify if the services is deleted")
    @Test
    public void test004(){
        servicesSteps.deleteservice(serviceID).log().all().statusCode(200);
        servicesSteps.verifyservicesDeleted(serviceID).log().all().statusCode(404);
    }
}
