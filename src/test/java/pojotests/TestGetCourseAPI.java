package pojotests;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import pojo.getcourse.Courses;
import pojo.getcourse.GetCourse;
import pojo.getcourse.WebAutomation;

import java.util.List;

import static io.restassured.RestAssured.*;

public class TestGetCourseAPI {
    @Test
    public void shouldGetCourses() throws InterruptedException {

       WebDriver driver = new ChromeDriver();
        String email = "bagannak@testvagrant.com";
        String password = "baganna$321";
       driver.get("https://accounts.google.com/o/oauth2/v2/auth/identifier?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&state=verifyfjdss&service=lso&o2v=2&flowName=GeneralOAuthFlow");

        driver.findElement(By.cssSelector("input[type=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("input[type=email]")).sendKeys(Keys.ENTER);
        Thread.sleep(6000);
        driver.findElement(By.cssSelector("input[type=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("input[type=password]")).sendKeys(Keys.ENTER);
        Thread.sleep(3000);

        String url;
        url = driver.getCurrentUrl();
        String partialUrl = url.split("code=")[1];
        String code =partialUrl.split("&scope")[0];
        String accessTokenResponse = given().urlEncodingEnabled(false)
                .queryParam("code", code)
                .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type", "authorization_code")
                .when().post("https://www.googleapis.com/oauth2/v4/token").asString();
        JsonPath jsonPath = new JsonPath(accessTokenResponse);

        String accessToken = jsonPath.getString("access_token");

        GetCourse jsonResponse = given().queryParam("access_token",accessToken)
                .expect().defaultParser(Parser.JSON)
                .when().get("https://rahulshettyacademy.com/getCourse.php")
                .then().assertThat().statusCode(200).extract().response()
                .as(GetCourse.class);
        Courses courses = jsonResponse.getCourses();
        System.out.println(courses.getWebAutomation().get(0).getCourseTitle()+"\n"+courses.getApi().get(1).getCourseTitle());

        List<WebAutomation> webAutomationCourses = courses.getWebAutomation();
        webAutomationCourses.forEach((course)-> System.out.println(course.getCourseTitle()));
        driver.quit();

    }
}
