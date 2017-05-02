package Server;

import java.net.*;

import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Server {

    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static final int maxClientsCount = 10;
    private static final clientThread[] threads = new clientThread[maxClientsCount];
    private static String userName;
    public static ArrayList<String> clients = new ArrayList<String>();
    private static String ip = null;

    public static void main(String[] args) throws IOException {

        int portNumber = 5555;
        if (args.length < 1) {
            System.out.println("Usage: java MultiThreadChatServer <portNumber>\n" + "Now using port number = " + portNumber);
        } else {
            portNumber = Integer.valueOf(args[0]).intValue();
        }

        DataInputStream din;
        DataOutputStream dout;

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }

        helloServer hs = new helloServer();
        hs.setVisible(true);
        System.out.println("done");
        String str = "";
        ip = InetAddress.getLocalHost().getHostAddress();
        System.out.println(ip);
        while (true) {

            clientSocket = serverSocket.accept();
            //System.out.println(clientSocket+" here");
            din = new DataInputStream(clientSocket.getInputStream());
            dout = new DataOutputStream(clientSocket.getOutputStream());
            str = din.readUTF();
            System.out.println(str);
            if (str.equals(ip)) {
                System.out.println("here");
                dout.writeUTF("validip");
                dout.flush();
            } //                clientSocket = serverSocket.accept();
            //                din = new DataInputStream(clientSocket.getInputStream());
            //                dout = new DataOutputStream(clientSocket.getOutputStream());
            else {
                
                RecieveInfo recieve = new RecieveInfo();
                recieve.process(str);
                userName = recieve.userName;
                recieve.recieveAndWrite();
                System.out.println(str +  "PRINT");
                if (recieve.validUser) {
                    System.out.println("valid");
                    dout.writeUTF("valid");
                    clientSocket = serverSocket.accept();
                    clients.add(userName);
                    try {
                        int i = 0;
                        for (i = 0; i < maxClientsCount; i++) {
                            if (threads[i] == null) {
                                // System.out.println("Start");
                                (threads[i] = new clientThread(clientSocket, threads, userName)).start();
                                break;
                            }
                        }
                        if (i == maxClientsCount) {
                            PrintStream os = new PrintStream(clientSocket.getOutputStream());
                            os.println("Server too busy. Try later.");
                            os.close();
                            clientSocket.close();
                        }
                    } catch (IOException e) {
                        System.out.println(e);
                    }

                } else {
                    //System.out.println("server  else");
                    if (recieve.alreadyExist) {
                        dout.writeUTF("exist");
                    } else {
                        dout.writeUTF("invalid");
                    }
                }
                dout.flush();

            }
//            dout.flush();
        }
    }
}

class clientThread extends Thread {

    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    private int maxClientsCount;
    private String userName;
    private BufferedReader br = null;

    public clientThread(Socket clientSocket, clientThread[] threads, String userName) {
        //System.out.println(clientSocket+"client thread");
        this.clientSocket = clientSocket;
        this.threads = threads;
        this.userName = userName;
        maxClientsCount = threads.length;
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        clientThread[] threads = this.threads;
        try {
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());

            //os.println("Enter your name.");
            String name = userName;
            os.print("ChAtWelcome " + name + " to our chat room.");
            os.write(0x00);
            os.flush();
            String message = "ChAtA new user " + name + " entered the chat room !!! \n Say hello to the fresher";
            sendMessage(message);
            sendOnlineUsers();
            while (true) {

                br = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
                String line = readUntilNull(br);
                if (line.startsWith("/quit")) {
                    break;
                }
                System.out.println(line + " thread inside");
                sendMessage(line);

            }

            String msg = "ChAtThe user " + name + " is leaving the chat room!!!";
            sendMessage(msg);
//            os.print("Bye " + name + "!!!\n Stay well.");
//            os.write(0x00);
//            os.flush();
            disconnect();
            Server.clients.remove(this.userName);
            sendOnlineUsers();
            /*
             * Close the output stream, close the input stream, close the socket.
             */
            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }

