package rahulAPI;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class RahulAPI {
  private static final Properties p = new Properties();
  public static final String logInUrl =
      "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss";
  public static final String rahulHost = "rahulHost";
  public static final String accessToken = "access_token";
  public static final String accessTokenValue = null;
  public static final String scope = "scope";
  public static final String scopeValue = "https://www.googleapis.com/auth/userinfo.email";
  public static final String authUrl = "auth_url";
  public static final String authUrlValue = "https://accounts.google.com/o/oauth2/v2/auth";
  public static final String clientId = "client_id";
  public static final String clientIdValue =
      "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com";
  public static final String responseType = "response_type";
  public static final String responseTypeValue = "code";
  public static final String redirectUri = "redirect_uri";
  public static final String redirectUriValue = "https://rahulshettyacademy.com/getCourse.php";
  public static final String state = "state";
  public static final String stateValue = "verifyfjdss";
  public static final String code = "code";
  public static final String codeValue =
      "4%2FvQGqRsGjrrrS6Iq3BTQkK9vHDt9dcEBR23xZh_6AlGLHCfAv3O8KYo6_MySuLYDihk9ubmof8cIOxzxz2HYrY18";
  public static final String clientSecret = "client_secret";
  public static final String clientSecretValue = "erZOWM9g3UtwNRj340YYaK_W";
  public static final String grantType = "grant_type";
  public static final String grantTypeAuthorizationCode = "authorization_code";
  public static final String googleApisOAuthToken = "https://www.googleapis.com/oauth2/v4/token";

  public static String getAuthorizationCode() {
    //    System.setProperty(
    //        "webdriver.chrome.driver", "C:\\WebDrivers\\chromedriver_win32\\chromedriver.exe");
    //    ChromeOptions co = new ChromeOptions();
    //    co.addArguments("--incognito");
    //    WebDriver driver = new ChromeDriver(co);
    //    driver.get(logInUrl);
    //
    // driver.findElement(By.xpath("//input[@type='email']")).sendKeys("tony.price1387@gmail.com");
    //    driver.findElement(By.id("identifierNext")).click();
    //    driver.findElement(By.xpath("//input[@type='password']")).sendKeys("TonyPr1c3");
    //    driver.findElement(By.id("passwordNext"));
    String url =
        "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvgFxpZbhwLAjqR8S5SudQvOwhw7zwjXhS0vwjHAgC2Grxm9HDsihpD-ipY4gdQURdR3Pk58Kfpy2bX0G_Bdgga8&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none&session_state=5b75ba6d170e3cd6cd66c0c28625670a98e14850..ec6e#";
    String partialUrl = url.split("code=")[1];
    String codeValue = partialUrl.split("&scope")[0];
    System.out.println(codeValue);

    return codeValue;
  }

  public static String getOAuthToken() {
    String accessTokenResponse =
        given()
            .urlEncodingEnabled(false)
            .queryParams(code, getAuthorizationCode())
            .queryParams(clientId, clientIdValue)
            .queryParams(clientSecret, clientSecretValue)
            .queryParams(redirectUri, redirectUriValue)
            .queryParams(grantType, grantTypeAuthorizationCode)
            .when()
            .log()
            .all()
            .post(googleApisOAuthToken)
            .asString();

    JsonPath jsonPath = new JsonPath(accessTokenResponse);
    String oAuthToken = jsonPath.getString(accessToken);
    System.out.println(oAuthToken);
    return oAuthToken;
  }

  @Test
  public void getRahulCoursesOauth() {
    String response =
        given()
            .queryParam(
                accessToken,
                "ya29.ImC7ByZ91KiQExJmSQDezjG_3wTPgZ6YnUP48Ca8Ws_k1Tpmi_cLib4ix3Jkt5p9lo3mnOmv8BL8Ncenki-vZ_4DFYIn-MmDXGyLojOKIClFWJsGhgmhdaglK7AxLD3Uur0")
            .when()
            .get(redirectUriValue)
            .asString();

    System.out.println(response);
  }


}
