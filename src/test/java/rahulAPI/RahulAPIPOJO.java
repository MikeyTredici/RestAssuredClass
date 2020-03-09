package rahulAPI;

import io.restassured.parsing.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojoRahul.API;
import pojoRahul.GetCourse;
import pojoRahul.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class RahulAPIPOJO {
  private static final Properties p = new Properties();

  public static final String accessToken = "access_token";
  public static final String accessTokenValue =
      "ya29.a0Adw1xeXCEA6kcR0XD1a9Wr-77nPT-2FOLbod38zZqlpa-uyS-BIS79VDCh8edoRqAqjBgnHHg2Kgmx2Mx-ZEgLdw-M5mL-_0Rsh8CDeHNGDtFKzhO13U_EnEQfJzIfFBYkIpZFH88Fsigjf50QhqdmeAU4Q5-DoQs0e_JA";
  public static final String redirectUriValue = "https://rahulshettyacademy.com/getCourse.php";
  public static final List<String> webCourses = Arrays.asList("Selenium Webdriver Java", "Cypress", "Protractor");


  @Test
  public static void getPOJOValues() {
    // If content type is application/json, the defaultParser is not needed
    GetCourse getCourse = given()
            .queryParam(accessToken, accessTokenValue)
            .expect()
            .defaultParser(Parser.JSON)
            .when()
            .get(redirectUriValue)
            .as(GetCourse.class);

    // Printing LinkedIn URL and Instructor name
    System.out.println(getCourse.getLinkedIn());
    System.out.println(getCourse.getInstructor());

    // Printing Price of the SoapUI Webservices testing
    List<API> apiCourses = getCourse.getCourses().getApi();
    for (int i = 0; i < apiCourses.size(); i++) {
        if (getCourse.getCourses().getApi().get(i).getCourseTitle().equals("SoapUI Webservices testing")) {
        System.out.println(getCourse.getCourses().getApi().get(i).getPrice());
        break;
        }
    }

    // Saving list of Web Automation courses and asserting they are all present``````````````````````````1`````````````````````````````````````````````

    List<WebAutomation> webAutomationCourses = getCourse.getCourses().getWebAutomation();
    ArrayList<String> listOfWebCourses = new ArrayList<>();
    for (int i = 0; i < webAutomationCourses.size(); i++) {
        listOfWebCourses.add(i, getCourse.getCourses().getWebAutomation().get(i).getCourseTitle());
    }
      Assert.assertEquals(webCourses, listOfWebCourses);
  }
}
