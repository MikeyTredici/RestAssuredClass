package utilities;

public class Payloads {
  public static String getAddPlace() {
    return "{"
        + "\"location\" : {"
        + "\"lat\" : -38.383494,"
        + "\"lng\" : 33.427362"
        + "},"
        + "\"accuracy\" : 50,"
        + "\"name\" : \"Frontline house\","
        + "\"phone_number\" : \"(+91) 983 893 3937\","
        + "\"address\" : \"29,  side layout, cohen 09\","
        + "\"types\" : [\"show park\", \"shop\"],"
        + "\"website\" : \"http://google.com\","
        + "\"language\" : \"French-IN\""
        + "}";
  }

  public static String getAddBook(String isbn, String aisle) {
    return "{" +
            "\"name\":\"Learn Appium Automation with Java\"," +
            "\"isbn\":\"" + isbn + "\"," +
            "\"aisle\":\"" + aisle + "\"," +
            "\"author\":\"John Doe\"" +
            "}";
  }

  public static String getDeleteBook(String id) {
    return "{\n" +
            "\"ID\" : \"" + id + "\"\n" +
            "}";
  }

  public static String jiraLogIn(String username, String password) {
    return "{ \n" +
            "\"username\": \"" + username + "\", \n" +
            "\"password\": \"" + password + "\"\n" +
            "}";
  }

  public static String jiraCreateIssue(String projectKey, String summary, String issueType, String description) {
    return "{\n" +
            "    \"fields\": {\n" +
            "        \"project\": {\n" +
            "            \"key\": \"" + projectKey + "\"\n" +
            "        },\n" +
            "        \"summary\": \"" + summary + "\",\n" +
            "        \"issuetype\": {\n" +
            "            \"name\": \"" + issueType + "\"\n" +
            "        },\n" +
            "        \"description\": \"" + description + "\"\n" +
            "        }\n" +
            "}";
  }

  public static String jiraAddAndUpdateComment(String comment) {
    return "{\n" +
            "    \"body\": \"" + comment + "\",\n" +
            "    \"visibility\": {\n" +
            "        \"type\": \"role\",\n" +
            "        \"value\": \"Administrators\"\n" +
            "    }\n" +
            "}";
  }
}
