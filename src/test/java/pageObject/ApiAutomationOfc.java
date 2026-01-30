package pageObject;

import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.AddPlaceResponse;
import specbuilder.Spec;

import static io.restassured.RestAssured.*;

public class ApiAutomationOfc {




@Test (description = "Send a Post Resquest to add a place and validate the response")
    public void sendPostRequest() {
        // Implementation for sending a POST request

        RestAssured.baseURI = "https://rahulshettyacademy.com";
    AddPlaceResponse  response = given().given()
            .spec(Spec.requestSpec())
            .body(PayloadAddPlace.getAddPlacePayload())
            .when().post("maps/api/place/add/json")
            .then().spec(Spec.responseSpec())
            .extract().as(AddPlaceResponse.class);

//    Assert.assertEquals(response., 200);

    Assert.assertEquals(response.getStatus(), "OK");
    Assert.assertEquals(response.getScope(), "APP");

    Assert.assertNotNull(response.getPlace_id());
    Assert.assertNotNull(response.getId());
    Assert.assertNotNull(response.getReference());



}

}
