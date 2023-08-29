import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class TestGetCourseAPI {
    @Test
    public void shouldGetCourses() {
        String accessTokenResponse = given().queryParam("code", "")
                .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type", "authorization_code")
                .when().post("https://www.googleapis.com/oauth2/v4/token").asString();
        JsonPath jsonPath = new JsonPath(accessTokenResponse);

        String accessToken = jsonPath.getString("access_token");

        String response = given().queryParam("access_token",accessToken)
                .log().all()
                .when().get("https://rahulshettyacademy.com/getCourse.php")
                .then().assertThat().statusCode(200).extract().response()
                .asString();

    }
}
