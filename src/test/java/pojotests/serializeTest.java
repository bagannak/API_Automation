package pojotests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.maps.AddPlace;
import pojo.maps.Location;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

public class serializeTest {

    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

		AddPlace addPlace = getPlace();
		Response res = given().log().all().queryParam("key", "qaclick123")
                .body(addPlace)
                .when().post("/maps/api/addPlace/add/json").
                then().assertThat().statusCode(200).extract().response();

        String responseString = res.asString();
        System.out.println(responseString);


    }

	private static AddPlace getPlace() {
		AddPlace addPlace = new AddPlace();
		addPlace.setAccuracy(50);
		addPlace.setAddress("29, side layout, cohen 09");
		addPlace.setLanguage("French-IN");
		addPlace.setPhone_number("(+91) 983 893 3937");
		addPlace.setWebsite("https://rahulshettyacademy.com");
		addPlace.setName("Frontline house");
		List<String> type = new ArrayList<>();
		type.add("shoe park");
		type.add("shop");

		addPlace.setTypes(type);
		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);

		addPlace.setLocation(location);
		return addPlace;
	}

}
