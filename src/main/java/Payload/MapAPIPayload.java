package Payload;

public class MapAPIPayload {
//    public static String addPlace() {
//        return """
//                {
//                "location": {
//                "lat": -38.383494,
//                "lng": 33.427362
//                    },
//                "accuracy": 50,
//
//                "name":"Frontline house",
//                "phone_number": "(+91) 983 893 3937",
//                "address": "29, side layout, cohen 09",
//                "types": [
//                "shoe park",
//                "shop"
//                    ],
//                "website": "http: //google.com",
//                "language": "French-IN"
//                }""";
//    }

    public static String updatePlace(String placeId){
        return "{\n" +
                "\"place_id\":\""+placeId+"\",\n" +
                "\"address\":\"0 winter walk, USA\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}";
    }

        public static String exampleJSON(){
        return """
                {

                "dashboard": {

                "purchaseAmount": 910,

                "website": "rahulshettyacademy.com"

                },

                "courses": [

                {

                "title": "Selenium Python",

                "price": 50,

                "copies": 6

                },

                {

                "title": "Cypress",

                "price": 40,

                "copies": 4

                },

                {

                "title": "RPA",

                "price": 45,

                "copies": 10

                }

                ]

                }

                """;
        }

}
