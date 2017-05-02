package Client;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignIn extends javax.swing.JFrame {
    
    private String serverIpAddress;
    
    public SignIn(String serverIpAddress) {
        super("SIGN IN - Edit Master");
        super.setBounds(240, 100, 920, 645);
        this.serverIpAddress = serverIpAddress;
        initComponents();
        setSize(785 , 510);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        signIn = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        userNameTextField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        logInButton1 = new javax.swing.JButton();
        warningLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        signIn.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        signIn.setText("               SIGN IN");
        jPanel1.add(signIn);
        signIn.setBounds(186, 16, 413, 90);

        passwordLabel.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        passwordLabel.setText("PASSWORD : ");
        jPanel1.add(passwordLabel);
        passwordLabel.setBounds(96, 243, 146, 23);

        userNameLabel.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        userNameLabel.setText("USER NAME : ");
        jPanel1.add(userNameLabel);
        userNameLabel.setBounds(96, 151, 153, 23);

        backButton.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        backButton.setText("BACK");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        backButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                backButtonKeyPressed(evt);
            }
        });
        jPanel1.add(backButton);
        backButton.setBounds(486, 396, 124, 49);

        userNameTextField.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        userNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameTextFieldActionPerformed(evt);
            }
        });
        jPanel1.add(userNameTextField);
        userNameTextField.setBounds(267, 148, 280, 33);

        passwordField.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });
        jPanel1.add(passwordField);
        passwordField.setBounds(267, 234, 280, 31);

        logInButton1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        logInButton1.setText("LOG IN");
        logInButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logInButton1ActionPerformed(evt);
            }
        });
        logInButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                logInButton1KeyPressed(evt);
            }
        });
        jPanel1.add(logInButton1);
        logInButton1.setBounds(176, 396, 137, 49);

        warningLabel.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        warningLabel.setForeground(new java.awt.Color(255, 51, 51));
        warningLabel.setText("Warning: INVALID PASSWORD or USERNAME");
        jPanel1.add(warningLabel);
        warningLabel.setBounds(156, 286, 488, 47);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Client/signIn.jpg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 780, 510);

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

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        new Online(serverIpAddress).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void logInButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logInButton1ActionPerformed

        String userName, password;
        userName = userNameTextField.getText();
        password = passwordField.getText();
        InfoSend is = new InfoSend(serverIpAddress, userName, password, false);
        try {
            //error  checking should be added here
            //System.out.println("here");
            is.send();
            //System.out.println("after");
           // System.out.println(is.recieve());
            if (!is.recieve().equals("valid")) {
                passwordField.setText("");
                warningLabel.setVisible(true);

            } else {
               this.dispose();
                FinalProject em = new FinalProject(serverIpAddress, userName);
                em.setVisible(true);

                //open edit master
            }
        } catch (IOException ex) {
            Logger.getLogger(SignUP.class.getName()).log(Level.SEVERE, null, ex);
        }
        // server theke validity check koro
        // jodi valid user hoy then Edit Master Collaborative Software open koro
        // else warning messsage show koro...

        //super.dispose();
        //new WelcomePage().setVisible(true);

    }//GEN-LAST:event_logInButton1ActionPerformed

    private void logInButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_logInButton1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String userName, password;
        userName = userNameTextField.getText();
        password = passwordField.getText();
        InfoSend is = new InfoSend(serverIpAddress,userName, password, false);
        try {
            //error  checking should be added here
            is.send();
            if (is.recieve().equals("valid")) {
                passwordField.setText("");
                warningLabel.setVisible(true);

            } else {
                
                this.dispose();
                FinalProject em = new FinalProject(serverIpAddress, userName);
                em.setVisible(true);

                //open edit master
            }
        } catch (IOException ex) {
            Logger.getLogger(SignUP.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }//GEN-LAST:event_logInButton1KeyPressed

    private void backButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_backButtonKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            new Online(serverIpAddress).setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_backButtonKeyPressed

    private void userNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userNameTextFieldActionPerformed

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFieldActionPerformed

    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton logInButton1;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel signIn;
    private javax.swing.JLabel userNameLabel;
    private javax.swing.JTextField userNameTextField;
    public javax.swing.JLabel warningLabel;
    // End of variables declaration//GEN-END:variables
}
