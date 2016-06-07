package ServerConnector;

import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Bart on 14-4-2016.
 */
public abstract class Data {
    /**
     * The Object output stream.
     */
    protected ObjectOutputStream objectOutputStream;

    /**
     * Data received.
     *
     * @param object the object
     */
    public abstract void DataReceived(Object object);

    /**
     * Send data.
     *
     * @param object the object
     */
    public void sendData(Object object){
        try {
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Sets object output stream.
     *
     * @param objectOutputStream the object output stream
     */
    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }
}

