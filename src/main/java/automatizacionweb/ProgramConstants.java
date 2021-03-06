/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatizacionweb;

import com.tigosv.ussdtester.test.TestConstants;
import java.util.ArrayList;

/**
 *
 * @author rigcampos
 */
public final class ProgramConstants {
    public static final String BYID = "id";
    public static final String BYXPATH = "xpath";
    public static final String BYNAME = "name";
    public static final String BYTAGNAME = "tagName";
    public static final String BYCLASSNAME = "className";
    public static final String BYXPATHTEXT = "xpath_text";
    public static final String DOCIMAGEPATH = "resources/documentation/image/";
    public static final String DOCIMAGEEXT = ".png";
    public static final String DOCIMAGETYPE = "png";
    public static final String DOCIMAGENAME = "screenShoot";
    public static final String DOCFECHAINICO = "Fecha Inicio";
    public static final String DOCFECHAFIN = "Fecha Fin";
    public static final String ACTIONJUMP = "0";
    public static final String ACTIONCLICK = "1";
    public static final String ACTIONWRITE = "2";
    public static final String ACTIONGOTO = "3";
    public static final String ACTIONSCREENSHOT = "4";
    public static final String ACTIONMOVETO = "5";
    public static final String ACTIONSUBMIT = "6";
    public static final String ACTIONCODIGO = "7";
    public static final String ACTIONGUARDAR = "8";
    public static final String ACTIONCP = "9";
    public static final String ACTIONNEWW = "10";
    public static final String ACTIONINIWIN = "11";
    public static final String ACTIONESPERAR = "12";
    public static final String ACTIONCERRAR = "13";
    public static final String ACTIONABRIR = "14";
    public static final String ACTIONSALTO = "15";
    public static final String ACTIONLINK = "16";
    public static final String ACTIONESPERAROBJ = "17";
    public static final String ACTIONFRAME = "18";
    public static final String ACTIONREGRESAR = "19";
    public static final String ACTIONDROPDOWN = "20";
    public static final String ACTIONOPENNEW = "21";
    public static final String ACTIONRUNCLICK = "22";
    public static final String ACTIONTABLA = "23";
    public static final String ACTIONCODIGOSIM= "24";
    public static final String ACTIONEXCELELE= "25";
    public static final String ACTIONEXCELWEB= "26";
    public static final String ACTIONRUNFN= "27";
    public static final String ACTIONCLEAR= "28";
    public static final String ACTIONIE= "29";
    public static final String LABELCODIGO = "Codigo de confirmacion";
    public static final String STARTTITLE = "##";
    public static final String STARTSEPARATOR = " -- ";
    public static final String STARTFILE = "resources/documento de inicio.txt";
    public static final String XLSXNAME = "resources/Automatizacion_Web";
    public static final String XLSXEXT = ".xlsx";
    public static final String XLSXSHEETNAME = "Automatizacion_Web";
    public static final String DOCGENERALFILE = "resources/documentation/plantillas/Plantilla general automatizacion.docx";
    public static final String DOCRESULTEXT = ".docx";
    public static final String PLATAFORMAS = "documento_plataformas";
    public static final String LOGODIR = "resources/image/tigo logo.PNG";
    public static final String PLATAFORMFILE = "resources/plataformas.txt";
    public static final String BTNCONTINUAR = "Continuar";
    public static final String BTNSOLOUSSD = "USSD";
    public static final String BTNTIGOMONEY = "Tigo Money";
    public static final String BTNSOLOPLATAFORMA = "Plataformas";
    public static final String BTNSOLOTSBCBS = "TSB CBS";
    public static final String BTNPLATAFORMAUAT = "UAT TEST";
    public static final String BTNREGRESIVAS = "Regresivas";
    public static final String BTNCTUSSD = "CT_USSD";
    public static final String BTNCTACTIONUSSD = "activar_USSD";
    public static final String BTNCTACTIONPORT = "activar_porta";
    public static final String BTNCTCREDENPORT = "credenciales_porta";
    public static final String KEYPASS = "Password";
    public static final String KEYFECHA = "Fecha de expiracion";
    public static final String KEYCVV = "cvv de la tarjeta";
    public static final java.awt.Color BTNCOLORCELESTE = new java.awt.Color(0, 200, 255);//[0,55,123]
    public static final java.awt.Color BTNCOLORMENUSE = new java.awt.Color(0, 55, 123);
    public static final java.awt.Color BTNCOLORMENUDE = new java.awt.Color(0, 25, 80);
    public static final java.awt.Font BTNFONT = new java.awt.Font("SansSerif", 0, 16);
    public static final java.awt.Color BTNCOLORBLANCO = new java.awt.Color(255, 255, 255);
    public static final javax.swing.border.LineBorder BTNBORDER = new javax.swing.border.LineBorder(BTNCOLORBLANCO, 2, true);
    public static final java.awt.Font CREDENCIALFONT = new java.awt.Font("SansSerif", 0, 12);
    public static final int BTNSTARTX = 52;
    public static final int BTNSTARTY = 112;
    public static final int BTNSPACE = 10;
    public static final int BTNWIDTH = 216;
    public static final int BTNHEIGHT = 84;
    public static final int BTNPERX = 4;
    public static final int BTNPERY = 5;
    public static final int CREDENINTERSEPA = 8;
    public static final int CREDENWIDTH = 232;
    public static final int CREDENHEIGHT = 32;
    public static final int CREDENEXTERSEPA = 16;
    public static final String EXCELPATH = "USSD.XLSX";
    public static final String EXCELPATHUSSDCT = "necesarios/USSD_CT.XLSX";
    public static final String EXCELPATHPORTA = "necesarios/PORTABILIDAD.XLSX";
    public static final String EXCELSHEETNAME = "USSDGENERICO";
    public static final int EXCELSTART = 1;
    public static final int EXCELCOLUMN = 2;
    public static final String SEPARATORUSSD = ",";
    public static final String USSDIMAGEPATH = "resources/documentation/ussd image/";
    public static final String USSDNAME = "soapxml.xml";
    public static final String MENURECURSIVA = "Regresivas";
    public static final String MENUUSSD = "USSD";
    public static final String MENUFLUJOSQA = "Flujos QA";
    public static final String CODACTIVADORREMOTO = "774";
    public static final String[] NEVERSAVE = {/*"pass","contra","credi","tarjeta","cvv"*/};
    public static final String UATWEBSERV="http://192.168.119.132:8080/services/BcServices?wsdl";
    public static final String PRODWEBSERV="http://192.168.128.41:8080/services/BcServices?wsdl";
    public static final String JSON_TIGO_MONEY = "tmy_web.json";
    public static final String JSON_TIGO_PORTIN = "portabilidad_portin.json";
    public static final String FLUJO_TIGO_MONEY = "tmy_flujo";
    public static final String FLUJO_TIGO_PORTA = "porta_flujo";
    public static final String BTNPORTABILIDAD = "Portabilidad";
    public static final String MENUFLUJOSQAPORT = "PORTIN";
    public static final String MENUFLUJOSQAPORTOUT = "PORTOUT";
    public static final String JSON_TSB_CBS = "TSBCBS.json";
}
