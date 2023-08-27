import Payload.LibraryAPIPayload;
import convertrawtojson.RawToJson;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class TestLibraryAPI {
    String response;
    JsonPath jsonPath;
    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI ="http://216.10.245.166";

    }
    @DataProvider(name = "BooksData")
    public Object[][] bookDataProvider(){
        return new Object[][]{
                {"abd2","171"},
                {"VR2","181"},
                {"BK2","121"},
                {"",""}
        };
    }
    @Test(dataProvider = "BooksData", priority = 1)
    public void shouldTestPostAPI(String isbn, String aisle){
        //Arange
       response = given().log().all().header("Content-Type","application/json")
               .body(LibraryAPIPayload.addBook(isbn,aisle))
                .when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200).extract().response().asString();
        //Act
        jsonPath = RawToJson.rawToJson(response);
        String status = jsonPath.getString("Msg");
        //Assert
        Assert.assertEquals(status,"successfully added");
    }

   @Test(dataProvider = "BooksData",priority = 2)
   public void shouldTestDeleteAPI(String isbn, String aisle){
       //Arange
       response = given().log().all().header("Content-Type","application/json")
               .body(LibraryAPIPayload.deleteBook(isbn,aisle))
               .when().post("/Library/DeleteBook.php")
               .then().assertThat().statusCode(200).extract().response().asString();
       //Act

       //Assert
   }
}
