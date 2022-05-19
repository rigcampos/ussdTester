/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JViews;

import automatizacionweb.Manager;
import automatizacionweb.ProgramConstants;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelData.ModelCredencial;

/**
 *
 * @author rigcampos
 */
public class ViewManager{
    
    private MainWindow mw;
    private JPanel mainPanel;
    private JPanel mainMenu;
    private static ViewManager instance;
    private ArrayList<Credencial> credenciales;
    private JLabel menuSelected;
    private String errorMessage="";

    private ViewManager() {
        
        mw =  new MainWindow();
        mainPanel = mw.getMainPanel();
        credenciales = new ArrayList<Credencial>();
        menuSelected = mw.getMRecursivas();
    }
    
    public synchronized static ViewManager getInstance(){
        if(instance == null){
            instance = new ViewManager();
        }
        return instance;
    }
    
    public void showMainTitle(String title){
        JtitleBar h3 = new JtitleBar(title);
        h3.mainTitle();
        mainPanel.add(h3);
        h3.setBounds(0,0,mainPanel.getSize().width, 96);
    }
    
    public void showBtnFlujos(){
        
        showMainTitle("Pruebas regresivas");
        int cant_x = 0, cant_y = 0;
        for(Map.Entry<String, TreeMap> entry : Manager.getInstance().getMasterMap().entrySet()){
            JbuttonProgram btn = new JbuttonProgram(entry.getKey().split(ProgramConstants.STARTTITLE)[1]);
            btn.generalFormat();
            btn.addMouseListener(new ActionBtn(ProgramConstants.BTNREGRESIVAS));
            mainPanel.add(btn);
            btn.setBounds((ProgramConstants.BTNSTARTX + (ProgramConstants.BTNSPACE * cant_x) + 
                    (ProgramConstants.BTNWIDTH * cant_x)), 
                    (ProgramConstants.BTNSTARTY + (ProgramConstants.BTNSPACE * cant_y) + 
                    (ProgramConstants.BTNHEIGHT * cant_y)),
                    ProgramConstants.BTNWIDTH, 
                    ProgramConstants.BTNHEIGHT);
            
            cant_y = cant_x == (ProgramConstants.BTNPERX - 1) ? cant_y+1 : cant_y ;
            cant_x = cant_x == (ProgramConstants.BTNPERX - 1) ? 0 : cant_x+1 ;
            
        }
        mainPanel.validate();
        mainPanel.repaint();
        mainPanel.setVisible(true);
    }
    
    public void showCredenciales(String key){
        showMainTitle("Credenciales necesarias");
        int cant_x = 0, cant_y = 0;
        for(String st :Manager.getInstance().getCredencialesMap().get(ProgramConstants.STARTTITLE+key)){
            Credencial cr = new Credencial(st);
            mainPanel.add(cr.getInputKey());
            mainPanel.add(cr.getInputVal());
            cr.internalBounds(ProgramConstants.BTNSTARTX + (ProgramConstants.CREDENWIDTH * cant_x)
                    + (ProgramConstants.BTNSPACE * cant_x), 
                    (ProgramConstants.BTNSTARTY + (ProgramConstants.CREDENHEIGHT * cant_y*2) + 
                            (ProgramConstants.CREDENEXTERSEPA * cant_y)),
                    ProgramConstants.CREDENWIDTH, ProgramConstants.CREDENHEIGHT);

            cant_y = cant_y+1;
            credenciales.add(cr);
            cant_x = cant_y == ProgramConstants.BTNPERY ? cant_x + 1 : cant_x;
            cant_y = cant_y == ProgramConstants.BTNPERY ? 0 : cant_y;
        }
        mainPanel.validate();
        mainPanel.repaint();
        mainPanel.setVisible(true);
    }
    
