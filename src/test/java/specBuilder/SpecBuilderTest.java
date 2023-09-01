package specBuilder;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import pojo.maps.AddPlace;
import pojo.maps.Location;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {

    private AddPlace getPlace() {
        AddPlace addPlace = new AddPlace();
        addPlace.setAccuracy(50);
        addPlace.setAddress("29, side layout, cohen 09");
        addPlace.setLanguage("French-IN");
        addPlace.setPhone_number("(+91) 983 893 3937");
        addPlace.setWebsite("https://rahulshettyacademy.com");
        addPlace.setName("Frontline house");
        List<String> myList = new ArrayList<String>();
        myList.add("shoe park");
        myList.add("shop");

        addPlace.setTypes(myList);
        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        addPlace.setLocation(location);
        return addPlace;
    }
    @Test
    public void shouldTestAddPlace(){
        AddPlace place = getPlace();

        RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();


        ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        RequestSpecification request = given().spec(requestSpecification)
                .body(place);

        Response response = request.when().post("/maps/api/place/add/json").
                then().spec(responseSpecification).extract().response();

        String responseString = response.asPrettyString();
        System.out.println(responseString);
    }
}
