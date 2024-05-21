package Utils;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static Page_Objects.ProcurementGov_Data.*;


public class CsvListener{

    private Workbook workbook;
    private Sheet sheet;
    private int rowNum;

    private final String[] headerTitles = {"შესყიდვის ტიპი", "განცხადების ნომერი", "შემსყიდველი", "შესყიდვის სავარაუდო ღირებულება", "შესყიდვის კატეგორია", "დამატებითი ინფორმაცია "  , "test"};


    public void onStart() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Tenders");
        Row headerRow = sheet.createRow(0);

        sheet.setColumnWidth(0, 60 * 256);
        sheet.setColumnWidth(1, 25 * 256);
        sheet.setColumnWidth(2, 45 * 256);
        sheet.setColumnWidth(3, 30 * 256);
        sheet.setColumnWidth(4, 35 * 256);
        sheet.setColumnWidth(5, 65 * 256);
        sheet.setColumnWidth(6, 65 * 256);


        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.index);
        headerCellStyle.setFont(font);

        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);

        for (int i = 0; i < headerTitles.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headerTitles[i]);
            cell.setCellStyle(headerCellStyle);
        }

        rowNum = 1;
    }

    public void onFinish() {
        setAllBorders();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());

            String excelFilePath = "Excel_Files";
            String fileName = excelFilePath + "/procurement_data" + timestamp + ".xlsx";
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeResultToExcel(String purchaseType, String applicationId, String buyer, String purchaseCost, String classifierCodes, String additionalInfo , String Test) {
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(purchaseType);
        row.createCell(1).setCellValue(applicationId);
        row.createCell(2).setCellValue(buyer);
        row.createCell(3).setCellValue(purchaseCost);
        row.createCell(4).setCellValue(classifierCodes);
        row.createCell(5).setCellValue(additionalInfo);
        row.createCell(6).setCellValue(Test);

    }

    private void setAllBorders() {
        for (int i = 1; i <= rowNum; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                for (int j = 0; j <= 6; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        CellStyle style = workbook.createCellStyle();
                        style.setBorderTop(BorderStyle.THIN);
                        style.setBorderBottom(BorderStyle.THIN);
                        style.setBorderLeft(BorderStyle.THIN);
                        style.setBorderRight(BorderStyle.THIN);
                        cell.setCellStyle(style);
                    }
                }
            }
        }
    }
}