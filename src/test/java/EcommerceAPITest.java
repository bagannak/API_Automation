import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.Ecom.AddProductResponse;
import pojo.Ecom.CreateOrderDetails;
import pojo.Ecom.LogInDetails;
import pojo.Ecom.LogInResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import static io.restassured.RestAssured.given;

public class EcommerceAPITest {
    String token;
    String userId;
    String productId;
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    @BeforeClass
    public void getLogInResponse() {
        //req and res spec build
        requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").build();
        responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();
        //creating pojo class obj
        LogInDetails logInDetails = new LogInDetails();
        logInDetails.setUserEmail("bk123@gmail.com");
        logInDetails.setUserPassword("Baganna1");
        //login automation
        RequestSpecification requestLogin = given().spec(requestSpecification)
                .header("Content-Type","application/json")
                .body(logInDetails);
        LogInResponse logInResponse = requestLogin.when()
                .post("/api/ecom/auth/login")
                .then().spec(responseSpecification)
                .extract().response().as(LogInResponse.class);
        //getting response
        token = logInResponse.getToken();
        userId = logInResponse.getUserId();

    }

    @Test
    public void shouldTestCreateProduct(){
        //Arrange
        Map<String,String> paramsList = new HashMap<>();
        paramsList.put("productName","image");
        paramsList.put("productAddedBy",userId);
        paramsList.put("productCategory","fashion");
        paramsList.put("productSubCategory","shirts");
        paramsList.put("productPrice","11500");
        paramsList.put("productDescription","Color Image");
        paramsList.put("productFor","women");
        //Act
        RequestSpecification addProductReq = given().spec(requestSpecification).params(paramsList)
                .multiPart("productImage", new File(""));
        AddProductResponse addProductResponse = addProductReq.when().post("/api/ecom/product/add-product")
                .then().spec(responseSpecification).extract().response().as(AddProductResponse.class);
        productId = addProductResponse.getProductId();
        String message = addProductResponse.getMessage();
        //Assert
        Assert.assertEquals(message, "Product Added Successfully");
    }
    @Test
    public void shouldTestCreateOrder(){
        //Arrange
        CreateOrderDetails createOrderDetails;
        createOrderDetails = new CreateOrderDetails();
        List<String> productIdList = new ArrayList<>();
        productIdList.add(productId);
        createOrderDetails.setProductOrderId(productIdList);

        //Act

        //Assert
    }
}
