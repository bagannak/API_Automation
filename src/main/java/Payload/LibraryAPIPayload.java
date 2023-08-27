package Payload;

public class LibraryAPIPayload {
    public static String addBook(String isbn, String aisle){
        return "{\n" +
                "\n" +
                "\"name\":\"Learn Python Basics \",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"Baganna k\"\n" +
                "}";
    }

    public static String deleteBook(String isbn, String aisle){
        return "{\n" +
                "\"ID\":\""+isbn+aisle+"\"\n" +
                "}";
    }
}
