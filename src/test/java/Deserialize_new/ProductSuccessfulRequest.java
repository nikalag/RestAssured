package Deserialize_new;

import Deserialize.RegistrationFailureResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.annotations.Test;

public class ProductSuccessfulRequest {
    @Test
    public void getProduct() {
       ProductList response= RestAssured.given().when()
                .get("https://chercher.tech/sample/api/product/read")
               .jsonPath()
               .getObject("", ProductList.class);


        System.out.println(response.records.get(0).id);
    }
}
