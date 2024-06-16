package E2EEcommerceAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class EcommerceApiTest {

    public static void main(String[] args) {

        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("izi@gmail.com");
        loginRequest.setUserPassword("Testing890-");

        RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequest);

        LoginResponse loginResponse =
                reqLogin.
                when().post("/api/ecom/auth/login")
                        .then().log().all()
                        .extract().response().as(LoginResponse.class);

        System.out.println(loginResponse.getToken());
        System.out.println(loginResponse.getUserId());
    }
}
