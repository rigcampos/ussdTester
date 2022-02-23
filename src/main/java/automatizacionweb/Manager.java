/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatizacionweb;

import DocumentCrud.InWordDocument;
import DocumentCrud.StartDocument;
import com.tigosv.ussdtester.test.BaseClass;
import com.tigosv.ussdtester.test.TestConstants;
import connections.SoapConnection;
import flujoWeb.RegresivaWeb;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelData.CP;
import modelData.ModelCredencial;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author rigcampos
 */
public class Manager extends Thread{
    private static Manager instance = null;
    private Map<String, TreeMap> masterMap;
    private Map<String, ArrayList<String>> credencialesMap;
    private Map<String, String> userVals;
    private Map<String, String> saveVals;
    private StartDocument st;
    private String flujoActual;
    private RegresivaWeb rw;
    private ArrayList<String> imagenes;
    private String startDate;
    private ArrayList<CP> cpList;
    private ArrayList<ModelCredencial> credencialesPojo;
    private static int instanceCamino = 0;

    private Manager() {
        st= new StartDocument();
        rw = new RegresivaWeb();
        userVals = new HashMap<String, String>();
        saveVals = new HashMap<String, String>();
        startDate = getNowDate();
    }

    public static synchronized Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }

        return instance;
    }
    
    public static synchronized Manager getInstance(int camino) {
        instanceCamino = camino;
        if (instance == null) {
            instance = new Manager();
        }

        return instance;
    }
    
    public void readDocument(){
        st.readStartDocument();
        masterMap = st.getFlujos();
        st.readCredencialDoc();
        credencialesMap = st.getCredenciales();
        st.readFlujoExcel();
        st.readJson(TestConstants.JSON_NAME_USSD);
        st.readJson(TestConstants.JSON_NAME_CREDENCIALES);
        cpList = st.getCpList();
        credencialesPojo = st.getCredencialesPojo();
    }
    
    public void readExcelDocument(){
        st.readFlujoExcel();
    }

    @Override
    public void run() {
        switch(instanceCamino){
            case 1 ->{
                startFlujo();
            }
            case 2 ->{
                processUSSD();
            }
        }
    }
    
    public void startFlujo(){
        rw.driverStart("Chrome", flujoActual);
        masterMap.get(flujoActual).forEach((k,v)->{
            ArrayList<String> temp = (ArrayList<String>)v;
            if(temp.size()>4){
                
                String val = temp.get(4);
                String atr = temp.get(3);
                if(userVals.containsKey(temp.get(4))){
                    val = userVals.get(temp.get(4));
                    if(temp.get(3).indexOf(temp.get(4)) >= 0){
                        atr = temp.get(3).replace(temp.get(4), val);
                    }
                }
                rw.waitAction(temp.get(1), temp.get(2),atr, val);
            }else{
                rw.waitAction(temp.get(1), temp.get(2),temp.get(3), "0");
            }    
        });
        crearArchivoWord(getImagenes(), ProgramConstants.XLSXSHEETNAME,456,228, ProgramConstants.DOCGENERALFILE);
    }
    
    public void crearArchivoWord(ArrayList<String> imagenes, String name, int w, int h, String path){
        InWordDocument iw = new InWordDocument(name);
        iw.updateWordDoc(imagenes,w,h,path);
    }
    
    public void crearArchivoWord(String st, String name, String path){
        InWordDocument iw = new InWordDocument(name);
        iw.updateWordDocText(st, path);
    }
    
    public String getNowDate(){
        LocalDateTime now = LocalDateTime.now();
        return now.getDayOfMonth() +"/"+now.getMonthValue()+"/"+now.getYear();
    }
    
    public void processUSSD(){   
        st.flujoUssd.forEach((k,v)->{
//            BaseClass.getInstance().beforeTest();
//            String call = k;
//            String[] way = v.split(ProgramConstants.SEPARATORUSSD);
//            
//            try {
//                BaseClass.getInstance().marcarCodigo(call);
//                BaseClass.getInstance().seguirFlujo(call,way);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
//            }finally{
//                
//            }
        });
        crearArchivoWord(BaseClass.getInstance().getImagenes(), ProgramConstants.EXCELSHEETNAME,152,228, ProgramConstants.DOCGENERALFILE);
        validacionesExternas();
    }
    
    public void validacionesExternas(){
        
        runFlujoWebUssd();
        crearArchivoWord(getImagenes(), ProgramConstants.EXCELSHEETNAME,456,228, ProgramConstants.EXCELSHEETNAME + ProgramConstants.DOCRESULTEXT);
        SoapConnection.getInstance().xmlResponse("http://192.168.128.41:8080/services/BcServices?wsdl", "79174491");
    }
    
    private void runFlujoWebUssd(){
        rw.driverStart("Chrome", flujoActual);
        cpList.forEach((cpTemp)->{
            rw.waitAction(ProgramConstants.ACTIONGOTO, "", "", cpTemp.getLink());
            cpTemp.getPasos().forEach((pasosTemp)->{
                rw.waitAction(pasosTemp.getAccion(), pasosTemp.getLlave(), pasosTemp.getValor(), pasosTemp.getInput());
            });
        });
    }
    
    public String getStartDate(){
        return this.startDate;
    }
    
    public ArrayList<String> getImagenes(){
        this.imagenes = rw.getImagenes();
        return this.imagenes;
    }
    
    public void setFlujoActual(String flujoActual){
        this.flujoActual = flujoActual;
    }
    
    public Map<String, TreeMap> getMasterMap(){
        return this.masterMap;
    }
    
    public Map<String, ArrayList<String>> getCredencialesMap(){
        return this.credencialesMap;
    }
    
    public Map<String, String> getUserVals(){
        return this.userVals;
    }
    
    public Map<String, String> getSaveVals(){
        return this.saveVals;
    }

    public ArrayList<ModelCredencial> getCredencialesPojo() {
        return credencialesPojo;
    }
    
}
