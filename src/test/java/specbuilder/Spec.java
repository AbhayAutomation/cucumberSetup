package specbuilder;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Spec {

    public static RequestSpecification requestSpec() {

        return new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType("application/json")
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification responseSpec() {

        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json;charset=UTF-8")
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getRequestSpec(String placeId) {

        return new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123").addQueryParam("place_id", placeId)
                .setContentType("application/json")
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }

}
