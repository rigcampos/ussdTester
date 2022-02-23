/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JViews;

import automatizacionweb.Manager;
import automatizacionweb.ProgramConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author rigcampos
 */
public class ActionBtn extends MouseAdapter{
    
    private String action="";
    
    public ActionBtn(){
    
    }
    
    public ActionBtn(String action){
        this.action = action;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        switch(action){
            case ProgramConstants.BTNCONTINUAR->{
                for(Credencial credencial : ViewManager.getInstance().getCredenciales()){
                    Manager.getInstance().getUserVals().put(credencial.getKeyText()
                            , credencial.getValText());
                }
                ViewManager.getInstance().minimizarMainW();
                
                Manager.getInstance(ViewManager.getInstance().getMenuSelected().getText()).start();
                
            }
            default ->{
                ViewManager.getInstance().deleteAll();
                Manager.getInstance().setFlujoActual(ProgramConstants.STARTTITLE
                        +((JButton) e.getComponent()).getText());
                ViewManager.getInstance().showCredenciales(((JButton) e.getComponent()).getText());
                ViewManager.getInstance().showBtnContinuar();
            }
                
        }
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().setBackground(ProgramConstants.BTNCOLORBLANCO);
        e.getComponent().setForeground(ProgramConstants.BTNCOLORCELESTE);
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        e.getComponent().setBackground(ProgramConstants.BTNCOLORCELESTE);
        e.getComponent().setForeground(ProgramConstants.BTNCOLORBLANCO);
    }
    
}
