import Payload.MapAPIPayload;
import convertrawtojson.RawToJson;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    public void shouldTestPostAPI() throws IOException {
        //Arange
       response = given().log().all()
        .queryParam("key","qaclick123")
               .header("Content-Type","application/json")
//                .body(MapAPIPayload.addPlace())
               //providing json data to the body from external file
               .body(new String(Files.readAllBytes(Paths.get("/Users/testvagrant/Desktop/newPlace.json"))))
                .when().post("maps/api/place/add/json")
                .then()
                .assertThat()
                .statusCode(200)
                .body("scope",equalTo("APP"))
                .extract().response().asPrettyString();
        //Act
        jsonPath= RawToJson.rawToJson(response);
        placeId = jsonPath.getString("place_id");

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
                .body(MapAPIPayload.updatePlace(placeId))
                .when().put("maps/api/place/update/json")
                .then().assertThat().statusCode(200)
                .extract().response().asPrettyString();

        //Act
         jsonPath = RawToJson.rawToJson(response);
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
