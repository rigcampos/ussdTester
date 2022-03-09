/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DocumentCrud;

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
    private Map<String,ModelCredencial> credencialesPojo;
    
    public StartDocument() {
        flujos = new LinkedHashMap<String, TreeMap>();
        credenciales = new LinkedHashMap<String, ArrayList<String>>();
        flujoUssd = new LinkedHashMap<String, String>();
        credencialesPojo = new HashMap<String,ModelCredencial>();
        jsonParser = new JSONParser();
        cpList = new ArrayList<CP>(); 
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
            System.out.println(flujos.toString());    
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
                
                flujoUssd.put(i+sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN - 1).getRawValue(), 
                        sheet.getRow(i).getCell(ProgramConstants.EXCELCOLUMN).getRawValue());
            }
            
            file.close();
            workbook.close();
             
        } catch (IOException | EncryptedDocumentException
                 ex) {
            System.out.println("MENSAJE: " + ex.getMessage());
            System.out.println("CAUSA: " + ex.getCause());
            ex.printStackTrace();
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
            }case TestConstants.JSON_NAME_CREDENCIALES ->{
                System.out.println(jo);
                JSONObject credenciales = (JSONObject) jo.get(TestConstants.FLUJO_NAME);
                credencialesPojo.put((String)credenciales.get("titulo"),new ModelCredencial(credenciales));
            }case TestConstants.JSON_GUARDADO ->{
                
                Manager.getInstance().setCredencialesGuardadas((JSONObject) jo.get("Credenciales"));
        //JSONArray tempList = (JSONArray) c.get("credenciales");
        //this.credenciales = ((JSONObject)tempList.get(0));
                
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

    public Map<String,ModelCredencial> getCredencialesPojo() {
        return credencialesPojo;
    }
    
}
