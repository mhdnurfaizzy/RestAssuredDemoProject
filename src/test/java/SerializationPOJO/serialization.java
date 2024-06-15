package SerializationPOJO;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class serialization {

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

        Response res = given().queryParam("key" ,"qaclick123")
                .body(aps)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response();

        String responseString = res.asString();

        System.out.println(responseString);
    }

}
