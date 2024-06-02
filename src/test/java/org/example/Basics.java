package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basics {
    public static void main(String[] args) {
        // Validate if Add place API is working as expected

        //Given - all input details
        //When - Submit the API - resource, http method
        //Then- Validate the response

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-type",  "application/json")
                .body(payload.addPlace())
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

        System.out.println("===RESPONSE BODY===");
        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String placeID = js.getString("place_id");

        System.out.println(placeID);

    }
}