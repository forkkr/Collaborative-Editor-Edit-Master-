package Server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

/**
 *
 * @author K ROY
 */
public class helloServer extends javax.swing.JFrame {

    private String ip;

    public helloServer() {
        super("SERVER - Edit Master");
        super.setBounds(100, 100, 920, 645);
        initComponents();
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            ipNumberLabel.setText(ip);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Server Not Found");
            this.dispose();
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        severLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        ipNumberLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jDesktopPane1.setBackground(new java.awt.Color(102, 0, 102));
        jDesktopPane1.setForeground(new java.awt.Color(51, 51, 51));
        jDesktopPane1.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N

        severLabel.setFont(new java.awt.Font("Footlight MT Light", 1, 48)); // NOI18N
        severLabel.setForeground(new java.awt.Color(153, 204, 0));
        severLabel.setText(" Server Started !!! ");

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("Server IP:");

        ipNumberLabel.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        ipNumberLabel.setForeground(new java.awt.Color(255, 102, 102));
        ipNumberLabel.setText("ahjdkaah");

        jDesktopPane1.setLayer(severLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(ipNumberLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ipNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(140, 140, 140))
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(severLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(severLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addGap(32, 32, 32)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(ipNumberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                .addGap(143, 143, 143))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ipNumberLabel;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel severLabel;
    // End of variables declaration//GEN-END:variables
}
