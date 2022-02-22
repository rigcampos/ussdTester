/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flujoWeb;

import java.security.DrbgParameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

/**
 *
 * @author rigcampos
 */
public class InternalChromeDriver implements InWebDriver{
    ChromeDriver driver;
    ChromeOptions options;
    
    public InternalChromeDriver() {
        options = new ChromeOptions();
    }

    @Override
    public WebDriver driverStart() {
        beforeStart();
        System.setProperty("webdriver.chrome.driver", "resources/drivers/chromedriver.exe");
        driver = new ChromeDriver(this.options);
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
        this.options.addArguments("start-maximized");
        this.options.addArguments("incognito");
        this.options.setBinary("resources/chrome 98/chrome.exe");
        //this.options.addArguments("disable-popup-blocking");
        //this.options.addArguments("disable-infobars"); 
    }
        
}
