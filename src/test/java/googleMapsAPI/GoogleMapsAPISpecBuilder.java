package googleMapsAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import pojoGoogleMaps.AddPlace;
import pojoGoogleMaps.Location;
import utilities.Resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class GoogleMapsAPISpecBuilder {
    private static final Properties p = new Properties();
    public static final String json = "json";

    @Test
    public void specBuilder() throws IOException {
        FileInputStream fis =
                new FileInputStream(System.getProperty("user.dir") + "\\data\\data.properties");

        p.load(fis);

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


        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri(p.getProperty("iphost"))
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON)
                .build();

        RequestSpecification requestAddPlace = given()
                .spec(req)
                .body(ap);

        ResponseSpecification res = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        Response response = requestAddPlace
                .when()
                .post(Resources.getAddPlace() + json)
                .then()
                .spec(res)
                .extract()
                .response();

        String placeAdded = response.asString();
        System.out.println(placeAdded);
    }
}