    public void showUSSDCredencial(String key){
        
        showMainTitle("Credenciales USSD");
        int cant_x = 0, cant_y = 0;
        for(String st : Manager.getInstance().getCredencialesPojo().get("Flujo USSD").getCredenciales().keySet()){
            Credencial cr = new Credencial(st);
            mainPanel.add(cr.getInputKey());
            mainPanel.add(cr.getInputVal());
            cr.internalBounds(ProgramConstants.BTNSTARTX + (ProgramConstants.CREDENWIDTH * cant_x)
                    + (ProgramConstants.BTNSPACE * cant_x), 
                    (ProgramConstants.BTNSTARTY + (ProgramConstants.CREDENHEIGHT * cant_y*2) + 
                            (ProgramConstants.CREDENEXTERSEPA * cant_y)),
                    ProgramConstants.CREDENWIDTH, ProgramConstants.CREDENHEIGHT);

            cant_y = cant_y+1;
            credenciales.add(cr);
            cant_x = cant_y == ProgramConstants.BTNPERY ? cant_x + 1 : cant_x;
            cant_y = cant_y == ProgramConstants.BTNPERY ? 0 : cant_y;
        }
        javax.swing.JRadioButton uatRadio = new javax.swing.JRadioButton();
        uatRadio.setText(ProgramConstants.BTNPLATAFORMAUAT);
        mainPanel.add(uatRadio);
        uatRadio.addMouseListener(new ActionBtn(ProgramConstants.BTNPLATAFORMAUAT));
        uatRadio.setBounds(ProgramConstants.BTNSTARTX + (ProgramConstants.CREDENWIDTH * cant_x)
                    + (ProgramConstants.BTNSPACE * cant_x), 
                    ((ProgramConstants.BTNSTARTY + (ProgramConstants.CREDENHEIGHT * cant_y*2) + 
                            (ProgramConstants.CREDENEXTERSEPA * cant_y))) + ProgramConstants.CREDENHEIGHT,
                    ProgramConstants.CREDENWIDTH, ProgramConstants.CREDENHEIGHT);
        mainPanel.validate();
        mainPanel.repaint();
        mainPanel.setVisible(true);
    }
    
    public void showCredencialTMY(String key){
        
        showMainTitle("Credenciales TMY necesarias");
        int cant_x = 0, cant_y = 0;
        for(String st : Manager.getInstance().getCredencialesPojo().get("Flujo TMY web").getCredenciales().keySet()){
            Credencial cr = new Credencial(st);
            mainPanel.add(cr.getInputKey());
            mainPanel.add(cr.getInputVal());
            cr.internalBounds(ProgramConstants.BTNSTARTX + (ProgramConstants.CREDENWIDTH * cant_x)
                    + (ProgramConstants.BTNSPACE * cant_x), 
                    (ProgramConstants.BTNSTARTY + (ProgramConstants.CREDENHEIGHT * cant_y*2) + 
                            (ProgramConstants.CREDENEXTERSEPA * cant_y)),
                    ProgramConstants.CREDENWIDTH, ProgramConstants.CREDENHEIGHT);

            cant_y = cant_y+1;
            credenciales.add(cr);
            cant_x = cant_y == ProgramConstants.BTNPERY ? cant_x + 1 : cant_x;
            cant_y = cant_y == ProgramConstants.BTNPERY ? 0 : cant_y;
        }
        
        mainPanel.validate();
        mainPanel.repaint();
        mainPanel.setVisible(true);
    }
    
    public void showCredencialPorta(String key){
        
        showMainTitle("Credenciales Portabilidad");
        int cant_x = 0, cant_y = 0;
        for(String st : Manager.getInstance().getCredencialesPojo().get("Flujo Porta web").getCredenciales().keySet()){
            Credencial cr = new Credencial(st);
            mainPanel.add(cr.getInputKey());
            mainPanel.add(cr.getInputVal());
            cr.internalBounds(ProgramConstants.BTNSTARTX + (ProgramConstants.CREDENWIDTH * cant_x)
                    + (ProgramConstants.BTNSPACE * cant_x), 
                    (ProgramConstants.BTNSTARTY + (ProgramConstants.CREDENHEIGHT * cant_y*2) + 
                            (ProgramConstants.CREDENEXTERSEPA * cant_y)),
                    ProgramConstants.CREDENWIDTH, ProgramConstants.CREDENHEIGHT);

            cant_y = cant_y+1;
            credenciales.add(cr);
            cant_x = cant_y == ProgramConstants.BTNPERY ? cant_x + 1 : cant_x;
            cant_y = cant_y == ProgramConstants.BTNPERY ? 0 : cant_y;
        }
        
        mainPanel.validate();
        mainPanel.repaint();
        mainPanel.setVisible(true);
    }
    
