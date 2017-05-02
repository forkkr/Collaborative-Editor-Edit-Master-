package Client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SignUP extends javax.swing.JFrame {

    private String serverIpAddress;

    public SignUP(String serverIpAddress) {
        super("SIGN UP - Edit Master");
        this.serverIpAddress = serverIpAddress;
        super.setBounds(240, 100, 920, 645);
        initComponents();
        setSize(795 , 510);
//        setLayout(new BorderLayout());
//	JLabel background=new JLabel(new ImageIcon("signUp.jpg"));
//	add(background);
//	background.setLayout(new FlowLayout());
//        setLayout(new BorderLayout());
//	setContentPane(new JLabel(new ImageIcon("C:\\Users\\Computer\\Downloads\\colorful design.png")));
//	setLayout(new FlowLayout())
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        signnUP = new javax.swing.JLabel();
        usernamelabel = new javax.swing.JLabel();
        namelabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        emailTextField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        userNameTextField = new javax.swing.JTextField();
        backButton = new javax.swing.JButton();
        DoneButton = new javax.swing.JButton();
        warningLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jButton2.setText("DONE");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        signnUP.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        signnUP.setForeground(new java.awt.Color(204, 204, 255));
        signnUP.setText("    SIGN UP");
        jPanel1.add(signnUP);
        signnUP.setBounds(257, 35, 219, 64);

        usernamelabel.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        usernamelabel.setForeground(new java.awt.Color(204, 204, 204));
        usernamelabel.setText("    USER NAME :");
        jPanel1.add(usernamelabel);
        usernamelabel.setBounds(52, 129, 171, 20);

        namelabel.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        namelabel.setForeground(new java.awt.Color(204, 204, 204));
        namelabel.setText("   NAME :");
        jPanel1.add(namelabel);
        namelabel.setBounds(123, 163, 100, 44);

        emailLabel.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(153, 153, 153));
        emailLabel.setText("  EMAIL :");
        jPanel1.add(emailLabel);
        emailLabel.setBounds(123, 213, 100, 53);

        passwordLabel.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(153, 153, 153));
        passwordLabel.setText("PASSWORD :");
        jPanel1.add(passwordLabel);
        passwordLabel.setBounds(83, 285, 140, 27);

        nameTextField.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel1.add(nameTextField);
        nameTextField.setBounds(233, 168, 300, 39);

        emailTextField.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        emailTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTextFieldActionPerformed(evt);
            }
        });
        jPanel1.add(emailTextField);
        emailTextField.setBounds(233, 222, 300, 38);

        passwordField.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel1.add(passwordField);
        passwordField.setBounds(233, 274, 300, 38);

        userNameTextField.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel1.add(userNameTextField);
        userNameTextField.setBounds(233, 119, 300, 38);

        backButton.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        backButton.setText("BACK");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        jPanel1.add(backButton);
        backButton.setBounds(495, 397, 126, 50);

        DoneButton.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        DoneButton.setText("DONE");
        DoneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DoneButtonActionPerformed(evt);
            }
        });
        jPanel1.add(DoneButton);
        DoneButton.setBounds(121, 404, 141, 50);

        warningLabel.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        warningLabel.setForeground(new java.awt.Color(255, 102, 102));
        warningLabel.setText("Warning : Username has been already taken !!");
        jPanel1.add(warningLabel);
        warningLabel.setBounds(216, 330, 445, 49);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Client/signUp.jpg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 790, 490);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DoneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DoneButtonActionPerformed
        String userName, name, email, password;
        userName = userNameTextField.getText();
        name = nameTextField.getText();
        email = emailTextField.getText();
        password = passwordField.getText();
        InfoSend is = new InfoSend(serverIpAddress, userName, password, true);
        try {
            is.send();
            if (is.recieve().equals("exist")) {
                warningLabel.setVisible(true);
                userNameTextField.setText("");
                //emailTextField.setText("");
                passwordField.setText("");
            }
            else
            {
                new Online(serverIpAddress).setVisible(true);
                this.dispose();
            }
        } catch (IOException ex) {
            Logger.getLogger(SignUP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_DoneButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        new Online(serverIpAddress).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void emailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTextFieldActionPerformed

    }//GEN-LAST:event_emailTextFieldActionPerformed

    /**
     * @param args the command line arguments //
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(SignUP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(SignUP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(SignUP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(SignUP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//               // new SignUP().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DoneButton;
    private javax.swing.JButton backButton;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JLabel namelabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel signnUP;
    private javax.swing.JTextField userNameTextField;
    private javax.swing.JLabel usernamelabel;
    public javax.swing.JLabel warningLabel;
    // End of variables declaration//GEN-END:variables
}
