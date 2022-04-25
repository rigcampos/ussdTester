/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flujoWeb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

/**
 *
 * @author rigcampos
 */
public class InternalExplorerDriver implements InWebDriver{
    InternetExplorerDriver driver;
    
    public InternalExplorerDriver() {
        
    }

    @Override
    public WebDriver driverStart() {
        beforeStart();
        System.setProperty("webdriver.ie.driver", "resources/drivers/IEDriverServer.exe");
        driver = new InternetExplorerDriver();
        return this.driver;
    }

    @Override
    public void driverQuit() {
        this.driver.close();
        this.driver.quit();
    }
    
    @Override
    public String[] getErrorDesc(){
        return new String[]{"chrome not reachable","target window already closed"};
    }

    @Override
    public void beforeStart() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        
        //this.options.addArguments("disable-popup-blocking");
        //this.options.addArguments("disable-infobars"); 
    }
        
}

