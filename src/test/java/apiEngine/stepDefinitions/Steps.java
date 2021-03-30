package apiEngine.stepDefinitions;


import apiEngine.model.Book;
import apiEngine.model.requests.AddBooksRequest;
import apiEngine.model.requests.AuthorizationRequest;
import apiEngine.model.requests.ISBN;
import apiEngine.model.requests.RemoveBookRequest;
import apiEngine.model.response.Books;
import apiEngine.model.response.Token;
import apiEngine.model.response.UserAccount;
import org.junit.Assert;

import apiEngine.EndPoints;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.response.Response;
import org.testng.annotations.Test;

public class Steps {

    private static final String USER_ID = "9b5f49ab-eea9-45f4-9d66-bcf56a531b85";
    private static Response response;
    private static Token tokenResponse;
    private static Book book;


    @Given("I am an authorized user")
    public void iAmAnAuthorizedUser() {
        AuthorizationRequest authRequest = new AuthorizationRequest("TOOLSQA-Test", "Test@@123");
        response = EndPoints.authenticateUser(authRequest);
        tokenResponse = response.getBody().as(Token.class);
    }

    @Given("A list of books are available")
    @Test
    public void listOfBooksAreAvailable() {
        response = EndPoints.getBooks();
        Books books = response.getBody().as(Books.class);
        book = books.books.get(0);
        System.out.println(book.isbn);
    }

    @When("I add a book to my reading list")
    public void addBookInList() {
        ISBN isbn = new ISBN(book.isbn);
        AddBooksRequest addBooksRequest = new AddBooksRequest(USER_ID, isbn);
        response = EndPoints.addBook(addBooksRequest, tokenResponse.token);
    }

    @Then("The book is added")
    public void bookIsAdded() {
        Assert.assertEquals(201, response.getStatusCode());

        UserAccount userAccount = response.getBody().as(UserAccount.class);

        Assert.assertEquals(USER_ID, userAccount.userID);
        Assert.assertEquals(book.isbn, userAccount.books.get(0).isbn);
    }

    @When("I remove a book from my reading list")
    public void removeBookFromList() {
        RemoveBookRequest removeBookRequest = new RemoveBookRequest(USER_ID, book.isbn);
        response = EndPoints.removeBook(removeBookRequest, tokenResponse.token);
    }

    @Then("The book is removed")
    public void bookIsRemoved() {
        Assert.assertEquals(204, response.getStatusCode());

        response = EndPoints.getUserAccount(USER_ID, tokenResponse.token);
        Assert.assertEquals(200, response.getStatusCode());

        UserAccount userAccount = response.getBody().as(UserAccount.class);
        Assert.assertEquals(0, userAccount.books.size());
    }
}