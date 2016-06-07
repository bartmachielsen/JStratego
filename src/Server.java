import ServerConnector.ConnectionSettings;

/**
 * Created by Bart on 7-6-2016.
 */
public class Server {
    public static void main(String[] args) {
        new Main(new ConnectionSettings(5012));
    }
}

