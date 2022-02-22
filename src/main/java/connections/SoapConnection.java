/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connections;

import automatizacionweb.ProgramConstants;
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
    
    private SoapConnection(){
    
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
                throw new RuntimeException(e);
        }
    }
    
}
