package Client;

import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            new WelcomePage().setVisible(true);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
