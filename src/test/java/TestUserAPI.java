import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestUserAPI {

    
    @Test
    public void shouldTestUserAPI(){
        //Arrange
        Response response = RestAssured.get("https://reqres.in/api/users?page=2");

        //Act
        int statuscode = response.getStatusCode();
        System.out.println(statuscode);
      String body =response.getBody().asPrettyString();
        System.out.println(body);

        //Assert
        Assert.assertEquals(statuscode,200);

    }
}
