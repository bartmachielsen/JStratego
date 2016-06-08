package Stratego;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by Bart on 8-6-2016.
 */
public abstract class MessageScreen{
    private ArrayList<Message> messages = new ArrayList<>();
    private Color turnColor = new Color(255, 80, 86,100);
    public abstract void MessageEntered(Message message);
    public MessageScreen(ArrayList<Message> messages){
        this.messages = messages;
    }
    protected void draw(Graphics2D graphics2D, int width, int height) {
        for(int i = 0; i < 6 && i < messages.size(); i++){
            Message message = messages.get((messages.size()-1)-i);
            message.draw(graphics2D,0,(int)((height/6.0)*i),width,(int)((height/6.0)),turnColor);

        }
    }
    public void clicked(int x, int y){
        MessageEntered(new Message(JOptionPane.showInputDialog("Send message to opponent: "),null));
    }
    public void turnEffect(boolean turn){
        if(turn){
            turnColor = new Color(86, 80, 255,100);
        }else{
            turnColor = new Color(255, 80, 86,100);
        }
    }
}