    public void showFlujosQA(){
        
        showMainTitle("Flujos QA");
        int cant_x = 0, cant_y = 0;
        String fqa[] = {"CT_USSD","Portabilidad","Tigo Money"}; 
        for(String entry : fqa){
            JbuttonProgram btn = new JbuttonProgram(entry);
            btn.generalFormat();
            btn.addMouseListener(new ActionBtn(entry));
            mainPanel.add(btn);
            btn.setBounds((ProgramConstants.BTNSTARTX + (ProgramConstants.BTNSPACE * cant_x) + 
                    (ProgramConstants.BTNWIDTH * cant_x)), 
                    (ProgramConstants.BTNSTARTY + (ProgramConstants.BTNSPACE * cant_y) + 
                    (ProgramConstants.BTNHEIGHT * cant_y)),
                    ProgramConstants.BTNWIDTH, 
                    ProgramConstants.BTNHEIGHT);
            
            cant_y = cant_x == (ProgramConstants.BTNPERX - 1) ? cant_y+1 : cant_y ;
            cant_x = cant_x == (ProgramConstants.BTNPERX - 1) ? 0 : cant_x+1 ;
            
        }
        mainPanel.validate();
        mainPanel.repaint();
        mainPanel.setVisible(true);
    }
    
    public void showFlujosQAUssd(){
        
        showMainTitle("Matriz de corte USSD");
        int cant_x = 0, cant_y = 0;
        String fqa[] = {"338","327","328","725"}; 
        for(String entry : fqa){
            JbuttonProgram btn = new JbuttonProgram(entry);
            btn.generalFormat();
            btn.addMouseListener(new ActionBtn(ProgramConstants.BTNCTACTIONUSSD));
            mainPanel.add(btn);
            btn.setBounds((ProgramConstants.BTNSTARTX + (ProgramConstants.BTNSPACE * cant_x) + 
                    (ProgramConstants.BTNWIDTH * cant_x)), 
                    (ProgramConstants.BTNSTARTY + (ProgramConstants.BTNSPACE * cant_y) + 
                    (ProgramConstants.BTNHEIGHT * cant_y)),
                    ProgramConstants.BTNWIDTH, 
                    ProgramConstants.BTNHEIGHT);
            
            cant_y = cant_x == (ProgramConstants.BTNPERX - 1) ? cant_y+1 : cant_y ;
            cant_x = cant_x == (ProgramConstants.BTNPERX - 1) ? 0 : cant_x+1 ;
            
        }
        mainPanel.validate();
        mainPanel.repaint();
        mainPanel.setVisible(true);
    }
    
    public void showFlujosQAPorta(){
        
        showMainTitle("Matriz de corte Portabilidad");
        int cant_x = 0, cant_y = 0;
        String fqa[] = {ProgramConstants.MENUFLUJOSQAPORT,ProgramConstants.MENUFLUJOSQAPORTOUT,"REVERSA PORT OUT","RECHAZO"}; 
        for(String entry : fqa){
            JbuttonProgram btn = new JbuttonProgram(entry);
            btn.generalFormat();
            btn.addMouseListener(new ActionBtn(ProgramConstants.BTNCTCREDENPORT));
            mainPanel.add(btn);
            btn.setBounds((ProgramConstants.BTNSTARTX + (ProgramConstants.BTNSPACE * cant_x) + 
                    (ProgramConstants.BTNWIDTH * cant_x)), 
                    (ProgramConstants.BTNSTARTY + (ProgramConstants.BTNSPACE * cant_y) + 
                    (ProgramConstants.BTNHEIGHT * cant_y)),
                    ProgramConstants.BTNWIDTH, 
                    ProgramConstants.BTNHEIGHT);
            
            cant_y = cant_x == (ProgramConstants.BTNPERX - 1) ? cant_y+1 : cant_y ;
            cant_x = cant_x == (ProgramConstants.BTNPERX - 1) ? 0 : cant_x+1 ;
            
        }
        mainPanel.validate();
        mainPanel.repaint();
        mainPanel.setVisible(true);
    }
    
