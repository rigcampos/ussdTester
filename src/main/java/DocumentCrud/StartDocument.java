/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DocumentCrud;

import JViews.ViewManager;
import automatizacionweb.Manager;
import automatizacionweb.ProgramConstants;
import com.tigosv.ussdtester.test.TestConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import modelData.CP;
import modelData.ModelCredencial;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author rigcampos
 */
public class StartDocument {
    
    public Map<String, TreeMap> flujos;
    public Map<String, ArrayList<String>> credenciales;
    public Map<String, String> flujoUssd;
    private JSONParser jsonParser;
    private ArrayList<CP> cpList;
    private ArrayList<CP> tigoMList;
    private ArrayList<CP> portabilidadList;
    private ArrayList<CP> tsbCbsList;
    private Map<String,ModelCredencial> credencialesPojo;
    private Map<String,String> guardarSim;
    private Map<String,ArrayList<String>> datosExcel;
    
    public StartDocument() {
        flujos = new LinkedHashMap<String, TreeMap>();
        credenciales = new LinkedHashMap<String, ArrayList<String>>();
        flujoUssd = new LinkedHashMap<String, String>();
        credencialesPojo = new HashMap<String,ModelCredencial>();
        guardarSim = new HashMap<String,String>();
        jsonParser = new JSONParser();
        cpList = new ArrayList<CP>();
        tigoMList = new ArrayList<CP>();
        portabilidadList = new ArrayList<CP>();
        tsbCbsList = new ArrayList<CP>();
        datosExcel = new HashMap<String,ArrayList<String>>();
    }
    
    public void readStartDocument(){
        
        try{
            File file = new File(ProgramConstants.STARTFILE);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st , temp = "";
            int num = 0;
            
            while ((st = br.readLine()) != null){
                if(st.startsWith(ProgramConstants.STARTTITLE)){
                    temp = st;
                    num = 0;
                    flujos.put(st,new TreeMap<Integer, ArrayList<String>>());
                }else{
                    ArrayList<String> arrTemp = new  ArrayList<String>();
                    arrTemp.addAll(Arrays.asList(st.split(ProgramConstants.STARTSEPARATOR)));
                    flujos.get(temp).put(num, arrTemp);
                    num++;
                }
            }
            
        }catch(IOException e){
            
        }
    }
    
    public void readCredencialDoc(){
        try{
            File file = new File(ProgramConstants.PLATAFORMFILE);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st , temp = "";
            int num = 0;
            
            while ((st = br.readLine()) != null){
                if(st.startsWith(ProgramConstants.STARTTITLE)){
                    temp = st;
                    num = 0;
                    credenciales.put(st,new ArrayList<String>());
                }else{
                    credenciales.get(temp).add(st);
                }
            }
                
        }catch(IOException e){
            
        }
    }
    
