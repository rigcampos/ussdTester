/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JViews;

import automatizacionweb.ProgramConstants;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;

/**
 *
 * @author rigcampos
 */
public class JbuttonProgram extends JButton {
    
    private String labelText;
    
    
    public JbuttonProgram() {
        
    }

    public JbuttonProgram(String text) {
        super(text);
        this.labelText = text;
    }
    
    public void generalFormat(){
        setBackground(ProgramConstants.BTNCOLORCELESTE);
        setFont(ProgramConstants.BTNFONT); // NOI18N
        setForeground(ProgramConstants.BTNCOLORBLANCO);
        setText(labelText);
        //setBorder(ProgramConstants.BTNBORDER);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
    }
    
}
