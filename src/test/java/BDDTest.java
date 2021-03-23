import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;

public class BDDTest {
    @Test
    public void test_NumberOfCircuitsFor2017Season_ShouldBe20() {
        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId",hasSize(20));
    }
    @Test
    public void test_Md5CheckSumForTest_ShouldBe098f6bcd4621d373cade4e832627b4f6() {

        String originalText = "test";
        String expectedMd5CheckSum = "098f6bcd4621d373cade4e832627b4f6";

        given().
                param("text",originalText).
                when().
                get("http://md5.jsontest.com").
                then().
                assertThat().
                body("md5",equalTo(expectedMd5CheckSum));
    }

    @Test
    public void test_NumberOfCircuits_ShouldBe20_Parameterized() {

        String season = "2017";
        int numberOfRaces = 20;

        given().
                pathParam("raceSeason",season).
                when().
                get("http://ergast.com/api/f1/{raceSeason}/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId",hasSize(numberOfRaces));
    }

    @Test
    public static void getResponseStatus(){
         given().queryParam("CUSTOMER_ID","68195")
                .queryParam("PASSWORD","1234!")
                .queryParam("Account_No","1")
                 .when()
                 .get("http://demo.guru99.com/V4/sinkministatement.php")
                 .then()
                 .assertThat()
                 .statusCode(200);

    }

    @Test
    public static void getResponseHeaders(){
        System.out.println("The headers in the response "+
                get("http://demo.guru99.com/V4/sinkministatement.php")
                        .then()
                        .extract()
                        .statusLine());
    }
    @Test
    public static void getResponseTime(){
        System.out.println("The time taken to fetch the response "+
                get("http://demo.guru99.com/V4/sinkministatement.php")
                .timeIn(TimeUnit.MILLISECONDS) + " milliseconds");
    }


    @DataProvider(name="seasonsAndNumberOfRaces")
    public Object[][] createTestDataRecords() {
        return new Object[][] {
                {"2017",14},
                {"2016",21},
                {"1966",9}
        };
    }

    @Test(dataProvider="seasonsAndNumberOfRaces")
    public void test_NumberOfCircuits_ShouldBe_DataDriven(String season, int numberOfRaces) {

        given().
                pathParam("raceSeason",season).
                when().
                get("http://ergast.com/api/f1/{raceSeason}/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId",hasSize(numberOfRaces));
    }
    @Test
    public void test_APIWithBasicAuthentication_ShouldBeGivenAccess() {

        given().
                auth().
                preemptive().
                basic("username", "password").
                when().
                get("http://path.to/basic/secured/api").
                then().
                assertThat().
                statusCode(200);
    }
    @Test
    public void test_ScenarioRetrieveFirstCircuitFor2017SeasonAndGetCountry_ShouldBeAustralia() {

        String circuitId = given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                extract().
                path("MRData.CircuitTable.Circuits.circuitId[0]");

        given().
                pathParam("circuitId",circuitId).
                when().
                get("http://ergast.com/api/f1/circuits/{circuitId}.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.Location[0].country",equalTo("Australia"));
    }

    ResponseSpecification checkStatusCodeAndContentType =
            new ResponseSpecBuilder().
                    expectStatusCode(200).
                    expectContentType(ContentType.JSON).
                    build();

    @Test
    public void test_NumberOfCircuits_ShouldBe20_UsingResponseSpec() {

        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                spec(checkStatusCodeAndContentType).
                and().
                body("MRData.CircuitTable.Circuits.circuitId",hasSize(20));
    }
}
