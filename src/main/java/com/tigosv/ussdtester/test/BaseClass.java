/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tigosv.ussdtester.test;

import JViews.ViewManager;
import automatizacionweb.Manager;
import automatizacionweb.ProgramConstants;
import com.testinium.deviceinformation.DeviceInfo;
import com.testinium.deviceinformation.DeviceInfoImpl;
import com.testinium.deviceinformation.device.DeviceType;
import com.testinium.deviceinformation.exception.DeviceNotFoundException;
import com.testinium.deviceinformation.model.Device;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.touch.offset.PointOption;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

/**
 *
 * @author rigcampos
 */
public class BaseClass {
    private static BaseClass instance;
    private DeviceInfo deviceInfo;
    static AppiumDriver<MobileElement> driver;
    private String code;
    private String[] flujo;
    private Map<String, MobileElement> numbers;
    private Wait<WebDriver> driverWait;
    private ArrayList<String> imagenes;
    private String nuevo = "";
    private String numeroGenerado ="";

    private BaseClass() {
        this.deviceInfo = new DeviceInfoImpl(DeviceType.ALL);
        numbers = new HashMap<String, MobileElement>();
        imagenes = new ArrayList<String>();
    }

    public static synchronized BaseClass getInstance() {
        if(instance == null){
            instance = new BaseClass();
        }
        return instance;
    }
    
