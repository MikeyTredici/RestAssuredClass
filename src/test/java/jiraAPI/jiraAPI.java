package jiraAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.APIUtilities;
import utilities.Payloads;
import utilities.Resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class jiraAPI {
  private static final Properties p = new Properties();
  public static final String jiraHost = "jiraHost";
  public static final String username = "Tredici";
  public static final String password = "keSwaS@m13";
  public static final String contentType = "Content-Type";
  public static final String applicationJson = "application/json";
  public static final String sessionValue = "session.value";
  public static final String cookie = "Cookie";
  public static final String jiraSessionId = "JSESSIONID=";
  public static final String projectKey = "TRED";
  public static final String issueTypeBug = "Bug";
  public static final String summary = "Bug From RestAssured Automation";
  public static final String description =
      "This is a bug created directly from the API using RestAssured";
  public static final String id = "id";
  public static final String comment = "Comment to update";
  public static final String updatedComment = "Updated Comment";

  @BeforeTest
  public void getProperty() throws IOException {
    FileInputStream fis =
        new FileInputStream(System.getProperty("user.dir") + "\\data\\data.properties");
    p.load(fis);

    RestAssured.baseURI = p.getProperty(jiraHost);
  }

  public static String logInAndGetJiraSessionId() {
    Response response =
        given()
            .header(contentType, applicationJson)
            .body(Payloads.jiraLogIn(username, password))
            .when()
            .post(Resources.jiraLogIn())
            .then()
            .statusCode(200)
            .extract()
            .response();

    JsonPath jsonPath = APIUtilities.rawToJson(response);
    String sessionId = jsonPath.get(sessionValue);
    System.out.println(sessionId);

    return sessionId;
  }

  @Test
  public void createIssueAndGetId() {
    Response response =
        given()
            .header(contentType, applicationJson)
            .header(cookie, jiraSessionId + logInAndGetJiraSessionId())
            .body(Payloads.jiraCreateIssue(projectKey, summary, issueTypeBug, description))
            .when()
            .post(Resources.jiraCreateIssue())
            .then()
            .statusCode(201)
            .extract()
            .response();

    JsonPath jsonPath = APIUtilities.rawToJson(response);
    String bugId = jsonPath.get(id);
    System.out.println(bugId);
  }

  @Test
  public void addingComment() {
    Response response =
        given()
            .header(contentType, applicationJson)
            .header(cookie, jiraSessionId + logInAndGetJiraSessionId())
            .body(Payloads.jiraAddAndUpdateComment(comment))
            .when()
            .post(Resources.jiraAddComment("10026"))
            .then()
            .statusCode(201)
            .extract()
            .response();

    JsonPath jsonPath = APIUtilities.rawToJson(response);
    String commentId = jsonPath.get(id);
    System.out.println(commentId);
  }

  @Test
  public void updatingComment() {
    given()
        .header(contentType, applicationJson)
        .header(cookie, jiraSessionId + logInAndGetJiraSessionId())
        .body(Payloads.jiraAddAndUpdateComment(updatedComment))
        .when()
        .put(Resources.jiraUpdateComment("10026", "10020"))
        .then()
        .assertThat()
        .statusCode(200);
  }
}
