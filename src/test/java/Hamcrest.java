import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Hamcrest {
    @Test
    void test() {
        RestAssured.given().when()
                .get("https://chercher.tech/sample/api/product/read?id=3793")
                .then()
                .body("id[0]", equalTo("3793"));
    }
    @Test
    void test2() {
        RestAssured.given().when()
                .get("https://chercher.tech/sample/api/product/read")
                .then()
                .body("records.id", empty());
    }
    @Test
    void test3() {
        RestAssured.given().when()
                .get("https://chercher.tech/sample/api/product/read")
                .then()
                .body("records.id[1,2]",hasItems("4361","4360"));
    }
    @Test
    void test4(){
        String testString = "Hector killed Achilles";
        assertThat(testString, anyOf(startsWith("Hec"), containsString("baeldung")));
    }
}
