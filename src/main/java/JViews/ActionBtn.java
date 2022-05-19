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
                //System.out.println(ViewManager.getInstance().getMenuSelected().getText());ProgramConstants.MENUFLUJOSQAPORT
                Manager.getInstance(ViewManager.getInstance().getMenuSelected().getText()).start();
                Manager.getInstance().saveDataCredencial();
                
            }case ProgramConstants.BTNSOLOUSSD->{
                Manager.getInstance().readFlujoUssd();
                for(Credencial credencial : ViewManager.getInstance().getCredenciales()){
                    Manager.getInstance().getUserVals().put(credencial.getKeyText()
                            , credencial.getValText());
                }
                ViewManager.getInstance().minimizarMainW();
                if(!Manager.instanceCamino.equals("")){
                    Manager.getInstance(ProgramConstants.BTNSOLOUSSD).run();
                }else{
                    Manager.getInstance(ProgramConstants.BTNSOLOUSSD).start();
                }
                
                Manager.getInstance().saveDataCredencial();
                
            }case ProgramConstants.BTNSOLOPLATAFORMA->{
                Manager.getInstance().readFlujoUssd();
                for(Credencial credencial : ViewManager.getInstance().getCredenciales()){
                    Manager.getInstance().getUserVals().put(credencial.getKeyText()
                            , credencial.getValText());
                }
                ViewManager.getInstance().minimizarMainW();
                if(!Manager.instanceCamino.equals("")){
                    Manager.getInstance(ProgramConstants.BTNSOLOPLATAFORMA).run();
                }else{
                    Manager.getInstance(ProgramConstants.BTNSOLOPLATAFORMA).start();
                }
                Manager.getInstance().saveDataCredencial();
                
            }case ProgramConstants.BTNSOLOTSBCBS->{
                Manager.getInstance().readFlujoUssd();
                for(Credencial credencial : ViewManager.getInstance().getCredenciales()){
                    Manager.getInstance().getUserVals().put(credencial.getKeyText()
                            , credencial.getValText());
                }
                ViewManager.getInstance().minimizarMainW();
                if(!Manager.instanceCamino.equals("")){
                    Manager.getInstance(ProgramConstants.BTNSOLOTSBCBS).run();
                }else{
                    Manager.getInstance(ProgramConstants.BTNSOLOTSBCBS).start();
                }
                Manager.getInstance().saveDataCredencial();
                
            }case ProgramConstants.BTNPLATAFORMAUAT->{
                if(Manager.getInstance().getInicio() == 0){
                    Manager.getInstance().setInicio(3);
                    Manager.getInstance().setTerminar(6);
                    Manager.getInstance().setLinkSoap(ProgramConstants.UATWEBSERV);
                    //((javax.swing.JRadioButton)e.getComponent()).setSelected(true);
                }else{
                    Manager.getInstance().setInicio(0);
                    Manager.getInstance().setTerminar(3);
                    Manager.getInstance().setLinkSoap(ProgramConstants.PRODWEBSERV);
                }
                
            }case ProgramConstants.BTNCTUSSD->{
                ViewManager.getInstance().deleteAll();
                ViewManager.getInstance().showFlujosQAUssd();
            }case ProgramConstants.BTNREGRESIVAS->{
                ViewManager.getInstance().deleteAll();
                Manager.getInstance().setFlujoActual(ProgramConstants.STARTTITLE
                        +((JButton) e.getComponent()).getText());
                ViewManager.getInstance().showCredenciales(((JButton) e.getComponent()).getText());
                ViewManager.getInstance().showBtnContinuar();
            }case ProgramConstants.BTNCTACTIONUSSD->{
                Manager.getInstance().setUssdCT(((JButton) e.getComponent()).getText());
                Manager.getInstance().readFlujoUssdCT();
//                for(Credencial credencial : ViewManager.getInstance().getCredenciales()){
//                    Manager.getInstance().getUserVals().put(credencial.getKeyText()
//                            , credencial.getValText());
//                }
                ViewManager.getInstance().minimizarMainW();
                if(!Manager.instanceCamino.equals("")){
                    Manager.getInstance(ProgramConstants.BTNSOLOUSSD).run();
                }else{
                    Manager.getInstance(ProgramConstants.BTNSOLOUSSD).start();
                }
                
            }case ProgramConstants.BTNTIGOMONEY->{
                ViewManager.getInstance().deleteAll();
                ViewManager.getInstance().showCredencialTMY("");
                ViewManager.getInstance().showBtnContinuar();
                
            }case ProgramConstants.BTNCTCREDENPORT->{
                ViewManager.getInstance().deleteAll();
                ViewManager.getInstance().showCredencialPorta("");
                ViewManager.getInstance().showBtnContinuar("Continuar nuevo");
                Manager.getInstance().setPortFl(((JButton) e.getComponent()).getText());
                
            }case ProgramConstants.BTNPORTABILIDAD->{
                ViewManager.getInstance().deleteAll();
                ViewManager.getInstance().showFlujosQAPorta();
            }case "Continuar nuevo"->{
                for(Credencial credencial : ViewManager.getInstance().getCredenciales()){
                    Manager.getInstance().getUserVals().put(credencial.getKeyText()
                            , credencial.getValText());
                }
                ViewManager.getInstance().minimizarMainW();
                //System.out.println(ViewManager.getInstance().getMenuSelected().getText());ProgramConstants.MENUFLUJOSQAPORT
                Manager.getInstance(Manager.getInstance().getPortFl()).start();
                Manager.getInstance().saveDataCredencial();
            }
            default ->{
                System.out.println("BOTON SIN ASIGNAR");

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
