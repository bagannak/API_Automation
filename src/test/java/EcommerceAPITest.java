import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.Ecom.*;

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
    List<String> orderId;
    @BeforeClass
    public void getLogInResponse() {
        //req and res spec build
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .setRelaxedHTTPSValidation()
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
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
                .then().statusCode(200)
                .extract().response().as(LogInResponse.class);
        //getting response
        token = logInResponse.getToken();
        userId = logInResponse.getUserId();

    }

    @Test
    public void shouldTestCreateProductAPI(){
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
        RequestSpecification addProductReq = given().spec(requestSpecification)
                .header("Authorization",token).params(paramsList)
                .multiPart("productImage",
                        new File("/Users/testvagrant/Desktop/Screenshot 2023-09-01 at 2.40.49 PM.png"));
        AddProductResponse addProductResponse = addProductReq.when().post("/api/ecom/product/add-product")
                .then().spec(responseSpecification)
                .extract().response().as(AddProductResponse.class);
        productId = addProductResponse.getProductId();
        String message = addProductResponse.getMessage();
        //Assert
        Assert.assertEquals(message, "Product Added Successfully");
    }
    @Test(dependsOnMethods = "shouldTestCreateProductAPI")
    public void shouldTestCreateOrderAPI(){
        //Arrange
        OrderDetails orderDetails = new OrderDetails();
        Order order;
        order = new Order();
        order.setCountry("India");
        order.setProductOrderedId(productId);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orderDetails.setOrders(orders);
        //Act
        RequestSpecification createOrderRequest = given().spec(requestSpecification)
                .header("Authorization",token)
                .header("Content-Type","application/json")
                .body(orderDetails);
        OrderResponse orderResponse = createOrderRequest.when().post("/api/ecom/order/create-order")
                .then().spec(responseSpecification).log().all().extract().response().as(OrderResponse.class);
         orderId = orderResponse.getOrders();
        //Assert
        Assert.assertEquals(orderResponse.getMessage(),"Order Placed Successfully");
    }
    @Test(dependsOnMethods = "shouldTestCreateOrderAPI")
    public void shouldTestGetOrderDetailAPI(){
        //Arrange
        
        //Act
        System.out.println(orderId.get(0));
        RequestSpecification getOrderDetailsRequest = given().spec(requestSpecification)
                .queryParam("id", orderId.get(0)).header("Authorization", token);
        GetOrderDetailsResponse getOrderDetailsResponse;
        getOrderDetailsResponse = getOrderDetailsRequest.when()
                .get("/api/ecom/order/get-orders-details")
                .then().log().all().statusCode(200)
                .extract().response().as(GetOrderDetailsResponse.class);
        //Assert
        Assert.assertEquals(getOrderDetailsResponse.getMessage(),"Orders fetched for customer Successfully");
    }
    
    @Test(dependsOnMethods = "shouldTestCreateProductAPI")
    public void shouldTestDeleteProductAPI(){
        //Arrange
        
        //Act
        RequestSpecification deleteProductRequest = given().spec(requestSpecification)
                .pathParam("productId", productId)
                .header("Authorization", token);
        DeleteProduct deleteProduct = deleteProductRequest.when()
                .delete("/api/ecom/product/delete-product/{productId}")
                .then().log().all()
                .extract().response()
                .as(DeleteProduct.class);
        String message = deleteProduct.getMessage();

        //Assert
        Assert.assertEquals(message , "Product Deleted Successfully");
    }



}
