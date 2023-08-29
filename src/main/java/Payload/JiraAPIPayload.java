package Payload;

public class JiraAPIPayload {
    public  static String loginCredentials(){
        return "{ \"username\":\"bagannak\", \"password\":\"baganna$321\"}";
    }
    public static String addComment(String comment){
        return "{\n" +
                "    \"body\": \""+comment+"\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}";
    }
}
