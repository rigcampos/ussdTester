/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package JViews;

import automatizacionweb.Manager;
import automatizacionweb.ProgramConstants;
import com.tigosv.ussdtester.test.BaseClass;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

/**
 *
 * @author rigcampos
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        logo.setIcon(new javax.swing.ImageIcon(ProgramConstants.LOGODIR)); // NOI18N
        logo.setText("");
        Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, sc.width, sc.height);
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
        mRecursivas.addMouseListener(new MenuAction(mRecursivas.getText()));
        mUSSD.addMouseListener(new MenuAction(mUSSD.getText()));
        mFlujosQA.addMouseListener(new MenuAction(mFlujosQA.getText()));
        //ViewManager.getInstance().setMenuSelected(mRecursivas);
        //setResizable(false);
        //setExtendedState(ICONIFIED); //31797833
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainMenu = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        mRecursivas = new javax.swing.JLabel();
        mUSSD = new javax.swing.JLabel();
        mFlujosQA = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quality Automation");
        setExtendedState(6);

        mainMenu.setBackground(new java.awt.Color(0, 25, 80));

        logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logo.setIcon(new javax.swing.ImageIcon("C:\\Users\\rigcampos\\Documents\\NetBeansProjects\\automatizacionWeb\\resources\\image\\tigo logo.PNG")); // NOI18N

        mRecursivas.setBackground(new java.awt.Color(0, 55, 123));
        mRecursivas.setFont(new java.awt.Font("SansSerif", 1, 17)); // NOI18N
        mRecursivas.setForeground(new java.awt.Color(255, 255, 255));
        mRecursivas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mRecursivas.setText(ProgramConstants.MENURECURSIVA);
        mRecursivas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mRecursivas.setOpaque(true);

        mUSSD.setBackground(new java.awt.Color(0, 25, 80));
        mUSSD.setFont(new java.awt.Font("SansSerif", 1, 17)); // NOI18N
        mUSSD.setForeground(new java.awt.Color(255, 255, 255));
        mUSSD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mUSSD.setText(ProgramConstants.MENUUSSD);
        mUSSD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mUSSD.setOpaque(true);

        mFlujosQA.setBackground(new java.awt.Color(0, 25, 80));
        mFlujosQA.setFont(new java.awt.Font("SansSerif", 1, 17)); // NOI18N
        mFlujosQA.setForeground(new java.awt.Color(255, 255, 255));
        mFlujosQA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mFlujosQA.setText(ProgramConstants.MENUFLUJOSQA);
        mFlujosQA.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mFlujosQA.setOpaque(true);

        javax.swing.GroupLayout mainMenuLayout = new javax.swing.GroupLayout(mainMenu);
        mainMenu.setLayout(mainMenuLayout);
        mainMenuLayout.setHorizontalGroup(
            mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logo, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
            .addComponent(mRecursivas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mUSSD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mFlujosQA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainMenuLayout.setVerticalGroup(
            mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainMenuLayout.createSequentialGroup()
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mRecursivas, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mUSSD, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mFlujosQA, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        mainPanel.setBackground(new java.awt.Color(0, 55, 123));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 858, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 512, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new MainWindow().setVisible(true);
                Manager.getInstance().readDocument();
                ViewManager vm = ViewManager.getInstance();
                vm.showBtnFlujos();
            }
        });
    }
    
    public javax.swing.JPanel getMainMenu(){
        return this.mainMenu;
    }
    
    public javax.swing.JPanel getMainPanel(){
        return this.mainPanel;
    }
    
    public javax.swing.JLabel getMRecursivas(){
        return this.mRecursivas;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel logo;
    private javax.swing.JLabel mFlujosQA;
    private javax.swing.JLabel mRecursivas;
    private javax.swing.JLabel mUSSD;
    private javax.swing.JPanel mainMenu;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
