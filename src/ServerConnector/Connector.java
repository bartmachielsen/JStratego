package ServerConnector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Bart on 15-4-2016.
 */
public class Connector extends Thread {
    private Socket socket;
    private Data data;

    public Connector(Socket socket, Data data) throws Exception {
        this.socket = socket;
        this.data = data;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            data.setObjectOutputStream(objectOutputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            while(true){
                if(objectInputStream.available() > 0){
                    data.DataReceived(objectInputStream.readObject());
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
