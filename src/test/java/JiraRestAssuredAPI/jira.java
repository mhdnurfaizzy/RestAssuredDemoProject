package JiraRestAssuredAPI;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;

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
        String expectedMessage ="Hi How are you?";
        String addCommentResponse = given().pathParam("key", "JIR-1").log().all().header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"body\": \""+expectedMessage+"\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}").filter(session)
                .when().post("/rest/api/2/issue/{key}/comment")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js=new JsonPath(addCommentResponse);

        String commentId= js.getString("id");;

        //Add attachment to issue
        given().header("X-Atlassian-Token","no-check").filter(session).pathParam("key", "JIR-1")
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("C:\\Users\\HOME\\IdeaProjects\\RestAssuredDemoProject\\src\\test\\java\\JiraRestAssuredAPI\\note.txt"))
                .when().post("/rest/api/2/issue/{key}/attachments")
                .then().log().all().assertThat().statusCode(200);

        //Get issue details
        String issueDetails=given().filter(session).pathParam("key", "JIR-1")

                .queryParam("fields", "comment")
                .log().all().when().get("/rest/api/2/issue/{key}").then()
                .log().all().extract().response().asString();

        System.out.println(issueDetails);
        JsonPath js1 =new JsonPath(issueDetails);

        int commentsCount=js1.getInt("fields.comment.comments.size()");

        for(int i=0;i<commentsCount;i++) {
            String commentIdIssue =js1.get("fields.comment.comments["+i+"].id").toString();

            if (commentIdIssue.equalsIgnoreCase(commentId)) {

                String message= js1.get("fields.comment.comments["+i+"].body").toString();
                System.out.println(message);
                Assert.assertEquals(message, expectedMessage);

            }

        }
    }
}