    public String readUntilNull(BufferedReader reader) {
        StringBuilder sb = new StringBuilder();
        String message = "";
        try {
            int ch;
            while ((ch = reader.read()) != -1) {
                if (ch == 0) {
                    message = sb.toString();
                    break;
                } else if (ch == 10) {
                    sb.append("\n");
                } else {
                    sb.append((char) ch);
                }
            }
        } catch (InterruptedIOException ex) {
            System.err.println("timeout!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("messgare username : " + message);
        return message;
    }

    String getCommand(String msg) {
        if (msg.startsWith("ChAt")) {
            return "chat";
        } else if (msg.startsWith("LiSt")) {
            return "list";
        } else if (msg.startsWith("TeXt")) {
            return "text";
        } else if (msg.startsWith("/quit")) {
            return "logOut";
        }
        else if(msg.startsWith("sAvE"))
        {
            return "save";
        }
        return "code";
    }

//        //************************** SAVE **************************\\
//    public void save(String s) {
//        PrintWriter fout = null;
//        try {
//            if (fileName == null) {
//                saveAs();
//            } else {
//                fout = new PrintWriter(new FileWriter(fileName));
//                //String s = mainarea.getText();
//                StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
//                while (st.hasMoreElements()) {
//                    fout.println(st.nextToken());
//                }
//                JOptionPane.showMessageDialog(this, "File Saved");
//                fileName = fileChooser.getSelectedFile().getName();
//                //setTitle(fileName + " - Edit Master");
//                fileContent = mainarea.getText();
//            }
//        } catch (IOException e) {
//        } finally {
//            if (fout != null) {
//                fout.close();
//            }
//        }
//    }
//
//    //************************** SAVE AS **************************\\
//    public void saveAs() {
//        PrintWriter fout = null;
//        int retval = -1;
//        try {
//            retval = fileChooser.showSaveDialog(this);// to check whether user decides to save the file or not
//            if (retval == JFileChooser.APPROVE_OPTION) {
//
//                if (fileChooser.getSelectedFile().exists()) {
//                    int option = JOptionPane.showConfirmDialog(this, "Do you want to replace this file?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
//                    if (option == 0) {
//                        fout = new PrintWriter(new FileWriter(fileChooser.getSelectedFile()));
//                        String s = mainarea.getText();
//                        StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
//                        while (st.hasMoreElements()) {
//                            fout.println(st.nextToken());
//                        }
//                        JOptionPane.showMessageDialog(this, "File Saved");
//                        fileName = fileChooser.getSelectedFile().getName();
//
//                        //setTitle(fileName + " - Edit Master");
//                        fileContent = mainarea.getText();
//                    } else {
//                        saveAs();
//                    }
//
//                } else {
//                    fout = new PrintWriter(new FileWriter(fileChooser.getSelectedFile()));
//                    String s = mainarea.getText();
//                    StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
//                    while (st.hasMoreElements()) {
//                        fout.println(st.nextToken());
//                    }
//                    JOptionPane.showMessageDialog(this, "File Saved");
//                    fileName = fileChooser.getSelectedFile().getName();
//
//                    //setTitle(fileName + " - Edit Master");
//                    fileContent = mainarea.getText();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fout != null) {
//                fout.close();
//            }
//        }
//    }
    
    private void sendOnlineUsers() {
        String userlist = "LiSt|"; //Userlist structure: "|user1|user2|user3|user4|..etc"
        for (int j = 0; j < Server.clients.size(); j++) {
            userlist += Server.clients.get(j) + "|";
        }
        System.out.println(userlist);
        synchronized (this) {
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null) {
                    threads[i].os.print(userlist);
                    threads[i].os.write(0x00);
                    threads[i].os.flush();

                }

            }
        }
    }

    private void disconnect() {
        synchronized (this) {
            for (int k = 0; k < maxClientsCount; k++) {
                if (threads[k] == this) {
                    threads[k] = null;
                    break;
                }
            }
        }
    }

    public void sendMessage(String message) {
        synchronized (this) {
            for (int i = 0; i < maxClientsCount; i++) { //loops through every client thread
                if (threads[i] != null && threads[i] != this) { //finds the other clients (except the current client)
                    threads[i].os.print(message);
                    threads[i].os.write(0x00);
                    threads[i].os.flush();
                }
            }
        }
    }

    public void sendMessageToAll(String message) {
        System.out.println(message);
        synchronized (this) {
            for (int i = 0; i < maxClientsCount; i++) { //loops through every client thread
                if (threads[i] != null) {//finds all valid clients
                    System.out.println(message + " inside");
                    threads[i].os.print(message);
                    threads[i].os.write(0x00);
                    threads[i].os.flush();
                }
            }
        }
    }

    public void writeToStream(String message) {
        os.print(message);
        os.write(0x00);
        os.flush();
    }

    public String getUserName() {
        return userName;
    }

}
