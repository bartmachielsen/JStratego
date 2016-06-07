import ServerConnector.ConnectionSettings;

/**
 * Created by Bart on 7-6-2016.
 */
public class Client {
    public static void main(String[] args) {
        new Main(new ConnectionSettings("localhost",5012));
    }
}

