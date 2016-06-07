package ServerConnector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;

/**
 * Created by Bart Machielsen on 17-4-2016.
 */
public class Server extends Node{
    public Server(Data data) {
        try {
            ServerSocket serverSocket = new ServerSocket(5012);
            while (true) {
                connector = new Connector(serverSocket.accept(), data);
                connector.start();
            }
            }catch(Exception e){
                e.printStackTrace();
            }


    }



}
