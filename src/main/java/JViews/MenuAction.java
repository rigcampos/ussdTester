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
import javax.swing.JLabel;

/**
 *
 * @author rigcampos
 */
public class MenuAction extends MouseAdapter{
    
    String menu;

    public MenuAction(String menu) {
        this.menu = menu;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        ViewManager.getInstance().deleteAll();
        switch(menu){
            case ProgramConstants.MENURECURSIVA->{
                e.getComponent().setBackground(ProgramConstants.BTNCOLORMENUSE);
                ViewManager.getInstance().showBtnFlujos();
                ViewManager.getInstance().getMenuSelected().setBackground(ProgramConstants.BTNCOLORMENUDE);
                ViewManager.getInstance().setMenuSelected((JLabel)e.getComponent());
            }
            case ProgramConstants.MENUUSSD->{
                e.getComponent().setBackground(ProgramConstants.BTNCOLORMENUSE);
                ViewManager.getInstance().showUSSDCredencial(menu);
                ViewManager.getInstance().getMenuSelected().setBackground(ProgramConstants.BTNCOLORMENUDE);
                ViewManager.getInstance().setMenuSelected((JLabel)e.getComponent());
                ViewManager.getInstance().showBtnSoloUssd();
                ViewManager.getInstance().showBtnSoloPlataforma();
                //ViewManager.getInstance().showBtnContinuar();
            }
            case ProgramConstants.MENUFLUJOSQA->{
                e.getComponent().setBackground(ProgramConstants.BTNCOLORMENUSE);
                //ViewManager.getInstance().showUSSDCredencial(menu);
                ViewManager.getInstance().getMenuSelected().setBackground(ProgramConstants.BTNCOLORMENUDE);
                ViewManager.getInstance().setMenuSelected((JLabel)e.getComponent());
                //ViewManager.getInstance().showBtnSoloUssd();
                //ViewManager.getInstance().showBtnSoloPlataforma();
                //ViewManager.getInstance().showBtnContinuar();
            }
            default ->{
                System.out.println("Boton no asignado aun D:");
            }
                
        }
        
    }
    
}
