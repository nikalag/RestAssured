import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SimpleGetTest {
    Response response;

    @BeforeMethod
    public void setup(){
        RestAssured.baseURI = "https://demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        response = httpRequest.request(Method.GET, "/Tbilisi");
    }

    @Test
    public void GetWeatherDetails()
    {
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);
    }

    @Test
    public void validateStatusCodes(){
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Correct status code returned");
    }


    @Test
    public void IteratingOverHeaders()
    {
        Headers allHeaders = response.headers();

        for(Header header : allHeaders)
        {
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }
    }

    @Test
    public void WeatherMessageBody()
    {
        ResponseBody body = response.getBody();
        System.out.println("Response Body is: " + body.asString());
    }
    @Test
    public void VerifyCityInJsonResponse()
    {
        JsonPath jsonPathEvaluator = response.jsonPath();
        String city = jsonPathEvaluator.get("City");
        System.out.println("City received from Response " + city);
        Assert.assertEquals(city, "Tbilisi", "Correct city name received in the Response");

    }

    @Test
    public void queryParameter() {

        RestAssured.baseURI ="https://samples.openweathermap.org/data/2.5/";
        RequestSpecification request = RestAssured.given();

        Response response = request.queryParam("q", "London,UK")
                .queryParam("appid", "2b1fd2d7f77ccf1b7de9b441571b39b8")
                .get("/weather");

        String jsonString = response.asString();
        System.out.println(response.getStatusCode());
        Assert.assertEquals(jsonString.contains("London"), true);

    }


}
