package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.example.files.ReUsableMethod;
import org.example.files.payload;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basics {
    public static void main(String[] args) {
        // Validate if Add place API is working as expected

        //Given - all input details
        //When - Submit the API - resource, http method
        //Then- Validate the response

        //Add Place testing

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-type",  "application/json")
                .body(payload.addPlace())
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

        System.out.println("===RESPONSE BODY Add Place===");
        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String placeID = js.getString("place_id");

        System.out.println(placeID);

        //Update Place Testing
        String address = "70 winter walk, USA";
        given().log().all().queryParam("key", "qaclick123").header("Content-type",  "application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeID+"\",\n" +
                        "\"address\":\""+address+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //Get Place Testing - verify place updated
        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        System.out.println("===RESPONSE BODY - Get Place===");
        JsonPath js1 = ReUsableMethod.rawToJson(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress, address);

    }
}