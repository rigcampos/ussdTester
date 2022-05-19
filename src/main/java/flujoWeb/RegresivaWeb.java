/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flujoWeb;

import JViews.ViewManager;
import automatizacionweb.Manager;
import automatizacionweb.ProgramConstants;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

/**
 *
 * @author rigcampos
 */
public class RegresivaWeb {
    
    private WebDriver driver;
    private InDriverFactory dfactory;
    private InWebDriver iwd;
    private WebDriver auxDriver;
    private Wait<WebDriver> driverWait;
    private Wait<WebElement> elementWait;
    private WebElement actual;
    private Actions action;
    private Robot r;
    private int num = 0;
    private ArrayList<String> imagenes;
    private String currentAtr;
    private boolean detenerFlujo = false;
    private String masterCod;

    public RegresivaWeb() {
        dfactory = new InDriverFactory();     
    }
    
    public void driverStart(String type, String url){
        iwd = dfactory.getDriver(type);
        driver = iwd.driverStart();
        driver.manage().window().maximize();
        action = new Actions(driver);
        fluentWaitDriver();
        iniRobot();
        imagenes = new ArrayList<String>();
    }
    
    public void iniRobot(){
        try {
            r = new Robot();
        } catch (AWTException ex) {
            //Logger.getLogger(RegresivaWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void endProcess(){
        iwd.driverQuit();
    }
    
    private void fluentWaitDriver(){
        driverWait = new FluentWait<WebDriver>(driver)
            .withTimeout(Duration.ofSeconds(12))
            .pollingEvery(Duration.ofSeconds(2))
            .ignoring(NoSuchElementException.class);
    }
    
    private WebElement findElement(By parameter){
        if(parameter == null) return null;
        WebElement element = driverWait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
              return driver.findElement(parameter);
            }
        });
        return element;
    }
    
    private boolean takeScreenShoot(){
        num = num + 1;
        try{
            BufferedImage image = r.createScreenCapture(new Rectangle(Toolkit
                    .getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, ProgramConstants.DOCIMAGETYPE, new File(ProgramConstants.DOCIMAGEPATH 
                    + ProgramConstants.DOCIMAGENAME + num + ProgramConstants.DOCIMAGEEXT));
            imagenes.add(ProgramConstants.DOCIMAGEPATH + ProgramConstants.DOCIMAGENAME + num + 
                    ProgramConstants.DOCIMAGEEXT);
            return true;
        }catch(IOException e){
            System.out.println("No se pudo realizar la accion uno");
            System.out.println("Mensaje: " + e.getMessage());
            System.out.println("Causa: " + e.getCause());
            e.printStackTrace();
        }
        return false;
    }
    
    private void moveToElement(WebElement element){
        JavascriptExecutor j = (JavascriptExecutor)driver;
        j.executeScript ("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", element);
    }
    
    private String tableScript(String id){
        String readTd = "var oTable = document.getElementById('"+id+"'); var rowLength = oTable.rows.length; var arreglo = []; for (i = 0; i < rowLength; i++){ var oCells = oTable.rows.item(i).cells; var cellLength = oCells.length; for(var j = 0; j < cellLength; j++){ var cellVal = oCells.item(j).textContent; arreglo.push(cellVal); } } return arreglo;";
        return readTd;
    }
    
    private void quitarBorde(WebElement element){
        JavascriptExecutor j = (JavascriptExecutor)driver;
        j.executeScript ("arguments[0].style.border='0px", element);
    }
    
    private void esperar(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegresivaWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private By byParameter(String key,String atr){
        
        switch(key){
            case ProgramConstants.BYID -> {
                return By.id(atr);
            }case ProgramConstants.BYTAGNAME -> {
                return By.tagName(atr);
            }case ProgramConstants.BYNAME -> {
                return By.name(atr);
            }case ProgramConstants.BYCLASSNAME -> {
                return By.className(atr);
            }case ProgramConstants.BYXPATH -> {
                return By.xpath(atr);
            }case "xpaths" -> {
                return By.xpath(atr);
            }
            default -> {return null;}
        } 
    }
    
    public Boolean waitAction(String act,String key, String atr , String val){
        currentAtr = atr;
        try {
            WebElement element;
            if(key.equals("xpaths")){
                element = getLastElementeList(byParameter(key, atr));
            }else{
                element = findElement(byParameter(key, atr));
            }
            //WebElement element = findElement(byParameter(key, atr));
            return driverWait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return doAction( act, element, val);
                }
            });
        } catch (Exception e) {
            
            if(e.getLocalizedMessage().contains("chrome not reachable") || 
                    e.toString().contains("org.openqa.selenium.WebDriverException: chrome not reachable")){
                    
                    detenerFlujo = true;
                    ViewManager.getInstance().infoBox("No se encontro el navegador.");
                    
            }
            
            if(act.equals("45") && atr.equals("form1:tablaSeriales:11:nSeriales")){
                doAction( "45", null, val);
            }
            return false;
            
        }finally{
        }
    }
    
    private WebElement getLastElementeList(By parameter){
        if(parameter == null) return null;
        WebElement element;
        //List<WebElement> t  = driver.findElements(parameter);
        
        List<WebElement> elements = driverWait.until(new Function<WebDriver, List<WebElement>>() {
            public List<WebElement> apply(WebDriver driver) {
              return driver.findElements(parameter);
            }
        });
        System.out.println(elements);
        element = elements.get(elements.size()-1);
        return element;
    }
    
    private Boolean doAction(String act, WebElement element, String val){
        try {
            switch(act){
                case ProgramConstants.ACTIONCLICK -> {
                    action.click(element).perform();
                }
                case ProgramConstants.ACTIONWRITE ->{
                    element.sendKeys(val);
                    //driverWait.until(ExpectedConditions.textToBePresentInElementValue(element, val));
                }
                case ProgramConstants.ACTIONGOTO ->{
                    if(val.equals("user.dir")){
                        val = System.getProperty("user.dir") + "/" + ProgramConstants.USSDNAME;
                    }if(val.equals("user.diruat")){
                        val = System.getProperty("user.dir") + "/" + "QueryCDR.xml";
                    }
                    driver.navigate().to(val);
                }
                case ProgramConstants.ACTIONSCREENSHOT->{
                    takeScreenShoot();
                }case ProgramConstants.ACTIONMOVETO->{
                    moveToElement(element);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid blue'", element);
                }case ProgramConstants.ACTIONSUBMIT->{
                    element.submit();
                }case ProgramConstants.ACTIONCODIGO->{
                    String valor = ViewManager.getInstance().showInputBox(ProgramConstants.LABELCODIGO);
                    element.sendKeys(valor);
                    //driverWait.until(ExpectedConditions.textToBePresentInElementValue(element, valor));
                }case ProgramConstants.ACTIONGUARDAR->{
                    Manager.getInstance().getSaveVals().put(val, element.getText());
                }case ProgramConstants.ACTIONCP ->{
                    imagenes.add(val);
                }case ProgramConstants.ACTIONNEWW ->{
                    driverWait.until(numberOfWindowsToBe(Integer.parseInt(val)));
                    int num = driver.getWindowHandles().size() -1;
                    String w = (String)driver.getWindowHandles().toArray()[num];
                    driver.switchTo().window(w);
                }case ProgramConstants.ACTIONINIWIN ->{
                    String w = (String)driver.getWindowHandles().toArray()[0];
                    driver.switchTo().window(w);
                }case ProgramConstants.ACTIONESPERAR ->{
                    esperar();
                }case ProgramConstants.ACTIONCERRAR ->{
                    iwd.driverQuit();
                    driverStart("Chrome","");
                }case ProgramConstants.ACTIONESPERAROBJ ->{
                    driverWait.until(ExpectedConditions.visibilityOf(element));
                }case ProgramConstants.ACTIONFRAME ->{
                    driver.switchTo().frame(element);
                }case ProgramConstants.ACTIONREGRESAR ->{
                    driver.switchTo().defaultContent();
                }case ProgramConstants.ACTIONDROPDOWN ->{
                    Select drpCountry = new Select(element);
                    drpCountry.selectByVisibleText(val);
                }case ProgramConstants.ACTIONOPENNEW ->{
                    JavascriptExecutor js = (JavascriptExecutor) driver; 
                    js.executeScript("window.open('"+val+"','_blank');");
                }case ProgramConstants.ACTIONRUNCLICK ->{
                    JavascriptExecutor js = (JavascriptExecutor) driver; 
                    js.executeScript("arguments[0].click();",element);
                }case ProgramConstants.ACTIONTABLA->{
                    //moveToElement(element);
                    Manager.getInstance().updateTableData(val + ((JavascriptExecutor) driver).
                        executeScript(tableScript(currentAtr)).toString());
                }case ProgramConstants.ACTIONCODIGOSIM->{
                    
                    element.sendKeys("8950303030"+Manager.getInstance().
                            getGuardarSim().get(Manager.getInstance().getCall()));
                }case ProgramConstants.ACTIONEXCELELE->{
                    element.sendKeys(Manager.getInstance().getDatosExcel()
                            .get(Manager.getInstance().getCall()).get(Integer.parseInt(val)));
                }case ProgramConstants.ACTIONEXCELWEB->{
                    element.sendKeys(Manager.getInstance().getDatosExcel()
                            .get(Manager.getInstance().getCall()).get(Integer.parseInt(val)));
                    
                }case ProgramConstants.ACTIONRUNFN->{
                    JavascriptExecutor js = (JavascriptExecutor) driver; 
                    js.executeScript(val);
                }case ProgramConstants.ACTIONCLEAR->{
                    element.clear();
                }case ProgramConstants.ACTIONIE->{
                    iwd.driverQuit();
                    driverStart("IE","");//(//span[contains(text(),'TIGO') and not(contains(text(),'MONEY'))])[1]
                }case "30"->{
                    //iwd.driverQuit();
                    //driverStart("IE","");//(//span[contains(text(),'TIGO') and not(contains(text(),'MONEY'))])[1]
                    String w = (String)driver.getWindowHandles().toArray()[1];
                    driver.switchTo().window(w);
                }case "31"->{
                    String codigo = element.getText().trim();
                    codigo = codigo.split(" ")[7].substring(0,6);
                    System.out.println(codigo);
                    masterCod = codigo;
                }case "32"->{
                    element.sendKeys(masterCod);
                }case "33"->{
                    String codigo = element.getText().trim();
                    codigo = codigo.substring(0,6);
                    System.out.println(codigo);
                    masterCod = codigo;
                }case "45"->{
                    for(int i = 0; i<10; i++){
                        try {
                            
                            if(waitAction("5","xpath", "//*[contains(text(),'"+
                                    Manager.getInstance().getGuardarSim().get(Manager.getInstance().getCall())+"')]" , "")){
                                i = 99;
                            }
                            String temp = i >= 1 ? "(//*[@class=' dr-dscr-button rich-datascr-button'])[4]" : "(//*[@class=' dr-dscr-button rich-datascr-button'])[2]";
                            waitAction("1","xpath", temp , "");
                        } catch (Exception e) {
                            
                        }
                    }
                    
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("No se pudo realizar la accion tres");
            System.out.println(act + " ------- " + element + " ---- " + val);
            System.out.println("Mensaje: " + e.getMessage());
            System.out.println("Causa: " + e.getCause());
            e.printStackTrace();
            return false;
        }finally{
            
        }
    }
    
    public ArrayList<String> getImagenes(){
        return this.imagenes;
    }
    
    public void pilas(){
        waitAction("3", "0","0", "https://www.google.com/?hl=es");
        waitAction("2", "name","q", "prueba terminada con exiito");
    }

    public boolean isDetenerFlujo() {
        return detenerFlujo;
    }

    public void setDetenerFlujo(boolean detenerFlujo) {
        this.detenerFlujo = detenerFlujo;
    }
}
