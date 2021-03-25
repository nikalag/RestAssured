package Meeting3;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

public class JsonPathExamples {
    @Test
    public void testGetSingleUser() {
        Response res = get("https://reqres.in/api/users/2");
        assertEquals(200, res.getStatusCode());
      /*  String json = res.asString();
        JsonPath jp =new JsonPath(json);*/
        assertEquals("janet.weaver@reqres.in", res.jsonPath().get("data.email"));

    }
    @Test
    public void withMap(){
        Response response = get("https://jsonplaceholder.typicode.com/users/1");
        Map<String, String> company = response.jsonPath().getMap("company");
        System.out.println(company.get("name"));
    }
    public void withListOfMap(){
        Response response = get("https://jsonplaceholder.typicode.com/users/");
        List<Map<String, String>> companies = response.jsonPath().getList("company");
        System.out.println(companies.get(0).get("name"));
    }
    @Test
    public void checkThereIsOneJapaneseCar() {

        given().
                when().
                get("https://jsonplaceholder.typicode.com/users/").
                then().
                assertThat().
                body("company.findAll{it.name=='Romaguera-Crona'}.size()", equalTo(1));
    }
}
