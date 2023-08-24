import Payload.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class TestMapAPI {
    String placeId;
    String response;
    JsonPath jsonPath;
    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI ="https://rahulshettyacademy.com";

    }
    @Test(priority = 1)
    public void shouldTestPostAPI(){
        //Arange
       response = given().log().all()
        .queryParam("key","qaclick123")
               .header("Content-Type","application/json")
                .body(Payload.addPlace())
                .when().post("maps/api/place/add/json")
                .then()
                .assertThat()
                .statusCode(200)
                .body("scope",equalTo("APP"))
                .header("Connection","Keep-Alive")
                .extract().response().asPrettyString();
        //Act
//        System.out.println(response);
        jsonPath= new JsonPath(response);
        placeId = jsonPath.getString("place_id");
//        System.out.println("Place Id : "+placeId);
        String status = jsonPath.getString("status");
        //Assert
        Assert.assertEquals(status,"OK","status is not ok");
    }
    @Test(dependsOnMethods = "shouldTestPostAPI",priority = 2)
    public void shouldTestPutAPI(){
        //Arange
      response = given().log().all()
                .queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(Payload.updatePlace(placeId))
                .when().put("maps/api/place/update/json")
                .then().assertThat().statusCode(200)
                .extract().response().asPrettyString();

        //Act
         jsonPath = new JsonPath(response);
        String status = jsonPath.getString("msg");
//        System.out.println(status);
        //Assert
        Assert.assertEquals(status,"Address successfully updated");
    }
    @Test(dependsOnMethods = "shouldTestPostAPI",priority = 3)
    public void shouldTestGetAPI(){
        //Arange
        response = given().log().all()
                .queryParam("key","qaclick123").queryParam("place_id",placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().statusCode(200).body("address",equalTo("0 winter walk, USA"))
                .extract().response().asPrettyString();
        //Act

        //Assert
    }
}
