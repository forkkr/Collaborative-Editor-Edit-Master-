
package Client;

import java.awt.event.KeyEvent;


public class Online extends javax.swing.JFrame {
    
        private String serverIpAddress;
    
    public Online(String serverIpAddress) {
        super("ONLINE MODE - Edit Master");
        super.setBounds(240, 100, 920, 645);
        this.serverIpAddress = serverIpAddress;
        initComponents();
        setSize(930, 550);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        signinbutton = new javax.swing.JButton();
        signupbutton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        signinbutton.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        signinbutton.setForeground(new java.awt.Color(0, 153, 0));
        signinbutton.setText("SIGN IN");
        signinbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signinbuttonActionPerformed(evt);
            }
        });
        signinbutton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                signinbuttonKeyPressed(evt);
            }
        });
        jPanel1.add(signinbutton);
        signinbutton.setBounds(600, 320, 216, 80);

        signupbutton.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        signupbutton.setForeground(new java.awt.Color(0, 51, 255));
        signupbutton.setText("SIGN UP");
        signupbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupbuttonActionPerformed(evt);
            }
        });
        signupbutton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                signupbuttonKeyPressed(evt);
            }
        });
        jPanel1.add(signupbutton);
        signupbutton.setBounds(110, 320, 220, 80);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 255));
        jLabel1.setText("\"COLLABORATION  IS  MAGIC\" ");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(110, 10, 754, 82);

        backButton.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        backButton.setText("BACK");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        jPanel1.add(backButton);
        backButton.setBounds(410, 420, 121, 46);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Client/online.jpg"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(-10, 0, 930, 530);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void signupbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupbuttonActionPerformed
       SignUP su = new SignUP(serverIpAddress);
        su.warningLabel.setVisible(false);
        su.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_signupbuttonActionPerformed

    private void signinbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signinbuttonActionPerformed

        SignIn si = new SignIn(serverIpAddress);
        si.warningLabel.setVisible(false);
        si.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_signinbuttonActionPerformed

    private void signupbuttonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_signupbuttonKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            SignUP su = new SignUP(serverIpAddress);
        su.warningLabel.setVisible(false);
        su.setVisible(true);
        this.dispose();
        }
    }//GEN-LAST:event_signupbuttonKeyPressed

    private void signinbuttonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_signinbuttonKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            SignIn si = new SignIn(serverIpAddress);
            si.warningLabel.setVisible(false);
            si.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_signinbuttonKeyPressed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        new WelcomePage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

//    public static void main(String args[])
//    {
//        //new Online().setVisible(true);
//    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton signinbutton;
    private javax.swing.JButton signupbutton;
    // End of variables declaration//GEN-END:variables
}
