import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class Log {
    @Test
    void testLogging() {
        RestAssured.given()
                .when()
                .log()
                .uri()
                .get("https://chercher.tech/sample/api/product/read");
    }
    @Test
    void testLogging2() {
        RestAssured.given()
                .when()
                .log().all() // after preparing request but not yet send to server
                .get("https://chercher.tech/sample/api/product/read")
                .then()
                .log().all(); // after receiving the response from server
    }
}
