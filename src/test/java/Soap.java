import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Soap {
    @Test
    public void readXml() throws IOException {
        FileInputStream stream=new FileInputStream("C:\\Users\\atkabladze\\Desktop\\RestAssuredProject\\src\\main\\resources\\test.xml");
        RestAssured.baseURI="http://currencyconverter.kowabunga.net";
        Response response=given().header("Content-Type","text/xml").and().body(IOUtils.toString(stream,"UTF-8"))
                .when().post("/converter.asmx").then().statusCode(200).and().log().all().extract().response();
        XmlPath xmlPath=new XmlPath(response.asString());
        String rate=xmlPath.getString("GetConversionRateResult");
        System.out.println(rate);
    }
}
