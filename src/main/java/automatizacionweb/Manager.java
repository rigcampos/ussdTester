/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatizacionweb;

import DocumentCrud.InExcelDocument;
import DocumentCrud.InWordDocument;
import DocumentCrud.StartDocument;
import JViews.ViewManager;
import com.tigosv.ussdtester.test.BaseClass;
import com.tigosv.ussdtester.test.TestConstants;
import connections.SoapConnection;
import flujoWeb.RegresivaWeb;
import java.io.File;
import java.io.FileWriter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import modelData.CP;
import modelData.ModelCredencial;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


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
    private ArrayList<CP> tigoMList;
    private ArrayList<CP> TSBCBSList;
    private ArrayList<CP> portabilidadList;
    private Map<String,ModelCredencial> credencialesPojo;
    private ArrayList<String> tableData;
    public static String instanceCamino = "";
    private String actualDoc = ProgramConstants.DOCGENERALFILE;
    private int numeroFlujo = 0;
    private Map credencialesGuardadas;
    private Map<String,String> guardarSim;
    private Map<String,ArrayList<String>> datosExcel;
    private String call="";
    private String ussdCT;
    private int[] flujoActive = new int[]{0,0,0}; 
    private int inicio = 0;
    private int terminar = 3;
    private String linkSoap = ProgramConstants.PRODWEBSERV;
    private String xmlDate1 ="";
    private String xmlDate2 ="";
    private boolean tn = false;
    private String portFl;

    private Manager() {
        st= new StartDocument();
        rw = new RegresivaWeb();
        userVals = new HashMap<String, String>();
        saveVals = new HashMap<String, String>();
        startDate = getNowDate();
        tableData = new ArrayList<String>();
        credencialesGuardadas = new HashMap<String, String>();
        guardarSim = new HashMap<String,String>();
    }

    public static synchronized Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }

        return instance;
    }
    
    public static synchronized Manager getInstance(String camino) {
        
        instanceCamino = camino;
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }
    
    public void readDocument(){
        st.readStartDocument(); // Lee un txt de flujo recursivo
        masterMap = st.getFlujos();
        st.readCredencialDoc();// Lee las credenciales de las plataformas, flujo recursivo
        credencialesMap = st.getCredenciales();
        readFlujoUssd();
    }
    
    public void readFlujoUssd(){
        st.readFlujoExcel();// Lee el excel del flujo USSD.
        st.readJson(TestConstants.JSON_NAME_USSD);
        st.readJson(TestConstants.JSON_NAME_CREDENCIALES);
        st.readJson(TestConstants.JSON_GUARDADO);
        st.readJson(ProgramConstants.JSON_TIGO_MONEY);
        st.readJson(ProgramConstants.JSON_TSB_CBS);
        cpList = st.getCpList();
        tigoMList = st.getTigoMList();
        TSBCBSList = st.getTSBCBSList();
        credencialesPojo = st.getCredencialesPojo();
    }
    
    public void readPortProcess(){
        st.readJson(ProgramConstants.JSON_TIGO_PORTIN);
        portabilidadList = st.getPortabilidaList();
    }
    
    public void readFlujoUssdCT(){
        st.readFlujoUSSDCT(ussdCT);// Lee el excel del flujo USSD.
        //st.readJson(TestConstants.JSON_NAME_USSD);
        //st.readJson(TestConstants.JSON_NAME_CREDENCIALES);
        //st.readJson(TestConstants.JSON_GUARDADO);
        //cpList = st.getCpList();
        credencialesPojo = st.getCredencialesPojo();
    }
    
    public void readExcelDocument(){
        st.readFlujoExcel();
    }

    @Override
    public void run() {
        switch(instanceCamino){
            case ProgramConstants.MENURECURSIVA ->{
                startFlujo();
            }
            case ProgramConstants.BTNSOLOUSSD ->{
                processUSSD();
            }case ProgramConstants.BTNSOLOPLATAFORMA ->{
                validacionesExternas();
            }case ProgramConstants.MENUFLUJOSQA ->{
                runFlujoTMYWeb();
            }case ProgramConstants.MENUFLUJOSQAPORT ->{
                runFlujoPortaWeb();
            }case ProgramConstants.MENUFLUJOSQAPORTOUT ->{
                runFlujoPortOUT();
            }case ProgramConstants.BTNSOLOTSBCBS ->{
                soloTsbCbs();
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
        String cero = "0";
        if(now.getMonthValue()>=10){cero = "";}
        xmlDate1=""+now.getYear()+cero+now.getMonthValue()+""+now.getDayOfMonth()+"000000";
        xmlDate2=""+now.getYear()+cero+now.getMonthValue()+""+now.getDayOfMonth()+"235959";
        System.out.println(xmlDate1);
        System.out.println(xmlDate2);
        return now.getDayOfMonth() +"/"+now.getMonthValue()+"/"+now.getYear();
    }
    
    public void processUSSD(){
        st.flujoUssd.forEach((k,v)->{
            //return;
            if(k.equals("") || k.length()<2){
                return;
            }
            numeroFlujo = numeroFlujo + 1;
            BaseClass.getInstance().beforeTest();
            call = k;
            String[] way = v.split(ProgramConstants.SEPARATORUSSD);
            
            try {
                BaseClass.getInstance().swipeElement(3);
                //BaseClass.getInstance().aceptAllPermision();
                //if(!tn){BaseClass.getInstance().swipeElement(3);}
                BaseClass.getInstance().marcarCodigo(call);
                BaseClass.getInstance().seguirFlujo(call,way);
                crearArchivoWord(BaseClass.getInstance().getImagenes(), call + ProgramConstants.EXCELSHEETNAME,
                        152,228, actualDoc);
                //actualDoc = ProgramConstants.EXCELSHEETNAME + ProgramConstants.DOCRESULTEXT;
                
            } catch (InterruptedException ex) {
                ViewManager.getInstance().infoBox("Error al abrir la Aplicacion en el movil");
            }finally{
                
            }
        });
    }
    
    public void validacionesExternas(){
        int count = 0;
        for(Map.Entry<String,ArrayList<String>> entry: datosExcel.entrySet()){
            String k = entry.getKey();
            ArrayList<String> v = entry.getValue();
            call = k;
            InExcelDocument ie = new InExcelDocument();
            SoapConnection.getInstance().xmlResponse(linkSoap, 
                    v.get(4));
            if(inicio == 3){
                SoapConnection.getInstance().xmlQueryCDR(linkSoap, 
                    v.get(4));
            }
            runFlujoWebUssd(v);
            String temp = count == 0 ? ProgramConstants.DOCGENERALFILE: ProgramConstants.PLATAFORMAS
                    + ProgramConstants.DOCRESULTEXT;
            crearArchivoWord(getImagenes(), call + ProgramConstants.PLATAFORMAS,
                    456,228, ProgramConstants.DOCGENERALFILE);
            count = 1;
            ie.createExcelDocument(tableData, 9, "documentacion_tablas.xlsx",1);
            finalizandoProceso();
        };
        
    }
    
    private void runFlujoWebUssd(ArrayList<String> st){
        rw.driverStart("Chrome", flujoActual);
        
        for(int i=inicio; i<terminar; i++){
            CP cpTemp = cpList.get(i);   
            
            if((cpTemp.getLink().contains("epos.sv.tigo.com") || cpTemp.getLink().contains("tigo-pos-web")) && st.get(2).trim().toLowerCase().equals("directa")){
                
                i = i+1;
                cpTemp = cpList.get(i);
            }
            rw.waitAction(ProgramConstants.ACTIONGOTO, "", "", cpTemp.getLink());
            cpTemp.getPasos().forEach((pasosTemp)->{
                if(rw.isDetenerFlujo()){
                    return;
                }
                if(userVals.containsKey(pasosTemp.getInput())){
                    rw.waitAction(pasosTemp.getAccion(), pasosTemp.getLlave(), pasosTemp.getValor(), userVals.get(pasosTemp.getInput()));
                }else{
                    rw.waitAction(pasosTemp.getAccion(), pasosTemp.getLlave(), pasosTemp.getValor(), pasosTemp.getInput());
                }
                
            });
            if((cpTemp.getLink().contains("epos.sv.tigo.com") || cpTemp.getLink().contains("tigo-pos-web")) && st.get(2).trim().toLowerCase().equals("indirecta")){
                
                i = 500;
                //cpTemp = cpList.get(i);
            }
            
            if(rw.isDetenerFlujo()){
                    return;
            }
        }
    }
    
    private void runFlujoTMYWeb(){
        rw.driverStart("Chrome", flujoActual);
        
        for(int i=0; i<tigoMList.size(); i++){
            CP cpTemp = tigoMList.get(i);   
            
            
            rw.waitAction(ProgramConstants.ACTIONGOTO, "", "", cpTemp.getLink());
            cpTemp.getPasos().forEach((pasosTemp)->{
                if(rw.isDetenerFlujo()){
                    return;
                }
                if(userVals.containsKey(pasosTemp.getInput())){
                    rw.waitAction(pasosTemp.getAccion(), pasosTemp.getLlave(), pasosTemp.getValor(), userVals.get(pasosTemp.getInput()));
                }else{
                    rw.waitAction(pasosTemp.getAccion(), pasosTemp.getLlave(), pasosTemp.getValor(), pasosTemp.getInput());
                }
                
            });
            
            if(rw.isDetenerFlujo()){
                    return;
            }
        }
        crearArchivoWord(getImagenes(), call + ProgramConstants.PLATAFORMAS,
                    456,228, ProgramConstants.DOCGENERALFILE);
        finalizandoProceso();
    }
    
    public void soloTsbCbs(){
        int count = 0;
        for(Map.Entry<String,ArrayList<String>> entry: datosExcel.entrySet()){
            String k = entry.getKey();
            ArrayList<String> v = entry.getValue();
            call = k;
            
            //runFlujoWebUssd(v);
            runFlujoTSBCBSWeb();
            String temp = count == 0 ? ProgramConstants.DOCGENERALFILE: ProgramConstants.PLATAFORMAS
                    + ProgramConstants.DOCRESULTEXT;
            crearArchivoWord(getImagenes(), call + ProgramConstants.PLATAFORMAS,
                    456,228, ProgramConstants.DOCGENERALFILE);
            count = 1;
            //ie.createExcelDocument(tableData, 9, "documentacion_tablas.xlsx",1);
            //finalizandoProceso();
        };
        
    }
    
    private void runFlujoTSBCBSWeb(){
        rw.driverStart("Chrome", flujoActual);
        
        for(int i=0; i<1; i++){
            CP cpTemp = TSBCBSList.get(i);   
            
            
            rw.waitAction(ProgramConstants.ACTIONGOTO, "", "", cpTemp.getLink());
            cpTemp.getPasos().forEach((pasosTemp)->{
                if(rw.isDetenerFlujo()){
                    return;
                }
                if(userVals.containsKey(pasosTemp.getInput())){
                    rw.waitAction(pasosTemp.getAccion(), pasosTemp.getLlave(), pasosTemp.getValor(), userVals.get(pasosTemp.getInput()));
                }else{
                    rw.waitAction(pasosTemp.getAccion(), pasosTemp.getLlave(), pasosTemp.getValor(), pasosTemp.getInput());
                }
                
            });
            
            if(rw.isDetenerFlujo()){
                    return;
            }
        }
        
        finalizandoProceso();
    }
    
    private void runFlujoPortaWeb(){
        readPortProcess();
        SoapConnection.getInstance().handleRequest("PORT IN");
        rw.driverStart("Chrome", flujoActual);
        cicloPorta();
        finalizandoProceso();
        SoapConnection.getInstance().handleRequest("REVERSA PORT IN");
        rw.driverStart("Chrome", flujoActual);
        cicloPorta();
        finalizandoProceso();
        crearArchivoWord(getImagenes(), call + ProgramConstants.PLATAFORMAS,
                    456,228, ProgramConstants.DOCGENERALFILE);
        
    }
    
    private void runFlujoPortOUT(){
        readPortProcess();
        SoapConnection.getInstance().handleRequest("PORT OUT");
        rw.driverStart("Chrome", flujoActual);
        cicloPorta();
        finalizandoProceso();
        crearArchivoWord(getImagenes(), call + ProgramConstants.PLATAFORMAS,
                    456,228, ProgramConstants.DOCGENERALFILE);
        
    }
    
    private void cicloPorta(){
        for(int i=0; i<portabilidadList.size(); i++){
            CP cpTemp = portabilidadList.get(i);   
            
            
            rw.waitAction(ProgramConstants.ACTIONGOTO, "", "", cpTemp.getLink());
            cpTemp.getPasos().forEach((pasosTemp)->{
                if(rw.isDetenerFlujo()){
                    return;
                }
                if(userVals.containsKey(pasosTemp.getInput())){
                    rw.waitAction(pasosTemp.getAccion(), pasosTemp.getLlave(), pasosTemp.getValor(), userVals.get(pasosTemp.getInput()));
                }else{
                    rw.waitAction(pasosTemp.getAccion(), pasosTemp.getLlave(), pasosTemp.getValor(), pasosTemp.getInput());
                }
                
            });
            
            if(rw.isDetenerFlujo()){
                    return;
            }
        }
    }
    
    private void finalizandoProceso(){
        if(ViewManager.getInstance().getErrorMessage().equals("")){
            ViewManager.getInstance().infoBox("Proceso finalizado");
        }else{
            ViewManager.getInstance().errorBox();
        }
        rw.endProcess();
    }
    
    public void saveDataCredencial(){
        
        JSONObject details = new JSONObject();
        userVals.forEach((k,v)->{
            boolean b = true;
            for(int i = 0; i<ProgramConstants.NEVERSAVE.length; i++){
                if(k.toLowerCase().contains(ProgramConstants.NEVERSAVE[i])){
                    b = false;
                }
            }
            if(b){
                JSONObject employeeDetails = new JSONObject();
                details.put(k, v);
            }
        });
        
        JSONObject saveObject = new JSONObject(); 
        saveObject.put("Credenciales", details);
        
        JSONArray listadatos = new JSONArray();
        listadatos.add(saveObject);
        
        try (FileWriter file = new FileWriter("datos_guardados.json")){
                        
            file.write(listadatos.toString());
            file.flush();
            
        } catch (Exception e) {
        }
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

    public Map<String,ModelCredencial> getCredencialesPojo() {
        return credencialesPojo;
    }
    
    public void updateTableData(String td){
        this.tableData.add(td);
    }

    public Map<String, String> getGuardarSim() {
        return guardarSim;
    }

    public void setGuardarSim(Map<String, String> guardarSim) {
        this.guardarSim = guardarSim;
    }

    public String getCall() {
        return call;
    }
    
    public int getNumeroFlujo(){
        return this.numeroFlujo;
    }

    public Map getCredencialesGuardadas() {
        return credencialesGuardadas;
    }

    public void setCredencialesGuardadas(Map credencialesGuardadas) {
        this.credencialesGuardadas = credencialesGuardadas;
    }

    public Map<String, ArrayList<String>> getDatosExcel() {
        return datosExcel;
    }

    public void setDatosExcel(Map<String, ArrayList<String>> datosExcel) {
        this.datosExcel = datosExcel;
    }
    
    public void setInicio(int inicio){
        this.inicio = inicio;
    }
    
    public int getInicio(){
        return this.inicio;
    }
    
    public void setTerminar(int terminar){
        this.terminar = terminar;
    }
    
    public int getTerminar(){
        return this.terminar;
    }
    
    public void setLinkSoap(String linkSoap){
        this.linkSoap = linkSoap;
    }
    
    public String getLinkSoap(){
        return this.linkSoap;
    }
    
    public void setUssdCT(String ussdCT){
        this.ussdCT = ussdCT;
    }

    public boolean isTn() {
        return tn;
    }

    public void setTn(boolean tn) {
        this.tn = tn;
    }
    
    public void setPortFl(String portFl){
        this.portFl = portFl;
    }
    
    public String getPortFl(){
        return portFl;
    }

    public String getXmlDate1() {
        return xmlDate1;
    }

    public void setXmlDate1(String xmlDate1) {
        this.xmlDate1 = xmlDate1;
    }

    public String getXmlDate2() {
        return xmlDate2;
    }

    public void setXmlDate2(String xmlDate2) {
        this.xmlDate2 = xmlDate2;
    }
}
