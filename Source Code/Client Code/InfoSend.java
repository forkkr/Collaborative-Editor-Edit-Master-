package Client;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class InfoSend extends JOptionPane {
    
     
     String userName,name,email,password;
        boolean newUser;
        DataInputStream din;
        DataOutputStream dout;
        Socket s;
        String validity;
        private String serverIpAddress;


    InfoSend(String serverIpAddress, String userName,String password,boolean newUser)
    {
        this.serverIpAddress = serverIpAddress;
        System.out.println(serverIpAddress + "amit");
        this.userName = userName;
        this.password = password;
        this.newUser = newUser;
       // System.out.println(userName + " INFO " + password);
         try {
             s = new Socket(serverIpAddress, 5555);
             din = new DataInputStream(s.getInputStream());
             dout = new DataOutputStream(s.getOutputStream());
         } catch (IOException ex) {
             JOptionPane.showMessageDialog(this,"Server Not Found");
             new WelcomePage().setVisible(true);
         }
    }
    
    

     void send() throws IOException 
    {
//        System.out.println(s+" "+"socket");
//        System.out.println(din + " "+ "din");
//        System.out.println(dout + " "+dout);
        String sendInfo;
        if(newUser)
            sendInfo = "1";//1 to check new user
        else
            sendInfo = "0";//0 to check old user
        sendInfo += (userName + " " + password);
        //System.out.println(sendInfo);
        dout.writeUTF(sendInfo);
        //System.out.println("Yes");
        validity = din.readUTF();
        //validity.equals("valid");

    }
    public String recieve() throws IOException
    {
        return validity;
    }
}