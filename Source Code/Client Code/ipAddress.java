package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author K ROY
 */
public class ipAddress extends JOptionPane {

    private String serverIpAddress;
    public Socket s = null;
    DataInputStream din = null;
    DataOutputStream dout = null;

    public ipAddress() {
        serverIpAddress = JOptionPane.showInputDialog("Please enter a Server Ip address:");
        if (serverIpAddress.equals("")) {
            JOptionPane.showMessageDialog(this, "Invalid :(");
            new WelcomePage().setVisible(true);
        } else {
            try {
                s = new Socket(serverIpAddress, 5555);
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                dout.writeUTF(serverIpAddress);
                dout.flush();
                String str = din.readUTF();
                System.out.println(serverIpAddress);
                if(str.equals("validip"))
                new Online(serverIpAddress).setVisible(true);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Server Not Found");
                new WelcomePage().setVisible(true);
            }
        }
    }
}
