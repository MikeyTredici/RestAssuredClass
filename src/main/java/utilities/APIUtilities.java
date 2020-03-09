package utilities;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class APIUtilities {
    private static final Properties p = new Properties();

    public static String generateStringFromResource(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static XmlPath rawToXml(Response response) {
        String stringResponse = response.asString();
        return new XmlPath(stringResponse);
    }

    public static JsonPath rawToJson(Response response) {
        String stringResponse = response.asString();
        return new JsonPath(stringResponse);
    }
}
