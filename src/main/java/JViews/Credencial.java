/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JViews;

import automatizacionweb.Manager;
import automatizacionweb.ProgramConstants;

/**
 *
 * @author rigcampos
 */
public class Credencial {
    
    private String keyText;
    private String valText;
    private javax.swing.JLabel inputKey;
    //private javax.swing.JTextField inputVal;
    //private javax.swing.JPasswordField inputPass;
    private javax.swing.JComponent component;
    private Boolean clave;
    
    public Credencial(String key){
        verificarInput(key);
        inputKey = new javax.swing.JLabel(key);
        //inputVal = new javax.swing.JTextField();
        //inputPass = new javax.swing.JPasswordField();
        component = clave ? new javax.swing.JPasswordField() : new javax.swing.JTextField();
        formato();
        revisarG(key);
    }
    
    private void formato(){
        inputKey.setFont(ProgramConstants.CREDENCIALFONT);
        inputKey.setForeground(ProgramConstants.BTNCOLORBLANCO);
        //component.setFont(ProgramConstants.CREDENCIALFONT);
        component.setBackground(new java.awt.Color(0, 55, 123));
        component.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        component.setForeground(new java.awt.Color(255, 255, 255));
        component.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        //component.setCaretColor(new java.awt.Color(255, 255, 255));
        component.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        component.setOpaque(false);
        //inputKey.setFont(ProgramConstants.CREDENCIALFONT);
    }
    
    private void verificarInput(String key){
        if(key.contains(ProgramConstants.KEYPASS) || key.contains(ProgramConstants.KEYFECHA) 
                || key.contains(ProgramConstants.KEYCVV)){
            clave = true;
        }else{
            clave = false;
        }
    }
    
    public void internalBounds(int x,int y,int w,int h){
        inputKey.setBounds(x, y, w, h);
        component.setBounds(x, y + h + ProgramConstants.CREDENINTERSEPA, w, h);
    }
    
    public javax.swing.JTextField getInputVal(){
        return (javax.swing.JTextField)this.component;
    }
    
    public javax.swing.JLabel getInputKey(){
        return this.inputKey;
    }

    public String getKeyText() {
        keyText = inputKey.getText();
        return keyText;
    }
    
    public String getValText(){
        if(clave){
            javax.swing.JPasswordField pass = (javax.swing.JPasswordField) component;
            valText = new String(pass.getPassword());
        }else{
            valText = ((javax.swing.JTextField)component).getText();
        }
        
        return valText;
    }
    
    private void revisarG(String k){
        String v = (String) Manager.getInstance().getCredencialesGuardadas().get(k);
        if(clave)((javax.swing.JPasswordField)component).setText(v);
        else ((javax.swing.JTextField)component).setText(v);
        
    }
    
}
