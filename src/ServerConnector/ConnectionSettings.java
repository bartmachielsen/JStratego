package ServerConnector;

import java.net.Inet4Address;

/**
 * Created by Bart on 3-6-2016.
 */
public class ConnectionSettings {
    /**
     * The enum Type.
     */
    public enum Type{
        /**
         * Server type.
         */
        SERVER, /**
         * Client type.
         */
        CLIENT;
    }
    private Type type;
    private String host;
    private int port;


    /**
     * Instantiates a new Connection settings.
     *
     * @param host the host
     * @param port the port
     */
    public ConnectionSettings(String host, int port){
        this.type = Type.CLIENT;
        this.host = host;
        this.port = port;
    }


    /**
     * Instantiates a new Connection settings.
     *
     * @param port the port
     */
    public ConnectionSettings(int port){
        this.type = Type.SERVER;
        this.port = port;
        try {
            this.host = Inet4Address.getLocalHost().getHostAddress();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Get connection node.
     *
     * @param data the data
     * @return the node
     */
    public Node getConnection(Data data){
        if(type == Type.SERVER)     return new Server(data);
        if(type == Type.CLIENT)     return new Client(data,host,port);
        return null;
    }
}

