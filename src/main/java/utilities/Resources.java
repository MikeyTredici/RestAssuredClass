package utilities;

public class Resources {
  public static String getNearbySearch() {
    return "/maps/api/place/nearbysearch/";
  }

  public static String getAddPlace() {
    return "/maps/api/place/add/";
  }

  public static String getDeletePlace() {
    return "/maps/api/place/delete/";
  }

  public static String getAddBook() {
    return "/Library/Addbook.php";
  }

  public static String getDeleteBook() {
    return "Library/DeleteBook.php";
  }

  public static String jiraLogIn() {
    return "rest/auth/1/session";
  }

  public static String jiraCreateIssue() {
    return "rest/api/2/issue";
  }

  public static String jiraDeleteIssue(String issueIdOrKey) {
    return "rest/api/2/issue/" + issueIdOrKey;
  }

  public static String jiraAddComment(String issueIdOrKey) {
    return "rest/api/2/issue/" + issueIdOrKey + "/comment";
  }

  public static String jiraUpdateComment(String issueIdOrKey, String commentId) {
    return "rest/api/2/issue/" + issueIdOrKey + "/comment/" + commentId;
  }
}
