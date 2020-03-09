package googleMapsAPI;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojoGoogleMaps.AddPlace;
import pojoGoogleMaps.Location;
import utilities.APIUtilities;
import utilities.Payloads;
import utilities.Resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GoogleMapsAPIPlusPOJO {
  private static final Properties p = new Properties();
  public static final String overQueryLimitStatus = "OVER_QUERY_LIMIT";
  public static final String server = "scaffolding on HTTPServer2";
  public static final String okStatus = "OK";
  public static final String alreadyDeletedMsg =
      "Delete operation failed, looks like the data doesn't exists";
  public static final String json = "json";
  public static final String xml = "xml";

  @Test(priority = 1)
  public void getTest() throws IOException {
    FileInputStream fis =
        new FileInputStream(System.getProperty("user.dir") + "\\data\\data.properties");

    p.load(fis);
    RestAssured.baseURI = p.getProperty("host");

    given()
        .param("location", "-33.8688197,151.2092955")
        .param("radius", "500")
        .param("key", p.getProperty("googleKey"))
        .when()
        .get(Resources.getNearbySearch() + json)
        .then()
        .assertThat()
        .statusCode(200)
        .and()
        .contentType(ContentType.JSON)
        .and()
        .body("status", equalTo(overQueryLimitStatus))
        .and()
        .header("Server", server);
  }

  @Test(priority = 2)
  public void postTest() throws IOException {
    FileInputStream fis =
        new FileInputStream(System.getProperty("user.dir") + "\\data\\data.properties");

    p.load(fis);
    RestAssured.baseURI = p.getProperty("iphost");

    // Add place and get Place ID for the delete tests
    Response add =
        given()
            .queryParam("key", p.getProperty("ipkey"))
            .body(Payloads.getAddPlace())
            .when()
            .post(Resources.getAddPlace() + json)
            .then()
            .assertThat()
            .body("status", equalTo(okStatus))
            .extract()
            .response();

    String addString = add.asString();
    JsonPath js = new JsonPath(addString);
    String placeID = js.get("place_id");

    // Delete first time
    given()
        .queryParam("key", p.getProperty("ipkey"))
        .body("{" + "\"place_id\" : \"" + placeID + "\"" + "}")
        .when()
        .post(Resources.getDeletePlace() + json)
        .then()
        .assertThat()
        .body("status", equalTo(okStatus));

    // Attempt to delete same Place ID again
    given()
        .queryParam("key", p.getProperty("ipkey"))
        .body("{" + "\"place_id\" : \"" + placeID + "\"" + "}")
        .when()
        .post(Resources.getDeletePlace() + json)
        .then()
        .assertThat()
        .body("msg", equalTo(alreadyDeletedMsg));
  }

  @Test(priority = 3)
  public void validatingXmlResponse() throws IOException {
    String postData =
        APIUtilities.generateStringFromResource(
            System.getProperty("user.dir") + "\\data\\postdata.xml");
    FileInputStream fis =
        new FileInputStream(System.getProperty("user.dir") + "\\data\\data.properties");

    p.load(fis);
    RestAssured.baseURI = p.getProperty("iphost");

    // TO DO
    Response res =
        given()
            .queryParam("key", p.getProperty("ipkey"))
            .body(postData)
            .when()
            .post(Resources.getAddPlace() + xml)
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .contentType(ContentType.XML)
            .extract()
            .response();

    XmlPath xmlPath = APIUtilities.rawToXml(res);

    String placeID = xmlPath.get("PlaceAddResponse.place_id");
    System.out.println(placeID);
  }

  @Test(priority = 4)
  public void gettingMultipleResponses() throws IOException {
    FileInputStream fis =
        new FileInputStream(System.getProperty("user.dir") + "\\data\\data.properties");

    p.load(fis);
    RestAssured.baseURI = p.getProperty("host");

    Response response =
        given()
            .param("location", "-33.8688197,151.2092955")
            .param("radius", "500")
            .param("key", p.getProperty("googleKey"))
            .log()
            .all()
            .when()
            .get(Resources.getNearbySearch() + json)
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .contentType(ContentType.JSON)
            .and()
            .body("status", equalTo(okStatus))
            .and()
            .header("Server", server)
            .log()
            .body()
            .extract()
            .response();

    JsonPath jsonPath = APIUtilities.rawToJson(response);
    int count = jsonPath.get("results.size()");
    System.out.println(count);

    for (int i = 0; i < count; i++) {
      String placeName = jsonPath.get("results[" + i + "].name");
      System.out.println(placeName);
    }
  }

  @Test
  public void deserialization() throws IOException {
    FileInputStream fis =
            new FileInputStream(System.getProperty("user.dir") + "\\data\\data.properties");

    p.load(fis);
    RestAssured.baseURI = p.getProperty("iphost");

    AddPlace ap = new AddPlace();
    ap.setAccuracy(50);
    ap.setAddress("29, side layout, cohen 09");
    ap.setLanguage("French-IN");
    ap.setPhone_number("(+91) 983 893 3937");
    ap.setWebsite("http://google.com");
    ap.setName("Frontline house");
    List<String> types = new ArrayList<>();
    types.add("show park");
    types.add("shop");
    ap.setTypes(types);
    Location location = new Location();
    location.setLat(-38.383494);
    location.setLng(33.427362);
    ap.setLocation(location);


    Response response = given().
            queryParam("key", "qaclick123")
            .body(ap)
            .when()
            .post(Resources.getAddPlace() + json)
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .response();

    String placeAdded = response.asString();
    System.out.println(placeAdded);
  }

}