    private void fluentWaitDriver(){
        driverWait = new FluentWait<WebDriver>(driver)
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofSeconds(2))
            .ignoring(NoSuchElementException.class);
    }
    
    private MobileElement findElement(By parameter){
        if(parameter == null) return null;
        MobileElement element = driverWait.until(new Function<WebDriver, MobileElement>() {
            public MobileElement apply(WebDriver driver) {
              return driver.findElement(parameter);
            }
        });
        return element;
    }
    
    public void beforeTest(){
        
        try{
            //imagenes = new ArrayList<String>();
             DeviceInfo deviceInfo = new DeviceInfoImpl(DeviceType.ANDROID);
            if (deviceInfo.anyDeviceConnected()) {
                Device device = deviceInfo.getFirstDevice();
                
                DesiredCapabilities caps = iniCapabilities(device);
                URL url = new URL("http://127.0.0.1:4723/wd/hub");
                driver = new AndroidDriver<>(url,caps);
                
                fluentWaitDriver();
                //swipeElement();
                
            }
            
        }catch(DeviceNotFoundException | IOException e){
            System.out.println("ERROR, MENSAJE: " + e.getMessage());
            System.out.println("ERROR, CAUSA: " +e.getCause());
            System.out.println("ERROR, StackTrace:");
            e.printStackTrace();
        }
    }
    
    private DesiredCapabilities iniCapabilities(Device device){
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME,device.getDeviceProductName());
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION,device.getProductVersion());
        caps.setCapability(MobileCapabilityType.DEVICE_NAME,device.getModelNumber());//SM-J120M
        caps.setCapability(MobileCapabilityType.UDID,device.getUniqueDeviceID());
        caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,120);//seconds
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,TestConstants.UIAUTO);
        caps.setCapability(TestConstants.KEYPACKAGE,TestConstants.VALPACKAGE);
        caps.setCapability(TestConstants.KEYACTIVITY,TestConstants.VALACTIVITY);
        
        return caps;
    }
    
    private void findNumeros(){
        try {
            numbers.put("*",findElement(By.id(TestConstants.USSDSTAR)));
            numbers.put("1",findElement(By.id(TestConstants.USSDONE)));
            numbers.put("2",findElement(By.id(TestConstants.USSDTWO)));
            numbers.put("3",findElement(By.id(TestConstants.USSDTHREE)));
            numbers.put("4",findElement(By.id(TestConstants.USSDFOUR)));
            numbers.put("5",findElement(By.id(TestConstants.USSDFIVE)));
            numbers.put("6",findElement(By.id(TestConstants.USSDSIX)));
            numbers.put("7",findElement(By.id(TestConstants.USSDSEVEN)));
            numbers.put("8",findElement(By.id(TestConstants.USSDEIGHT)));
            numbers.put("9",findElement(By.id(TestConstants.USSDNINE)));
            numbers.put("0",findElement(By.id(TestConstants.USSDZERO)));
            numbers.put("#",findElement(By.id(TestConstants.USSDNUM)));
            numbers.put("call",findElement(By.id(TestConstants.USSDSEND)));
        } catch (Exception e) {
            ViewManager.getInstance().infoBox("Error en detectar el teclado del movil");
        }
    }
    
    public void seguirFlujo(String call, String[] way) throws InterruptedException{
        imagenes = new ArrayList<String>();
        Thread.sleep(4000L);
        int fin  = way.length;
        for(int i=0; i<way.length; i++){
            try {
                findElement(By.id(TestConstants.TEST_IFIELD)).sendKeys(way[i]);
                Thread.sleep(1000L);
                tomarScreenShot(i+ProgramConstants.DOCIMAGENAME);
                findElement(By.id(TestConstants.TEST_BTNOK)).click();
                Thread.sleep(2000L);
            } catch (Exception e) {
                ViewManager.getInstance().infoBox("Error en el menu, Excel");
            }  
        }
        
        Thread.sleep(12000L);
        Thread.sleep(2000L);
        tomarScreenShot(fin+ProgramConstants.DOCIMAGENAME);
        if(nuevo.equals("774")){
            guardarNumero();
        }
        try {
            findElement(By.id(TestConstants.TEST_BTNOK)).click();
        } catch (Exception e) {
        }
        
    }
    
    public void swipeElement(int num){
        
        for(int i =0; i<num; i++){
            try {
                PointOption inicial = new PointOption();
                inicial.withCoordinates(400,440);

                PointOption fin = new PointOption();
                fin.withCoordinates(27,440);

                TouchAction ta = new TouchAction(driver); 
                ta.press(inicial).moveTo(fin).release().perform();
            } catch (Exception e) {
                
            }
            
        }
        aditionalControl();
    }
    
    public void aceptAllPermision(){
        try {
            MobileElement el1 = (MobileElement) findElement(By.id("v.d.d.answercall:id/btn_done"));
            el1.click();
            MobileElement el2 = (MobileElement) findElement(By.id("com.android.permissioncontroller:id/permission_allow_button"));
            el2.click();
            el2.click();
            el2.click();
            el2.click();
            el2.click();
            el2.click();
            MobileElement el3 = (MobileElement) findElement(By.id("android:id/button2"));
            el3.click();
            MobileElement el4 = (MobileElement) findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.view.ViewGroup/android.widget.RelativeLayout/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.TextView[1]"));
            el4.click();
            MobileElement el5 = (MobileElement) findElement(By.id("v.d.d.answercall:id/btn_phone_number"));
            el5.click(); 
            Manager.getInstance().setTn(true);
        } catch (Exception e) {
        
        }
            
        //android:id/button2
    }
    
    public void aditionalControl(){
        MobileElement el1 = (MobileElement) findElement(By.id("v.d.d.answercall:id/btn_done"));
        el1.click();
        MobileElement el2 = (MobileElement) findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.view.View/android.widget.RelativeLayout/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.TextView[1]"));
        el2.click();
        MobileElement el3 = (MobileElement) findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.view.View/android.widget.RelativeLayout/androidx.viewpager.widget.ViewPager/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.ListView/android.view.View[3]/android.widget.RelativeLayout/android.widget.LinearLayout[1]"));
        el3.click();
    }
    
    public void tomarScreenShot(String name){
        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        //String fileName = UUID.randomUUID().toString();
        String namePath = ProgramConstants.USSDIMAGEPATH + Manager.getInstance().getNumeroFlujo()
                +name + ProgramConstants.DOCIMAGEEXT;
        File targetFile = new File(namePath);
        imagenes.add(namePath);
        try {
            FileUtils.copyFile(srcFile, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void marcarCodigo(String call) throws InterruptedException{
        findNumeros();
        String[] st = call.split("");
        nuevo = "";
        for(int i = st.length-1; i>=st.length-3; i--){
            nuevo = st[i] + nuevo;
        }
        try {
            numbers.get("*").click();
            for(int i = 0; i<nuevo.split("").length; i++){
                try {
                    numbers.get(nuevo.split("")[i]).click();
                } catch (Exception e) {

                }

            }
            numbers.get("#").click();
            Thread.sleep(1000L);
            numbers.get("call").click();
        } catch (Exception e) {
        }
    }
    
    public void guardarNumero(){
        String txt = findElement(By.id("android:id/message")).getText();
        numeroGenerado = txt.split("Telefono:")[1].split(",")[0].trim().replace("-", "");
        
        Manager.getInstance().getUserVals().put("Numero generado"
                            ,numeroGenerado);
    }
    
    private void allData(String code, String[] flujo){
        this.code = code;
        this.flujo = flujo;
    }
    
    public ArrayList<String> getImagenes(){
        return this.imagenes;
    }
    
    public String getNumeroGenerado(){
        return this.numeroGenerado;
    }
    
}
