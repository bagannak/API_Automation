import Payload.JiraAPIPayload;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraAPITest {
    String response;
    JsonPath jsonPath;
    String commentId;
    String comment;
    SessionFilter sessionFilter = new SessionFilter();
    @BeforeMethod
    public  void setUp(){
        RestAssured.baseURI="http://localhost:8080";

    }
    @Test(priority = 1)
    public void shouldTestLogin(){
        //Arrange
       response= given().log().all().header("Content-Type","application/json")
                .body(JiraAPIPayload.loginCredentials()).filter(sessionFilter)
                .when().post("/rest/auth/1/session")
                .then().assertThat().statusCode(200).log().all()
                .extract().response().asString();
        //Act
        //Assert
    }
 @Test
 public void shouldTestAddComment(){
     //Arrange
//     given().log().all().header("Content-Type","application/json")
//             .body(JiraAPIPayload.loginCredentials()).filter(sessionFilter)
//             .when().post("/rest/auth/1/session")
//             .then().assertThat().statusCode(200).log().all()
//             .extract().response().asString();
      comment = "this is my 7th comment";

     //Act
     response = given().pathParam("key", "10007").header("Content-Type", "application/json")
             .body(JiraAPIPayload.addComment(comment))
             .filter(sessionFilter)
             .when().post("/rest/api/2/issue/{key}/comment")
             .then().assertThat().statusCode(201)
             .extract().response().asString();
     jsonPath = new JsonPath(response);
     commentId = jsonPath.getString("id");
     System.out.println(commentId);
     //Assert


 }
 @Test
 public void shouldTestAddAttachments(){
     //Arrange
     given().header("Content-Type","application/json")
             .body(JiraAPIPayload.loginCredentials()).filter(sessionFilter)
             .when().post("/rest/auth/1/session")
             .then().assertThat().statusCode(200).log().all()
             .extract().response().asString();
     //Act
     //Add attachments
     given().pathParam("key", "10007")
             .header("X-Atlassian-Token", "no-check").header("Content-Type","multipart/form-data")
             .multiPart("file",new File("/Users/testvagrant/Baganna/API_Automation/src/main/resources/Image.png"))
             .filter(sessionFilter)
             .when().post("/rest/api/2/issue/{key}/attachments")
             .then().assertThat().statusCode(200).log().all();


     //Assert
 }
 @Test(dependsOnMethods = "shouldTestAddComment")
 public void shouldTestGetIssue(){
     //Arrange
//     given().relaxedHTTPSValidation().header("Content-Type","application/json")
//             .body(JiraAPIPayload.loginCredentials()).filter(sessionFilter)
//             .when().post("/rest/auth/1/session")
//             .then().assertThat().statusCode(200).log().all()
//             .extract().response().asString();
     //Act
     String issueResponse=given().pathParam("key", "10007").queryParam("fields","comment")
             .filter(sessionFilter)
             .when().get("/rest/api/2/issue/{key}")
             .then().extract().response().asString();
     jsonPath = new JsonPath(issueResponse);
     int commentCount = jsonPath.getInt("fields.comment.comments.size()");
     //Assert
     for (int i=0;i<commentCount;i++){
         if(commentId.equals(jsonPath.getString("fields.comment.comments["+i+"].id"))){
             String actualComment = jsonPath.getString("fields.comment.comments["+i+"].body");
            Assert.assertEquals(actualComment,comment);
         }
     }
 }

}
