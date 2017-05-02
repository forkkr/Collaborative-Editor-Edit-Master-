
package Client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WelcomePage extends javax.swing.JFrame {
    public WelcomePage() {
        super("WELCOME PAGE - Edit Master");
        super.setBounds(240, 100, 920, 645);
        initComponents();
        setSize(925 , 545);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        onLineButton = new javax.swing.JButton();
        offLineButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 75)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("           WELCOME");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(291, 11, 587, 95);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 110)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 153, 255));
        jLabel5.setText("   EDIT MASTER");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(82, 112, 842, 128);

        onLineButton.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        onLineButton.setForeground(new java.awt.Color(0, 153, 0));
        onLineButton.setText("ONLINE"); // NOI18N
        onLineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onLineButtonActionPerformed(evt);
            }
        });
        jPanel1.add(onLineButton);
        onLineButton.setBounds(663, 372, 222, 51);

        offLineButton.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        offLineButton.setForeground(new java.awt.Color(0, 0, 255));
        offLineButton.setText("OFFLINE");
        offLineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                offLineButtonActionPerformed(evt);
            }
        });
        jPanel1.add(offLineButton);
        offLineButton.setBounds(663, 293, 222, 51);

        jLabel2.setForeground(new java.awt.Color(0, 102, 153));
        jLabel2.setText("Copyright © Kashob and Amit, CSEDU 22 ");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(684, 492, 201, 36);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Client/welcomePage.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(0, 0, 920, 540);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void offLineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_offLineButtonActionPerformed
        this.dispose();
        try {
            new EditMaster().setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(WelcomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_offLineButtonActionPerformed

    private void onLineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onLineButtonActionPerformed
        this.dispose();
       new ipAddress().setVisible(true);
    }//GEN-LAST:event_onLineButtonActionPerformed

//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new WelcomePage().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton offLineButton;
    private javax.swing.JButton onLineButton;
    // End of variables declaration//GEN-END:variables
}
