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
        System.out.println(atr);
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
            }
            default -> {return null;}
        } 
    }
    
    public void waitAction(String act,String key, String atr , String val){
        
        try {
            WebElement element = findElement(byParameter(key, atr));
            driverWait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return doAction( act, element, val);
                }
            });
        } catch (Exception e) {
            System.out.println("No se pudo realizar la accion dos");
            System.out.println("Mensaje: " + e.getMessage());
            System.out.println("Causa: " + e.getCause());
            e.printStackTrace();
            
        }finally{
        }
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
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("No se pudo realizar la accion tres");
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
}
