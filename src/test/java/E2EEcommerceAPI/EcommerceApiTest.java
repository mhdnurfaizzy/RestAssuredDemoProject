package E2EEcommerceAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcommerceApiTest {

    public static void main(String[] args) {

        //Login and get token

        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("izi@gmail.com");
        loginRequest.setUserPassword("Testing890-");


        //SSL HTTPS relaxed
        RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all().spec(req).body(loginRequest);

        LoginResponse loginResponse =
                reqLogin.
                when().post("/api/ecom/auth/login")
                        .then().log().all()
                        .extract().response().as(LoginResponse.class);

        System.out.println(loginResponse.getToken());
        String token = loginResponse.getToken();
        System.out.println(loginResponse.getUserId());
        String userId = loginResponse.getUserId();

        //Add Product
        RequestSpecification addProductBaseReq = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token).build();

        RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq).param("productName", "Laptop")
                .param("productAddedBy", userId).param("productCategory", "fashion")
                .param("productSubCategory", "shirts").param("productPrice", "11500")
                .param("productDescription", "Lenova")
                .param("productFor", "men") .multiPart("productImage", new File("C:\\Users\\HOME\\IdeaProjects\\RestAssuredDemoProject\\Github.png"));

        String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
                .then().log().all().extract().response().asString();

        JsonPath js = new JsonPath(addProductResponse);
        String productId = js.get("productId");

        //Create order
        RequestSpecification createOrderBaseReq = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token).setContentType(ContentType.JSON).build();

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCountry("India");
        orderDetails.setProductOrderedId(productId);

        List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
        orderDetailsList.add(orderDetails);
        Orders orders = new Orders();
        orders.setOrders(orderDetailsList);

        RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(orders);

        String responseAddOrdere = createOrderReq.when().post("/api/ecom/order/create-order")
                .then().log().all().extract().response().asString();

        System.out.println(responseAddOrdere);

        //Delete Product
        RequestSpecification deleteProductBaseReq = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token).setContentType(ContentType.JSON).build();

        RequestSpecification deleteProductReq = given().log().all().spec(deleteProductBaseReq).pathParam("productId", productId);

        String deleteProductResponse = deleteProductReq.when().delete("/api/ecom/product/delete-product/{productId}")
                .then().log().all().extract().response().asString();

        JsonPath js1 = new JsonPath(deleteProductResponse);

        Assert.assertEquals("Product Deleted Successfully", js1.get("message"));
    }
}
