package libraryAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.APIUtilities;
import utilities.Payloads;
import utilities.Resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class LibraryAPI {
  private static final Properties p = new Properties();
  private static final String contentType = "Content-Type";
  private static final String applicationJson = "application/json";
  private static final String idString = "ID";
  private static final String msgString = "msg";

  @Test(dataProvider = "Books Data")
  public void addAndDeleteBook(String isbn, String aisle) throws IOException {
    FileInputStream fis =
        new FileInputStream(System.getProperty("user.dir") + "\\data\\data.properties");
    p.load(fis);

    RestAssured.baseURI = p.getProperty("iplibrary");

    Response addResponse =
        given()
            .header(contentType, applicationJson)
            .body(Payloads.getAddBook(isbn, aisle))
            .when()
            .post(Resources.getAddBook())
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .response();

    JsonPath addPath = APIUtilities.rawToJson(addResponse);
    String id = addPath.get(idString);
    System.out.println(id);

    Response deleteResponse =
        given()
            .header(contentType, applicationJson)
            .body(Payloads.getDeleteBook(id))
            .when()
            .post(Resources.getDeleteBook())
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .response();

    JsonPath deletePath = APIUtilities.rawToJson(deleteResponse);
    String message = deletePath.get(msgString);
    System.out.println(message);
  }

  @Test
  public void staticJson() throws IOException {
    FileInputStream fis =
        new FileInputStream(System.getProperty("user.dir") + "\\data\\data.properties");
    p.load(fis);

    RestAssured.baseURI = p.getProperty("iplibrary");

    Response addResponse =
        given()
            .header(contentType, applicationJson)
            .body(
                APIUtilities.generateStringFromResource(
                        System.getProperty("user.dir") + "\\data\\addBook.json"))
            .when()
            .post(Resources.getAddBook())
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .response();

    JsonPath addPath = APIUtilities.rawToJson(addResponse);
    String id = addPath.get(idString);
    System.out.println(id);

    Response deleteResponse =
        given()
            .header(contentType, applicationJson)
            .body(Payloads.getDeleteBook(id))
            .when()
            .post(Resources.getDeleteBook())
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .response();

    JsonPath deletePath = APIUtilities.rawToJson(deleteResponse);
    String message = deletePath.get(msgString);
    System.out.println(message);
  }

  @DataProvider(name = "Books Data")
  public Object[][] getData() throws IOException {
    return new Object[][] {{"abcd", "123"}, {"efgh", "456"}, {"ijkl", "789"}};
  }
}
