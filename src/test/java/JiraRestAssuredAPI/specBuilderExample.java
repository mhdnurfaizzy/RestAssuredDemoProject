package JiraRestAssuredAPI;

import SerializationPOJO.addPlace;
import SerializationPOJO.location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class specBuilderExample {

    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        addPlace aps = new addPlace();

        aps.setAccuracy(50);
        aps.setName("Arindam Dalal");
        aps.setPhone_number("(+91) 983 893 3937");
        aps.setAddress("B-408,Arcadia,Sinhagad Road,Pune,Maharashtra,India");
        aps.setWebsite("http://google.com");
        aps.setLanguage("French-IN");
        List<String> typelist = new ArrayList<String>();
        typelist.add("shoe park");
        typelist.add("shop");
        aps.setTypes(typelist);
        location loc = new location();
        loc.setLat(-38.383494);
        loc.setLng(33.427362);

        aps.setLocation(loc);



        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        ResponseSpecification resSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();

        RequestSpecification res = given().spec(req).body(aps);

        Response response = res.when().post("/maps/api/place/add/json")
                .then().spec(resSpec).extract().response();

        String responseString = response.asString();
        System.out.println(responseString);
    }

}
