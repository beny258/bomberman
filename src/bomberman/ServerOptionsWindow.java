/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author David Benes
 */
public class ServerOptionsWindow extends javax.swing.JFrame {

    /**
     * Creates new form ServerInterfaceWindow
     */
    public ServerOptionsWindow() {
        initComponents();
    }
    
    private final String OKSTATUSTEXT = "OK";
    
    private boolean checkNum(String s) {
        char[] nums = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (char c:s.toCharArray()) {
            boolean b = false;
            for (char num:nums) {
                if (c == num) b = true;
            }
            if (!b) return false;
        }
        return true;
    }
    
    private void sizeChecker(JTextField jtf, JLabel jl) {
        int input = Integer.parseInt(jtf.getText());
        if (input < 5 || input > 25) jl.setText("Must be between 5 and 25!");
        else if (input % 2 == 0) jl.setText("Must be odd!");
        else jl.setText(OKSTATUSTEXT);
    }
    
    private boolean checkAllInput() {
        if (!this.checkNum(jTextFieldXSize.getText())) return false;
        if (!this.checkNum(jTextFieldYSize.getText())) return false;
        if (!this.checkNum(jTextFieldPortNumber.getText())) return false;
        if (!jLabelXSizeChecker.getText().equals(OKSTATUSTEXT)) return false;
        if (!jLabelYSizeChecker.getText().equals(OKSTATUSTEXT)) return false;
        return jLabelPortNumberChecker.getText().equals(OKSTATUSTEXT);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextFieldPortNumber = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabelPortNumberChecker = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldXSize = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldYSize = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jSliderPlayers = new javax.swing.JSlider();
        jLabelPlayers = new javax.swing.JLabel();
        jLabelXSizeChecker = new javax.swing.JLabel();
        jLabelYSizeChecker = new javax.swing.JLabel();
        jButtonNewServer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Bomberman - New Server");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Server Interface"));
        jPanel1.setName(""); // NOI18N

        jTextFieldPortNumber.setText("8000");
        jTextFieldPortNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldPortNumberFocusLost(evt);
            }
        });

        jLabel2.setText("Port Number:");

        jLabelPortNumberChecker.setText("OK");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldPortNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelPortNumberChecker, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldPortNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPortNumberChecker))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Game Interface"));

        jLabel3.setText("Map sizes (number of fields):");

        jLabel4.setText("X:");

        jTextFieldXSize.setText("15");
        jTextFieldXSize.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldXSizeFocusLost(evt);
            }
        });

        jLabel5.setText("Y:");

        jTextFieldYSize.setText("15");
        jTextFieldYSize.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldYSizeFocusLost(evt);
            }
        });

        jLabel6.setText("Number of players:");

        jSliderPlayers.setMaximum(4);
        jSliderPlayers.setMinimum(2);
        jSliderPlayers.setValue(2);
        jSliderPlayers.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderPlayersStateChanged(evt);
            }
        });

        jLabelPlayers.setText("2");

        jLabelXSizeChecker.setText("OK");

        jLabelYSizeChecker.setText("OK");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jTextFieldYSize, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabelYSizeChecker))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jTextFieldXSize, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabelXSizeChecker)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelPlayers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSliderPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldXSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelXSizeChecker))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldYSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelYSizeChecker))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabelPlayers))
                    .addComponent(jSliderPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonNewServer.setText("New Server");
        jButtonNewServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewServerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonNewServer)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonNewServer)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNewServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewServerActionPerformed
        if (!checkAllInput()) {
            System.out.println("Wrong input!");
            return;
        }
        try {
            Server server = new Server(Integer.parseInt(jTextFieldPortNumber.getText()),
                    jSliderPlayers.getValue(), Integer.parseInt(jTextFieldXSize.getText()),
                    Integer.parseInt(jTextFieldYSize.getText()));
            new ServerWindow(server).setVisible(true);
            this.dispose();
        } catch (IOException ex) {
            Logger.getLogger(ServerOptionsWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonNewServerActionPerformed

    private void jSliderPlayersStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderPlayersStateChanged
        jLabelPlayers.setText(""+jSliderPlayers.getValue());
    }//GEN-LAST:event_jSliderPlayersStateChanged

    private void jTextFieldXSizeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldXSizeFocusLost
        sizeChecker(jTextFieldXSize, jLabelXSizeChecker);
    }//GEN-LAST:event_jTextFieldXSizeFocusLost

    private void jTextFieldYSizeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldYSizeFocusLost
        sizeChecker(jTextFieldYSize, jLabelYSizeChecker);
    }//GEN-LAST:event_jTextFieldYSizeFocusLost

    private void jTextFieldPortNumberFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldPortNumberFocusLost
        int input = Integer.parseInt(jTextFieldPortNumber.getText());
        if (input < 1000 || input > 9999) jLabelPortNumberChecker.setText("Must be between 1000 and 9999!");
        else jLabelPortNumberChecker.setText(OKSTATUSTEXT);
    }//GEN-LAST:event_jTextFieldPortNumberFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonNewServer;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelPlayers;
    private javax.swing.JLabel jLabelPortNumberChecker;
    private javax.swing.JLabel jLabelXSizeChecker;
    private javax.swing.JLabel jLabelYSizeChecker;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSlider jSliderPlayers;
    private javax.swing.JTextField jTextFieldPortNumber;
    private javax.swing.JTextField jTextFieldXSize;
    private javax.swing.JTextField jTextFieldYSize;
    // End of variables declaration//GEN-END:variables
}
