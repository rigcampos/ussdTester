/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DocumentCrud;

import JViews.ViewManager;
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
    private int temp = 1;
    
    public InExcelDocument() {
        pagesName = new ArrayList<String>();
    }
    
    public void createExcelDocument(ArrayList<String> data, int co, String excelName, int startRow){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(ProgramConstants.XLSXSHEETNAME);
        int corte = temp%2 == 0? 11:9;
        System.out.println(temp%2); System.out.println(corte);
        int rowCount = startRow;
        int cambio = 0;
        int columnCount = 0;
        
        for(String d : data){
            String dataTemp;
            
            d = d.replace("[", "");
            d = d.replace("]", "");

            String[] dataList = d.split(",");

            Row row = sheet.createRow(rowCount);
            for(int i = 0; i<dataList.length; i++){
                System.out.println("focus en el excel");
                if((i % corte) == 0){
                    System.out.println("entramos!!!!!!!!!!!!!!!!!!!!!!1");
                    row = sheet.createRow(rowCount);
                    rowCount = rowCount + 1;
                    columnCount = 0;
                }
                Cell cell = row.createCell(++columnCount);
                String  val = dataList[i];
                if(dataList[i].isBlank() || dataList[i].contains(");") || dataList[i].contains("{") 
                        || dataList[i].contains("}") || dataList[i].contains("lick") 
                        || dataList[i].contains("electe") || dataList[i].contains("econd")){
                    val = "-";
                }
                
                cell.setCellValue(val);
            }
            
            temp = temp+1;
            corte = temp%2 == 0? 11:9;
            rowCount = rowCount +3;
        }
        
         
        try (FileOutputStream outputStream = new FileOutputStream(excelName)) {
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException ex) {
            //Logger.getLogger(InWordDocument.class.getName()).log(Level.SEVERE, null, ex);
            ViewManager.getInstance().updateErrorMessage("Error en archivo Excel");
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
