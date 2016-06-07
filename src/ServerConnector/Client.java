package ServerConnector;

import java.awt.event.ActionListener;
import java.net.Socket;

/**
 * Created by Bart Machielsen on 17-4-2016.
 */
public class Client extends Node {

    /**
     * Instantiates a new Client.
     *
     * @param data the data
     * @param host the host
     * @param port the port
     */
    public Client(Data data, String host, int port) {
        try {
            connector = new Connector(new Socket(host, port), data);
            connector.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Instantiates a new Client.
     *
     * @param data   the data
     * @param socket the socket
     */
    public Client(Data data, Socket socket){
        try {
            connector = new Connector(socket, data);
            connector.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
