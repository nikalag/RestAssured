import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.Assertion;

import static io.restassured.RestAssured.given;

public class Test {
    private static final String ApiKey = "";
    private static String CollectionUid;

    @BeforeClass
    public static void setUp()
    {
        RestAssured.baseURI="https://api.getpostman.com";
        RestAssured.basePath="collections";
    }

    @org.testng.annotations.Test(description = "create empty collection",priority=1)
    public static void createCollection()
    {
       String RequestBody ="{\n"+
               "\"collection\": {\n" +
               "    \"info\": {\n" +
               "      \"name\": \"Sample Collection\",\n" +
               "      \"description\": \"This is just a sample collection.\",\n" +
                       " \"schema\": \"https://schema.getpostman.com/json/collection/v2.1.0/collection.json\""+
               "    },\n"+
               "\"item\":[]"+
               "}\n"+
               "}\n";


        Response response =given().proxy("192.168.100.50",3128)
                .header("Content-Type","application/json")
                .header("X-Api-Key",ApiKey)
                .body(RequestBody)
                .when().post().then().extract().response();

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertNotEquals(response.jsonPath().getString("collection.id"),null);
        Assert.assertNotEquals(response.jsonPath().getString("collection.uid"),null);
        Assert.assertEquals(response.jsonPath().getString("collection.name"),"Sample Collection");
        //save collectionuid for other requests
        CollectionUid=response.jsonPath().getString("collection.uid");

    }

    @org.testng.annotations.Test(description = "add sample request in collection",priority = 2)
    public static void updateCollection()
    {
        String RequestBody ="{\n"+
                "\"collection\": {\n" +
                "    \"info\": {\n" +
                "      \"name\": \"Sample Collection changed\",\n" +
                "      \"description\": \"This is just a sample collection.\",\n" +
                " \"schema\": \"https://schema.getpostman.com/json/collection/v2.1.0/collection.json\""+
                "    },\n"+
                "\"item\":[{\n" +
                "        \"name\": \"Sample GET Request\",\n" +
                "        \"request\": {\n" +
                "          \"url\": \"https://postman-echo/get\",\n" +
                "          \"method\": \"GET\",\n" +
                "          \"description\": \"This is a sample GET Request\"\n" +
                "        }\n" +
                "      }]"+
                "}\n"+
                "}\n";


        Response response = given().proxy("192.168.100.50",3128)
                .header("Content-Type","application/json")
                .header("X-Api-Key","PMAK-616c3f4a652ad40035c38fe7-2c2c11048ff732c6caa8419fc401ebcf1d")
                .body(RequestBody)
                .when().put("/"+CollectionUid).then().extract().response();

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertNotEquals(response.jsonPath().getString("collection.id"),null);
        Assert.assertNotEquals(response.jsonPath().getString("collection.uid"),null);
        Assert.assertEquals(response.jsonPath().getString("collection.name"),"Sample Collection changed");

    }

    @org.testng.annotations.Test(description = "get collection",priority = 3)
    public static void getCollection()
    {

        Response response = given()
                .proxy("192.168.100.50",3128).header("X-Api-Key",ApiKey)
                .when().get("https://api.getpostman.com/collections")
                .then().extract().response();





        Assert.assertEquals(response.statusCode(),200);
        //assert that get request added and collection name changed
        Assert.assertEquals(response.jsonPath().getString("collection.info.name"),"Sample Collection changed");
        Assert.assertEquals(response.jsonPath().getString("collection.item.name"),"[Sample GET Request]");
    }

    @org.testng.annotations.Test(description = "delete collection",priority = 4)
    public static void deleteCollection()
    {
        Response response = given().proxy("192.168.100.50",3128)
                .header("Content-Type","application/json")
                .header("X-Api-Key",ApiKey)
                .when().delete("/"+CollectionUid).then().extract().response();

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertNotEquals(response.jsonPath().getString("collection.id"),null);
        Assert.assertNotEquals(response.jsonPath().getString("collection.uid"),null);
    }


}
