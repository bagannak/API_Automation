package ExcelDataDriven;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class DataDriven {
    public static void main(String[] args) throws IOException {
        FileInputStream file = new FileInputStream("src/main/resources/TestData.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
    }
}
