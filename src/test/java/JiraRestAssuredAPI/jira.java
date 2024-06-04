package JiraRestAssuredAPI;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class jira {

    public static void main(String[] args) {


        RestAssured.baseURI="http://localhost:8080";

        //Add Comment
        given().pathParam("key", "JIR").log().all().header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"body\": \"Add comment to issue using restassured API\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}")
                .post("/rest/api/2/issue/{key}/comment");
    }
}
