/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package com.tigosv.ussdtester.test;

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author rigcampos
 */
public class BaseClassNGTest {
    
    public BaseClassNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        ////--BaseClass.getInstance().startServer();
        //BaseClass.getInstance().beforeTest();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        //BaseClass.getInstance().stopServer();
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test
    public void testSomeMethod() {
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
