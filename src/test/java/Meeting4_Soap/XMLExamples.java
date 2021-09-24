package Meeting4_Soap;
import io.restassured.RestAssured;
import io.restassured.internal.path.xml.NodeChildrenImpl;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;
import static io.restassured.internal.matcher.xml.XmlXsdMatcher.matchesXsd;
import static io.restassured.internal.matcher.xml.XmlXsdMatcher.matchesXsdInClasspath;

import java.io.FileInputStream;
import java.io.IOException;

public class XMLExamples {
    @Test
    void sampleTest() {
        String book = RestAssured.given().when()
                .get("https://chercher.tech/sample/api/books.xml")
                .then().extract().path("**.findAll { it.@category == 'cooking' }.year");
        System.out.println(book);
    }

    //more than one matching element
    @Test
    void sampleTest2() {
        NodeChildrenImpl book = RestAssured.given().when()
                .get("https://chercher.tech/sample/api/books.xml")
                .then().extract().path("bookstore.book.findAll { it.@category == 'cooking' }.price");
        System.out.println(book);
    }
    @Test
    void sampleTest3() {
        NodeChildrenImpl books = RestAssured.given().when()
                .get("https://chercher.tech/sample/api/books.xml")
                .then().extract().path("bookstore.book.title");
        System.out.println(books.get(0));
    }
    @Test
    void sampleTest4() {
        NodeChildrenImpl books = RestAssured.given().when()
                .get("https://chercher.tech/sample/api/books.xml")
                .then().extract().path("bookstore.book.title");

        System.out.println("just single string: "+ books);
        System.out.println("spcific index : "+ books.get(0));
        System.out.println("is empty : "+ books.isEmpty());
        System.out.println("size : "+ books.size());
        System.out.println("list : "+ books.list());
    }
    @Test
    void sampleTest5() {
        NodeChildrenImpl books = RestAssured.given().when()
                .get("https://chercher.tech/sample/api/books.xml")
                .then().extract().path("bookstore.book");

        System.out.println("name : "+ books.get(0).name());
        System.out.println("attributes : "+ books.get(0).attributes());
        System.out.println("getAttribute : "+ books.get(0).getAttribute("category"));
        System.out.println("get : "+ books.get(0).get("year"));
        System.out.println("children : "+ books.get(0).children().list());
        System.out.println("children : "+ books.get(0).children().get("price"));
        System.out.println("getNode : "+ books.get(0).getNode("price").children().get(0));
    }
}
