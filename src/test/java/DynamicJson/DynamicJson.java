package DynamicJson;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.example.files.ReUsableMethod;
import org.example.files.payload;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(dataProvider="BooksData")
    public void addBook(String isbn, String aisle){
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String response =
                given().log().all().header("Content-Type", "application/json")
                        .body(payload.AddBook(isbn, aisle))
                        .when()
                        .post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUsableMethod.rawToJson(response);
        String id = js.get("ID");

        System.out.println(id);

    }

    @DataProvider(name="BooksData")
    public Object[][] getData() {
        return new Object[][]
                {
                    {"Kolis","9012"},
                    {"lopis","1324"},
                    {"holis","9485"}
                };
    }
}
