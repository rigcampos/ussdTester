/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DocumentCrud;

import automatizacionweb.ProgramConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author rigcampos
 */
public class InExcelDocument {

    private ArrayList<String> pagesName;
    private TreeMap<String,ArrayList<String>> internalData;
    private String filePathUpdate;
    
    public InExcelDocument() {
        pagesName = new ArrayList<String>();
    }
    
    public void createExcelDocument(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(ProgramConstants.XLSXSHEETNAME);
        
        int rowCount = 0;
        
        for (Map.Entry<String,ArrayList<String>> entry : internalData.entrySet()) {
            Row row = sheet.createRow(++rowCount);
            int columnCount = 0;
            Cell cell = row.createCell(++columnCount);
            //System.out.println("Key : " + entry.getKey() + ", Value : " + entry.getValue());
            for(String val : entry.getValue()){
                Cell cell2 = row.createCell(++columnCount);
                cell2.setCellValue(val);
            }
        }
         
        try (FileOutputStream outputStream = new FileOutputStream(ProgramConstants.XLSXNAME + 
                ProgramConstants.XLSXEXT)) {
            workbook.write(outputStream);
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(InWordDocument.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(InWordDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateExcelDocument(){
        String excelFilePath = filePathUpdate;
        
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream); 
 
            Sheet sheet = workbook.getSheetAt(0);
            
            inputStream.close();
 
            FileOutputStream outputStream = new FileOutputStream(filePathUpdate);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
             
        } catch (IOException | EncryptedDocumentException
                 ex) {
            ex.printStackTrace();
        }
    }
}
