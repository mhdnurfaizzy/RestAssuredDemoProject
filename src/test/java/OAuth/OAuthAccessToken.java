package OAuth;

import deserializationPOJO.api;
import deserializationPOJO.getCourse;
import deserializationPOJO.webAutomation;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OAuthAccessToken {

    public static void main(String[] args) throws InterruptedException {

        String[] courseTitle={"Selenium Webdriver Java", "Cypress", "Protractor"};

        //AuthorizationServer
        String response =
                given()
                        .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                        .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                        .formParams("grant_type", "client_credentials")
                        .formParams("scope", "trust")
                        .when().log().all()
                        .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

        System.out.println(response);

        JsonPath jsonPath = new JsonPath(response);
        String accessToken = jsonPath.getString("access_token"); //Store the token in json path
        System.out.println(accessToken);

        //GetCourseDetails
        getCourse gc= given()
                .queryParams("access_token", accessToken)
                .when()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .as(getCourse.class);

        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());

//        64. Solving Complex Queries from Json with simple POJO methods -Part 1
        List<api> apiCourses = gc.getCourses().getApi();
        for(int i=0;i<apiCourses.size();i++) {
            if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
                System.out.println(apiCourses.get(i).getPrice());
            }
        }

//        65. Solving Complex Queries from Json with simple POJO methods-Part 2
//        Get the course names of webAutomation

        ArrayList<String> a = new ArrayList<>();

        List<webAutomation> wa = gc.getCourses().getWebAutomation();
        for (int j=0;j<wa.size();j++) {
            a.add(wa.get(j).getCourseTitle());
        }
        List<String> expectedList = Arrays.asList(courseTitle);
        Assert.assertTrue(a.equals(expectedList));


    }
}
