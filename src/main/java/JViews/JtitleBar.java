/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JViews;

import javax.swing.JLabel;

/**
 *
 * @author rigcampos
 */
public class JtitleBar extends JLabel{
    
    private String labelText;

    public JtitleBar(String labelText) {
        super(labelText);
        this.labelText = labelText;
    }
    
    public void mainTitle(){
        setBackground(new java.awt.Color(0, 55, 123));
        setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        setForeground(new java.awt.Color(255, 255, 255));
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        //setText("VAMOS A EDITAR Y A BORRAR");
        setOpaque(true);
    }
}
