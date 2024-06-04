package JiraRestAssuredAPI;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.given;

public class jira {

    public static void main(String[] args) {


        RestAssured.baseURI="http://localhost:8080";

        //Login Scenario - Using Cookies
        SessionFilter session = new SessionFilter();
        String response = given().header("Content-Type", "application/json")
                        .body("{ \n" +
                                "    \"username\": \"your-username\", \n" +
                                "    \"password\": \"your-password\" \n" +
                                "}").log().all().filter(session)
                        .when().post("rest/auth/1/session")
                        .then().log().all().extract().response().asString();

        //Add Comment to issue
        given().pathParam("key", "JIR-1").log().all().header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"body\": \"Add comment to issue using restassured API\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}").filter(session)
                .when().post("/rest/api/2/issue/{key}/comment")
                .then().log().all().assertThat().statusCode(201);
    }
}
