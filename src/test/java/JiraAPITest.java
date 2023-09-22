import Payload.JiraAPIPayload;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraAPITest {
    SessionFilter sessionFilter = new SessionFilter();

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void shouldTestLogin() {
        given().log().all().header("Content-Type", "application/json")
                .body(JiraAPIPayload.loginCredentials())
                .filter(sessionFilter)
                .when().post("/rest/auth/1/session")
                .then().assertThat().statusCode(200).log().all();
        
    }

    @Test(dependsOnMethods = "shouldTestLogin")
    public void shouldTestAddComment() {
        String comment = "this is my 8th comment";

        String response = given().pathParam("key", "10007").header("Content-Type", "application/json")
                .body(JiraAPIPayload.addComment(comment))
                .filter(sessionFilter)
                .when().post("/rest/api/2/issue/{key}/comment")
                .then().assertThat().statusCode(201)
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        String commentId = jsonPath.getString("id");
        System.out.println(commentId);
    }

    @Test(dependsOnMethods = "shouldTestAddComment")
    public void shouldTestGetIssue() {
        String issueResponse = given().pathParam("key", "10007").queryParam("fields", "comment")
                .filter(sessionFilter)
                .when().get("/rest/api/2/issue/{key}")
                .then().extract().response().asString();

        JsonPath jsonPath = new JsonPath(issueResponse);
        int commentCount = jsonPath.getInt("fields.comment.comments.size()");
        String expectedComment = "this is my 8th comment";

        for (int i = 0; i < commentCount; i++) {
            String actualComment = jsonPath.getString("fields.comment.comments[" + i + "].body");
            if (expectedComment.equals(actualComment)) {
                Assert.assertEquals(actualComment, expectedComment);
                break;
            }
        }
    }

    @Test(dependsOnMethods = "shouldTestLogin")
    public void shouldTestAddAttachments() {
        given().pathParam("key", "10007")
                .header("X-Atlassian-Token", "no-check")
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("/Users/testvagrant/Baganna/API_Automation/src/main/resources/Image.png"))
                .filter(sessionFilter)
                .when().post("/rest/api/2/issue/{key}/attachments")
                .then().assertThat().statusCode(200).log().all();
    }
}

