/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flujoWeb;

import org.openqa.selenium.WebDriver;

/**
 *
 * @author rigcampos
 */
public class InDriverFactory {

    public InDriverFactory() {
    }
    
    public InWebDriver getDriver(String driverType){
        switch(driverType){
            case "Chrome" -> {
                return new InternalChromeDriver();
            }case "IE" -> {
                return new InternalExplorerDriver();
            }
                
        }
        return new InternalChromeDriver();
    }
}
