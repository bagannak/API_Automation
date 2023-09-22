package graphQL;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class TestGraphQLAPI {
    @BeforeMethod
    public void setUp(){

        baseURI = "https://rahulshettyacademy.com";
    }
    @Test
    public void shouldTestQuery() {
        //Arrange

        //Act
        String response = given().log().all().header("Content-Type", "application/json")
                .body("{\"query\":\"query($characterId: Int!, $locationId: Int!,$episodeId:Int!){\\n  character(characterId: $characterId) {\\n    name\\n    gender\\n    status\\n  }\\n  location(locationId: $locationId) {\\n    name\\n    dimension\\n  }\\n  episode(episodeId: $episodeId) {\\n    name\\n    air_date\\n    episode\\n  }\\n  characters(filters: {name: \\\"rahul\\\"}) {\\n    info {\\n      count\\n    }\\n    result {\\n      name\\n      type\\n    }\\n  }\\n  episodes(filters: {episode: \\\"hulu\\\"}) {\\n    result {\\n      id\\n      air_date\\n      name\\n      episode\\n    }\\n  }\\n}\\n\",\"variables\":{\"characterId\":1691,\"locationId\":2134,\"episodeId\":1616}}")
                .when().post("/gq/graphql")
                .then().extract().response().asPrettyString();
        JsonPath jsonPath = new JsonPath(response);
        String characterName  =jsonPath.getString("data.character.name");
        //Assert
        Assert.assertEquals(characterName,"hipo");
    }@Test
    public void shouldTestMutations() {
        //Arrange

        //Act
        String response = given().log().all().header("Content-Type", "application/json")
                .body("{\"query\":\"mutation($characterId: Int!, $locationId: Int!,$episodeId:Int!,$locationIds:[Int!]){\\n  createLocation(location:{name:\\\"IND\\\",type:\\\"North pole\\\",dimension:\\\"34511\\\"}){\\n    id\\n  }\\n  createCharacter(character:{name:\\\"hipo\\\",gender:\\\"male\\\",image:\\\"png\\\",type:\\\"macho\\\",status:\\\"dead\\\",species:\\\"anime\\\",originId:$locationId,locationId:$locationId}){\\n    id\\n  }\\n  createEpisode(episode:{name:\\\"fighting with demon\\\",air_date:\\\"21/02/2013\\\",episode:\\\"E234\\\"}){\\n    id\\n  }\\n  deleteLocations(locationIds:$locationIds){\\n    locationsDeleted\\n  }\\n  associateEpisodeCharacter(episodeId:$episodeId,characterId:$characterId){\\n    status\\n  }\\n}\",\"variables\":{\"characterId\":1714,\"locationId\":2134,\"episodeId\":1621,\"locationIds\":[2137,2138,2136]}}")
                .when().post("/gq/graphql")
                .then().assertThat().statusCode(200).extract().response().asPrettyString();
        //Assert
        System.out.println("RESPONSE: "+response);
    }
}
