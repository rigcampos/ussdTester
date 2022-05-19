/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connections;

import automatizacionweb.Manager;
import automatizacionweb.ProgramConstants;
import flujoWeb.RegresivaWeb;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author rigcampos
 */
public class SoapConnection{
    
    private static SoapConnection sc;
    private HashMap<String,ArrayList<String>> mapaNumeros;
    
    private SoapConnection(){
        mapaNumeros = new HashMap<String,ArrayList<String>>();
    }
    
    public synchronized static SoapConnection getInstance(){
        if(sc == null){
            sc = new SoapConnection();
        }
        return sc;
    }
    
    public String xmlResponse(String serviceUrl,String numero){
        
        try{
            String responseString = "";
            String outputString = "";
            String wsEndPoint = serviceUrl;//"http://192.168.128.41:8080/services/BcServices?wsdl";
            URL url = new URL(wsEndPoint);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:bcs=\"http://www.huawei.com/bme/cbsinterface/bcservices\" xmlns:cbs=\"http://www.huawei.com/bme/cbsinterface/cbscommon\" xmlns:bcc=\"http://www.huawei.com/bme/cbsinterface/bccommon\"> <soapenv:Header/> <soapenv:Body> <bcs:QueryCustomerInfoRequestMsg> <RequestHeader> <cbs:Version>1</cbs:Version> <cbs:BusinessCode>1</cbs:BusinessCode> <cbs:MessageSeq>2022-02-02</cbs:MessageSeq> <cbs:OwnershipInfo> <cbs:BEID>101</cbs:BEID> <cbs:BRID>101</cbs:BRID> </cbs:OwnershipInfo> <cbs:AccessSecurity> <cbs:LoginSystemCode>102</cbs:LoginSystemCode> <cbs:Password>xyYSFeOUi5DagegPuCQmUQ==</cbs:Password> </cbs:AccessSecurity> <cbs:OperatorInfo> <cbs:OperatorID>101</cbs:OperatorID> <cbs:ChannelID>1</cbs:ChannelID> </cbs:OperatorInfo> <cbs:TimeFormat> <cbs:TimeType>1</cbs:TimeType> <cbs:TimeZoneID>1</cbs:TimeZoneID> </cbs:TimeFormat> </RequestHeader> <QueryCustomerInfoRequest> <bcs:QueryObj> <bcs:SubAccessCode> <bcc:PrimaryIdentity>"
                +numero
                +"</bcc:PrimaryIdentity> </bcs:SubAccessCode> </bcs:QueryObj> </QueryCustomerInfoRequest> </bcs:QueryCustomerInfoRequestMsg> </soapenv:Body> </soapenv:Envelope>";
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
            bout.write(buffer);
            System.out.println(xmlInput);
            System.out.println(numero);
            byte[] b = bout.toByteArray();
            String SOAPAction = "getUserDetails";
            httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            OutputStream out = httpConn.getOutputStream();
            // Write the content of the request to the outputstream of the HTTP
            // Connection.
            out.write(b);
            out.close();
            // Ready with sending the request.
            // Read the response.
            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), Charset.forName("UTF-8"));
            BufferedReader in = new BufferedReader(isr);
            // Write the SOAP message response to a String.
            while ((responseString = in.readLine()) != null) {
                                            outputString = outputString + responseString;
            }
            // Write the SOAP message formatted to the console.
            FileOutputStream output2 = new FileOutputStream(ProgramConstants.USSDNAME);
            formatXML(outputString,output2);
        }catch(IOException e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
            System.out.println("ERROR ENE LA CREACION DEL ARCHIVO SOAP");
        }
        return "";
    }
    
    private String xmlResponse(String request, String serviceUrl, String action, String name){
        
        try{
            String responseString = "";
            String outputString = "";
            String wsEndPoint = serviceUrl;//"http://192.168.128.41:8080/services/BcServices?wsdl";
            URL url = new URL(wsEndPoint);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            String xmlInput = request; 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
            bout.write(buffer);
            byte[] b = bout.toByteArray();
            String SOAPAction = action;
            httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            OutputStream out = httpConn.getOutputStream();
            // Write the content of the request to the outputstream of the HTTP
            // Connection.
            out.write(b);
            out.close();
            // Ready with sending the request.
            // Read the response.
            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), Charset.forName("UTF-8"));
            BufferedReader in = new BufferedReader(isr);
            // Write the SOAP message response to a String.
            while ((responseString = in.readLine()) != null) {
                                            outputString = outputString + responseString;
            }
            // Write the SOAP message formatted to the console.
            FileOutputStream output2 = new FileOutputStream(name);
            formatXML(outputString,output2);
        }catch(IOException e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
            System.out.println("ERROR ENE LA CREACION DEL ARCHIVO SOAP");
        }
        return "";
    }
    
    private String xmlInputPortIn(String numPort, String numTemp){
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.messagin.portability.ea.tigo.com/\">\n" +
                "<soapenv:Header/>\n" +
                "<soapenv:Body>\n" +
                "<ws:executeBlackOutPortability>\n" +
                "<!-- Numero de la competencia -->\n" +
                "<numeroPortar>"+numPort+"</numeroPortar>\n" +
                "<!-- Numero Tigo -->\n" +
                "<numeroTemporal>"+numTemp+"</numeroTemporal>\n" +
                "<!--Optional:-->\n" +
                "<idReceptor>100</idReceptor>\n" +
                "<!--Optional:-->\n" +
                "<idDonante>010</idDonante>\n" +
                "<!--Optional:-->\n" +
                "<tipoPortabilidad>0</tipoPortabilidad>\n" +
                "<!--Optional:-->\n" +
                "<tipoActividad>I</tipoActividad>\n" +
                "<!--Optional:-->\n" +
                "<tipoUsuario>0</tipoUsuario>\n" +
                "</ws:executeBlackOutPortability>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }
    
    private String xmlInputPortOut(String numPort, String numTemp){
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.messagin.portability.ea.tigo.com/\">\n" +
                "<soapenv:Header/>\n" +
                "<soapenv:Body>\n" +
                "<ws:executeBlackOutPortability>\n" +
                "<!-- Haber hecho primero PortIN y reversa\n" +
                "Numero TIGO -->\n" +
                "<numeroPortar>"+numPort+"</numeroPortar>\n" +
                "<!--Numero TIGO -->\n" +
                "<numeroTemporal>"+numTemp+"</numeroTemporal>\n" +
                "<!--Optional:-->\n" +
                "<idReceptor>010</idReceptor>\n" +
                "<!--Optional:-->\n" +
                "<idDonante>100</idDonante>\n" +
                "<!--Optional:-->\n" +
                "<tipoPortabilidad>1</tipoPortabilidad>\n" +
                "<!--Optional:-->\n" +
                "<tipoActividad>I</tipoActividad>\n" +
                "<!--Optional:-->\n" +
                "<tipoUsuario>0</tipoUsuario>\n" +
                "</ws:executeBlackOutPortability>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }
    
    private String xmlInputReversa(String numPort, String numTemp){
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.messagin.portability.ea.tigo.com/\">\n" +
                "<soapenv:Header/>\n" +
                "<soapenv:Body>\n" +
                "<ws:executeBlackOutPortability>\n" +
                "<!--Ingresar numero portout a revertir:-->\n" +
                "<numeroPortar>"+numPort+"</numeroPortar>\n" +
                "<!--Ingresar numero portout a revertir:-->\n" +
                "<numeroTemporal>"+numTemp+"</numeroTemporal>\n" +
                "<!--Optional:-->\n" +
                "<idReceptor>010</idReceptor>\n" +
                "<!--Optional:-->\n" +
                "<idDonante>100</idDonante>\n" +
                "<!--Optional:-->\n" +
                "<tipoPortabilidad>4</tipoPortabilidad>\n" +
                "<!--Optional:-->\n" +
                "<tipoActividad>E</tipoActividad>\n" +
                "<tipoUsuario>1</tipoUsuario>\n" +
                "</ws:executeBlackOutPortability>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>\n";
    }
    
    private String causalRechazo(String numPort){
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.messagin.portability.ea.tigo.com/\">\n" +
                "<soapenv:Header/>\n" +
                "<soapenv:Body>\n" +
                "<ws:validatePortabilityRejection>\n" +
                "<!--Optional:-->\n" +
                "<direccion></direccion>\n" +
                "<!--Optional:-->\n" +
                "<idTransaccion>001</idTransaccion>\n" +
                "<!--Optional:-->\n" +
                "<numeroIdentificacion>22022033</numeroIdentificacion>\n" +
                "<!--Optional:-->\n" +
                "<tipoIdentificacion>0</tipoIdentificacion>\n" +
                "<!--Zero or more repetitions:-->\n" +
                "<numerosAbonados>\n" +
                "<!--Optional:-->\n" +
                "<imei/>\n" +
                "<!--Optional:-->\n" +
                "<numero>"+numPort+"</numero>\n" +
                "</numerosAbonados>\n" +
                "<!--Optional:-->\n" +
                "<NIT></NIT>\n" +
                "<!--Optional:-->\n" +
                "<representanteLegal></representanteLegal>\n" +
                "<!--Optional:-->\n" +
                "<tipoOperacion>1</tipoOperacion>\n" +
                "</ws:validatePortabilityRejection>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }
    
    private String numberInventory(String num){
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://consumer.esb.ea.tigo.com\">\n" +
                "<soapenv:Header/>\n" +
                "<soapenv:Body>\n" +
                "<con:ServiceRequest>\n" +
                "<con:serviceId>getNumberInventoryInformationService</con:serviceId>\n" +
                "<con:accessToken>1234</con:accessToken>\n" +
                "<con:country>SV</con:country>\n" +
                "<con:applicationId>daperez</con:applicationId>\n" +
                "<con:requestDate>${=new java.text.SimpleDateFormat(\"yyyy-MM-dd'T'HH:mm:ss.SSS\").format(new Date())}</con:requestDate>\n" +
                "<con:dataType>json-pretty</con:dataType>\n" +
                "<con:data>\n" +
                "{\n" +
                "\"msisdn\": \""+num+"\"\n" +
                "}</con:data>\n" +
                "<con:environmentId>production</con:environmentId>\n" +
                "</con:ServiceRequest>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }
    
    public String xmlQueryCDR(String serviceUrl,String numero){
        
        try{
            String responseString = "";
            String outputString = "";
            String wsEndPoint = "http://192.168.128.41:8080/services/BbServices?wsdl";//"http://192.168.119.132:8080/services/BbServices?wsdl";//"http://192.168.128.41:8080/services/BcServices?wsdl";
            URL url = new URL(wsEndPoint);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:bbs=\"http://www.huawei.com/bme/cbsinterface/bbservices\" xmlns:cbs=\"http://www.huawei.com/bme/cbsinterface/cbscommon\" xmlns:bbc=\"http://www.huawei.com/bme/cbsinterface/bbcommon\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <bbs:QueryCDRRequestMsg>\n" +
                    "           <RequestHeader>                                                                                                                                                                                                                                           \n" +
                    "            <cbs:Version>1</cbs:Version>                                                                                                                                                                                                                           \n" +
                    "            <cbs:BusinessCode>1</cbs:BusinessCode>                                                                                                                                                                                                                 \n" +
                    "            <cbs:MessageSeq>${=new java.text.SimpleDateFormat(\"yyyy-MM-dd'T'HH:mm:ss.SSS\").format(new Date())}</cbs:MessageSeq>                                                                                                                                                                                                    \n" +
                    "            <cbs:OwnershipInfo>                                                                                                                                                                                                                                    \n" +
                    "               <cbs:BEID>101</cbs:BEID>                                                                                                                                                                                                                            \n" +
                    "               <cbs:BRID>101</cbs:BRID>                                                                                                                                                                                                                            \n" +
                    "            </cbs:OwnershipInfo>                                                                                                                                                                                                                                   \n" +
                    "            <cbs:AccessSecurity>                                                                                                                                                                                                                                   \n" +
                    "               <cbs:LoginSystemCode>102</cbs:LoginSystemCode>                                                                                                                                                                                                    \n" +
                    "               <cbs:Password>xyYSFeOUi5DagegPuCQmUQ==</cbs:Password>                                                                                                                                                                                                                 \n" +
                    "               <cbs:RemoteIP>192.168.128.42</cbs:RemoteIP>                                                                                                                                                                                                            \n" +
                    "            </cbs:AccessSecurity>                                                                                                                                                                                                                                  \n" +
                    "            <cbs:OperatorInfo>                                                                                                                                                                                                                                     \n" +
                    "               <cbs:OperatorID>101</cbs:OperatorID>                                                                                                                                                                                                                \n" +
                    "               <cbs:ChannelID>1</cbs:ChannelID>                                                                                                                                                                                                                  \n" +
                    "            </cbs:OperatorInfo>                                                                                                                                                                                                                                    \n" +
                    "            <cbs:TimeFormat>                                                                                                                                                                                                                                       \n" +
                    "               <cbs:TimeType>1</cbs:TimeType>                                                                                                                                                                                                                      \n" +
                    "               <cbs:TimeZoneID>101</cbs:TimeZoneID>                                                                                                                                                                                                                  \n" +
                    "            </cbs:TimeFormat>                                                                                                                                                                                                                                      \n" +
                    "         </RequestHeader>      \n" +
                    "         <QueryCDRRequest>\n" +
                    "            <bbs:SubAccessCode>\n" +
                    "               <bbc:PrimaryIdentity>"+numero+"</bbc:PrimaryIdentity>\n" +
                    "            </bbs:SubAccessCode>\n" +
                    "            <bbs:TimePeriod>\n" +
                    "               <bbs:StartTime>"+Manager.getInstance().getXmlDate1()+"</bbs:StartTime>\n" +
                    "               <bbs:EndTime>"+Manager.getInstance().getXmlDate2()+"</bbs:EndTime>\n" +
                    "            </bbs:TimePeriod>\n" +
                    "            <bbs:TotalCDRNum>40</bbs:TotalCDRNum>\n" +
                    "            <bbs:BeginRowNum>0</bbs:BeginRowNum>\n" +
                    "            <bbs:FetchRowNum>40</bbs:FetchRowNum>\n" +
                    "         </QueryCDRRequest>\n" +
                    "      </bbs:QueryCDRRequestMsg>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
            bout.write(buffer);
            System.out.println(xmlInput);
            System.out.println(numero);
            byte[] b = bout.toByteArray();
            String SOAPAction = "getUserDetails";
            httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            OutputStream out = httpConn.getOutputStream();
            // Write the content of the request to the outputstream of the HTTP
            // Connection.
            out.write(b);
            out.close();
            // Ready with sending the request.
            // Read the response.
            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), Charset.forName("UTF-8"));
            BufferedReader in = new BufferedReader(isr);
            // Write the SOAP message response to a String.
            while ((responseString = in.readLine()) != null) {
                                            outputString = outputString + responseString;
            }
            // Write the SOAP message formatted to the console.
            FileOutputStream output2 = new FileOutputStream("QueryCDR.xml");
            formatXML(outputString,output2);
        }catch(IOException e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
            System.out.println("ERROR ENE LA CREACION DEL ARCHIVO SOAP");
        }
        return "";
    }
    
    private static void formatXML(String unformattedXml, FileOutputStream fs) {
        try {
            Document document = parseXmlFile(unformattedXml);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", 3);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult xmlOutput = new StreamResult(fs);
            transformer.transform(source, xmlOutput);
                //return xmlOutput.getWriter().toString();
        } catch (TransformerException e) {
             System.out.println("ERROR EN EL FORMATO DE SOAP");    
            throw new RuntimeException(e);
        }
    }
	// parse XML
    private static Document parseXmlFile(String in) {
        try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource(new StringReader(in));
                return db.parse(is);
        } catch (IOException | ParserConfigurationException | SAXException e) {
             System.out.println("ERROR EN EL FORMATO DE SOAP");    
            throw new RuntimeException(e);
        }
    }
    
    private void esperar(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegresivaWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleRequest(String title){
        switch(title){
            case "PORT IN" -> {
                
                String num1 = "61393797";//mapaNumeros.get("PORT IN").get(0);
                String num2 = "74830089";//mapaNumeros.get("PORT IN").get(1);
                
                xmlResponse(xmlInputPortIn(num1,num2), 
                        "http://portabilityprod.sv.tigo.com:80/PortabilityMessagingService/PortabilityMessagingBeanService", 
                        "getUserDetails","PORT_IN_go.xml");
                esperar();
                xmlResponse(numberInventory(num1), 
                        "http://tsbprod.sv.tigo.com:80/soa-infra/services/tsb/esb-core/ServiceConsumer?WSDL", 
                        "getUserDetails", num1+"go_NumberInventory.xml");
                esperar();
                xmlResponse(numberInventory(num2), 
                        "http://tsbprod.sv.tigo.com:80/soa-infra/services/tsb/esb-core/ServiceConsumer?WSDL", 
                        "getUserDetails", num2+"go_NumberInventory.xml");
                esperar();
                
                return;
            }case "REVERSA PORT IN" -> {
                String num1 = "61393797";//mapaNumeros.get("PORT IN").get(0);
                String num2 = "74830089";//mapaNumeros.get("PORT IN").get(1);
                xmlResponse(xmlInputPortIn(num2,num1), 
                        "http://portabilityprod.sv.tigo.com:80/PortabilityMessagingService/PortabilityMessagingBeanService", 
                        "getUserDetails","PORT_IN_reversa.xml");
                esperar();
                xmlResponse(numberInventory(num1), 
                        "http://tsbprod.sv.tigo.com:80/soa-infra/services/tsb/esb-core/ServiceConsumer?WSDL", 
                        "getUserDetails", num1+"reverse_NumberInventory.xml");
                esperar();
                xmlResponse(numberInventory(num2), 
                        "http://tsbprod.sv.tigo.com:80/soa-infra/services/tsb/esb-core/ServiceConsumer?WSDL", 
                        "getUserDetails", num2+"reverse_NumberInventory.xml");
                esperar();
                return;
            }case "PORT OUT" -> {
                String num1 = "61393797";//mapaNumeros.get("PORT IN").get(0);
                String num2 = "74830089";//mapaNumeros.get("PORT IN").get(1);
                xmlResponse(xmlInputPortOut(num1,num1), 
                        "http://portabilityprod.sv.tigo.com:80/PortabilityMessagingService/PortabilityMessagingBeanService", 
                        "getUserDetails","PORT_OUT_go.xml");
                esperar();
                xmlResponse(numberInventory(num1), 
                        "http://tsbprod.sv.tigo.com:80/soa-infra/services/tsb/esb-core/ServiceConsumer?WSDL", 
                        "getUserDetails", num1+"_PORTOUT_NumberInventory.xml");
                return;
            }case "REVERSA PORT OUT" -> {
                return;
            }case "RECHAZO" -> {
                return;
            }
            default -> {
                return;
            }
        }
    }
    
}