    public void readFlujoExcel(){
        
        try {
            
            FileInputStream file = new FileInputStream(new File(ProgramConstants.EXCELPATH));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet(ProgramConstants.EXCELSHEETNAME);
            for(int i = ProgramConstants.EXCELSTART; i<=sheet.getLastRowNum(); i++){
                try {
                    if(sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN - 1)==null){return;}
                    String v0 = i+sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN - 1).getStringCellValue();
                    flujoUssd.put(v0, 
                            sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN).getStringCellValue());
                    //if(v0.contains(ProgramConstants.CODACTIVADORREMOTO)){
                            guardarSim.put(v0,
                                sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN + 6).getStringCellValue());
                            ArrayList<String> temporal = new ArrayList<String>();
                            String v1 = sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN+1).getStringCellValue();
                            String v2 = sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN+2).getStringCellValue();
                            String v3 = sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN+3).getStringCellValue();
                            String v4 = sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN+4).getStringCellValue();
                            String v5 = sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN+5).getStringCellValue();
                            String v6 = sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN + 7).getStringCellValue();
                            temporal.add(v1);temporal.add(v2);temporal.add(v3);temporal.add(v4);temporal.add(v5);temporal.add(v6);
                            datosExcel.put(v0, temporal);
                    //}
                } catch (Exception e) {
                    System.out.println("CAUSA ---> "+e.getCause());
                    System.out.println("MENSAJE ----> " +e.getMessage());
                    ViewManager.getInstance().infoBox("Error excel USSD. Todas las columnas deben ser tipo Texto");
                }
                    
            }
            
            Manager.getInstance().setGuardarSim(guardarSim);
            Manager.getInstance().setDatosExcel(datosExcel);
            file.close();
            workbook.close();
             
        } catch (IOException | EncryptedDocumentException
                 ex) {
            ViewManager.getInstance().infoBox("No se encontro el archivo USSD o la hoja USSDGENERICO no existe");
        }
    }
    
    public void readFlujoUSSDCT(String ussdCT){
        flujoUssd.clear();
        try {
            
            FileInputStream file = new FileInputStream(new File(ProgramConstants.EXCELPATHUSSDCT));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet(ussdCT);
            for(int i = ProgramConstants.EXCELSTART; i<=sheet.getLastRowNum(); i++){
                try {
                    String v0 = i+sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN - 1).getStringCellValue();
                    flujoUssd.put(v0, 
                            sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN).getStringCellValue());
                    
                } catch (Exception e) {
                    ViewManager.getInstance().infoBox("Error excel USSD. Todas las columnas deben ser tipo Texto");
                }
                    
            }
            
            file.close();
            workbook.close();
             
        } catch (IOException | EncryptedDocumentException
                 ex) {
            ViewManager.getInstance().infoBox("No se encontro el archivo de Corte o la hoja no existe");
        }
    }
    
    public void readFlujoPortaCT(String ussdCT){
        flujoUssd.clear();
        try {
            
            FileInputStream file = new FileInputStream(new File(ProgramConstants.EXCELPATHPORTA));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet(ussdCT);
            for(int i = ProgramConstants.EXCELSTART; i<=sheet.getLastRowNum(); i++){
                try {
                    String v0 = i+sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN - 1).getStringCellValue();
                    flujoUssd.put(v0, 
                            sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN).getStringCellValue());
                    
                } catch (Exception e) {
                    ViewManager.getInstance().infoBox("Error excel USSD. Todas las columnas deben ser tipo Texto");
                }
                    
            }
            
            file.close();
            workbook.close();
             
        } catch (IOException | EncryptedDocumentException
                 ex) {
            ViewManager.getInstance().infoBox("No se encontro el archivo de Corte o la hoja no existe");
        }
    }
    
    public void readJson(String nombreArchivo){
         
        try (FileReader reader = new FileReader(nombreArchivo)){//TestConstants.JSON_NAME_USSD
            
            Object obj = jsonParser.parse(reader);
            JSONArray tempList = (JSONArray) obj;
            for(int i = 0; i< tempList.size(); i++){
                parseObject( (JSONObject) tempList.get(i), nombreArchivo);
            }
            //tempList.forEach( test -> parseObject( (JSONObject) test, nombreArchivo ) );
 
        }catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
    
    private void parseObject(JSONObject jo, String name){
        switch(name){
            case TestConstants.JSON_NAME_USSD ->{
                JSONObject cp = (JSONObject) jo.get(TestConstants.CP_NAME);
                cpList.add(new CP(cp));
                
                JSONObject cp2 = (JSONObject) jo.get("CP2");
                cpList.add(new CP(cp2));
                JSONObject cp3 = (JSONObject) jo.get("CP3");
                cpList.add(new CP(cp3));
            }case TestConstants.JSON_NAME_CREDENCIALES ->{
                
                JSONObject credenciales = (JSONObject) jo.get(TestConstants.FLUJO_NAME);
                credencialesPojo.put((String)credenciales.get("titulo"),new ModelCredencial(credenciales));
                
                JSONObject credenciales2 = (JSONObject) jo.get(ProgramConstants.FLUJO_TIGO_MONEY);
                credencialesPojo.put((String)credenciales2.get("titulo"),new ModelCredencial(credenciales2));
                
                JSONObject credenciales3 = (JSONObject) jo.get(ProgramConstants.FLUJO_TIGO_PORTA);
                credencialesPojo.put((String)credenciales3.get("titulo"),new ModelCredencial(credenciales3));
            }case TestConstants.JSON_GUARDADO ->{
                
                Manager.getInstance().setCredencialesGuardadas((JSONObject) jo.get("Credenciales"));
        //JSONArray tempList = (JSONArray) c.get("credenciales");
        //this.credenciales = ((JSONObject)tempList.get(0));
                
            }case ProgramConstants.JSON_TIGO_MONEY ->{
                JSONObject cp = (JSONObject) jo.get(TestConstants.CP_NAME);
                tigoMList.add(new CP(cp));
                System.out.println(tigoMList);
            }case ProgramConstants.JSON_TIGO_PORTIN ->{
                JSONObject cp = (JSONObject) jo.get(TestConstants.CP_NAME);
                portabilidadList.add(new CP(cp));
            }case ProgramConstants.JSON_TSB_CBS ->{
                JSONObject cp = (JSONObject) jo.get(TestConstants.CP_NAME);
                tsbCbsList.add(new CP(cp));
            }
        }
        
    }
    
    public Map<String, TreeMap> getFlujos(){
        return this.flujos;
    }
    
    public Map<String, ArrayList<String>> getCredenciales(){
        return this.credenciales;
    }
    
    public ArrayList<CP> getCpList(){
        return cpList;
    }
    
    public ArrayList<CP> getTigoMList(){
        return tigoMList;
    }
    
    public ArrayList<CP> getTSBCBSList(){
        return tsbCbsList;
    }
    
    public ArrayList<CP> getPortabilidaList(){
        return portabilidadList;
    }

    public Map<String,ModelCredencial> getCredencialesPojo() {
        return credencialesPojo;
    }
    
}
