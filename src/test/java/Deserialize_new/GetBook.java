package Deserialize_new;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class GetBook {
    @Test
    public void getBook() {
        Book response= RestAssured.given().when()
                .get("https://bookstore.toolsqa.com/BookStore/v1/Book?ISBN=9781449325862")
                .as(Book.class);
        System.out.println(response.pages);
    }
    @Test
    public void getBookList() {

        BookList response= RestAssured.given().when()
                .get("https://bookstore.toolsqa.com/BookStore/v1/Book?ISBN=9781449325862")
                .as(BookList.class);
        System.out.println(response.books.get(0).pages);
    }
    @Test
    public void getBookWithSerialization() {
        Request request=new Request();

       RestAssured.given().queryParam("ISBN",request.ISBN, ObjectMapperType.JAXB).when()
                .get("https://bookstore.toolsqa.com/BookStore/v1/Book").then().log().all();



    }
}