    public void showBtnContinuar(){
        JbuttonProgram btn = new JbuttonProgram(ProgramConstants.BTNCONTINUAR);
            btn.generalFormat();
            btn.addMouseListener(new ActionBtn(ProgramConstants.BTNCONTINUAR));
            mainPanel.add(btn);
            btn.setBounds((int)(mainPanel.getWidth()/2) - (int)(ProgramConstants.BTNWIDTH/2),
                    (mainPanel.getHeight() - ProgramConstants.BTNHEIGHT - ProgramConstants.BTNSTARTX), 
                    ProgramConstants.BTNWIDTH, ProgramConstants.BTNHEIGHT);
    }
    
    public void showBtnContinuar(String key){
        JbuttonProgram btn = new JbuttonProgram(ProgramConstants.BTNCONTINUAR);
            btn.generalFormat();
            btn.addMouseListener(new ActionBtn(key));
            mainPanel.add(btn);
            btn.setBounds((int)(mainPanel.getWidth()/2) - (int)(ProgramConstants.BTNWIDTH/2),
                    (mainPanel.getHeight() - ProgramConstants.BTNHEIGHT - ProgramConstants.BTNSTARTX), 
                    ProgramConstants.BTNWIDTH, ProgramConstants.BTNHEIGHT);
    }
    
    public void showBtnSoloUssd(){
        JbuttonProgram btn = new JbuttonProgram(ProgramConstants.BTNSOLOUSSD);
            btn.generalFormat();
            btn.addMouseListener(new ActionBtn(ProgramConstants.BTNSOLOUSSD));
            mainPanel.add(btn);
            btn.setBounds((int)(mainPanel.getWidth()/8) - (int)(ProgramConstants.BTNWIDTH/2),
                    (mainPanel.getHeight() - ProgramConstants.BTNHEIGHT - ProgramConstants.BTNSTARTX), 
                    ProgramConstants.BTNWIDTH, ProgramConstants.BTNHEIGHT);
    }
    
    public void showBtnSoloPlataforma(){
        JbuttonProgram btn = new JbuttonProgram(ProgramConstants.BTNSOLOPLATAFORMA);
            btn.generalFormat();
            btn.addMouseListener(new ActionBtn(ProgramConstants.BTNSOLOPLATAFORMA));
            mainPanel.add(btn);
            btn.setBounds((int)((mainPanel.getWidth()/3)) - (int)(ProgramConstants.BTNWIDTH/2),
                    (mainPanel.getHeight() - ProgramConstants.BTNHEIGHT - ProgramConstants.BTNSTARTX), 
                    ProgramConstants.BTNWIDTH, ProgramConstants.BTNHEIGHT);
    }
    
    public void showBtnSoloCBSTSB(){
        JbuttonProgram btn = new JbuttonProgram(ProgramConstants.BTNSOLOTSBCBS);
            btn.generalFormat();
            btn.addMouseListener(new ActionBtn(ProgramConstants.BTNSOLOTSBCBS));
            mainPanel.add(btn);
            btn.setBounds((int)((mainPanel.getWidth()/1.84)) - (int)(ProgramConstants.BTNWIDTH/2),
                    (mainPanel.getHeight() - ProgramConstants.BTNHEIGHT - ProgramConstants.BTNSTARTX), 
                    ProgramConstants.BTNWIDTH, ProgramConstants.BTNHEIGHT);
    }
    
    public String showInputBox(String title){
        String value = JOptionPane.showInputDialog(title);
        return value;
    }
    
    public void infoBox(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Info Box",JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void errorBox(){
        JOptionPane.showMessageDialog(null, errorMessage, "Error Box",JOptionPane.ERROR_MESSAGE);
    }
    
    public void minimizarMainW(){
        mw.setExtendedState(JFrame.ICONIFIED);
    }
            
    public void deleteAll(){
        mainPanel.removeAll();
    }
    
    public JPanel getMainPanel(){
        return this.mainPanel;
    }
    
    public ArrayList<Credencial> getCredenciales(){
        return this.credenciales;
    }

    public JLabel getMenuSelected() {
        return menuSelected;
    }

    public void setMenuSelected(JLabel menuSelected) {
        this.menuSelected = menuSelected;
    }
    
    public void updateErrorMessage(String m){
        this.errorMessage = errorMessage + " - " + m; 
    }
    
    public String getErrorMessage(){
        return this.errorMessage;
    }
    
} 