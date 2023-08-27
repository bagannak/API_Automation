import Payload.MapAPIPayload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ComplexJsonTest {
    @Test
    public void shouldTestSomething(){
        //Arange
        JsonPath js = new JsonPath(MapAPIPayload.exampleJSON());
        int size=js.get("courses.size()");
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        int count = 0;
        //Act
        System.out.println(size+","+purchaseAmount);

        for(int i=0; i<size;i++){
            System.out.println("CoursesName : "+js.get("courses["+i+"].title")+"---->"+" Price : "+js.get("courses["+i+"].price"));
            count+=js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies");
        }
        //Assert
        Assert.assertEquals(count,purchaseAmount);
    }
}
