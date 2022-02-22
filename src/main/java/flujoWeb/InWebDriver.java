/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package flujoWeb;

import org.openqa.selenium.WebDriver;

/**
 *
 * @author rigcampos
 */
public interface InWebDriver {
    WebDriver driverStart();
    void beforeStart();
    String[] getErrorDesc();
    void driverQuit();
}
