package convertrawtojson;

import io.restassured.path.json.JsonPath;

public class RawToJson {
    public static JsonPath rawToJson(String response){
        return new JsonPath(response);
    }
}
