package Deserialize;

import com.sun.xml.bind.v2.model.core.TypeRef;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static org.testng.AssertJUnit.assertEquals;

public class RegistrationSuccessful{
@Test
public void RegistrationSuccessful() {
    RestAssured.baseURI = "https://demoqa.com/customer";
    RequestSpecification request = RestAssured.given().header("Content-type", "application/json");

    JSONObject requestParams = new JSONObject();
    requestParams.put("FirstName", "Virender"); // Cast
    requestParams.put("LastName", "Singh");
    requestParams.put("UserName", "63userf2d3d2011");
    requestParams.put("Password", "password1");
    requestParams.put("Email", "ed26dff39@gmail.com");

    request.body(requestParams.toJSONString());
    Response response = request.post("/register");

    ResponseBody body = response.getBody();

    // Deserialize the Response body into RegistrationSuccessResponse
    RegistrationSuccessResponse responseBody = body.as(RegistrationSuccessResponse.class);

    // Use the RegistrationSuccessResponse class instance to Assert the values of
    // Response.
    Assert.assertEquals("OPERATION_SUCCESS", responseBody.SuccessCode);
    Assert.assertEquals("Operation completed successfully", responseBody.Message);

}

    @Test
    public void RegistrationWithDifferentStatuses()
    {
        RestAssured.baseURI ="https://demoqa.com/customer";
        RequestSpecification request = RestAssured.given().header("Content-type", "application/json");

        JSONObject requestParams = new JSONObject();
        requestParams.put("FirstName", "Virender"); // Cast
        requestParams.put("LastName", "Singh");
        requestParams.put("UserName", "63userf2d3d2011");
        requestParams.put("Password", "password1");
        requestParams.put("Email",  "ed26dff39@gmail.com");

        request.body(requestParams.toJSONString());
        Response response = request.post("/register");

        ResponseBody body = response.getBody();
        System.out.println(response.body().asString());

        if(response.statusCode() == 200)
        {
            // Deserialize the Response body into RegistrationFailureResponse
            RegistrationFailureResponse responseBody = body.as(RegistrationFailureResponse.class);

            // Use the RegistrationFailureResponse class instance to Assert the values of
            // Response.
            Assert.assertEquals("User already exists", responseBody.FaultId);
            Assert.assertEquals("FAULT_USER_ALREADY_EXISTS", responseBody.fault);
        }
        else if (response.statusCode() == 201)
        {
            // Deserialize the Response body into RegistrationSuccessResponse
            RegistrationSuccessResponse responseBody = body.as(RegistrationSuccessResponse.class);
            // Use the RegistrationSuccessResponse class instance to Assert the values of
            // Response.
            Assert.assertEquals("OPERATION_SUCCESS", responseBody.SuccessCode);
            Assert.assertEquals("Operation completed successfully", responseBody.Message);
        }
    }



}