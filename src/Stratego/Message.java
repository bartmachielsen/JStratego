package Stratego;

import Event.StratEvent;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Bart on 8-6-2016.
 */
public class Message extends StratEvent {
    public static final int MAX_CHAR = 40;
    public static final Dimension BLOCK_DIMENSION = new Dimension(380,150);


    private String message;
    private Piece.Team team;
    public Message(String message, Piece.Team team){
        super();
        this.message = message;
        this.team = team;
        this.turnChanged = false;
    }

    public String getMessage() {
        return message;
    }

    public Piece.Team getTeam() {
        return team;
    }

    public void setTeam(Piece.Team team) {
        this.team = team;
    }

    public void draw(Graphics2D graphics2D, int x, int y, int width, int height, Color color){
        BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics2D image = (Graphics2D)bufferedImage.getGraphics();
        image.scale(width/BLOCK_DIMENSION.getWidth(),height/BLOCK_DIMENSION.getHeight());
        image.setColor(Color.black);
        image.draw(new Rectangle2D.Double(0,0,BLOCK_DIMENSION.getWidth(),BLOCK_DIMENSION.getHeight()));
        image.setColor(color);
        image.fill(new Rectangle2D.Double(0,0,BLOCK_DIMENSION.getWidth(),BLOCK_DIMENSION.getHeight()));


        image.setColor(Color.black);
        image.setFont(new Font("Arial",Font.BOLD,25));
        image.drawString(team.name(),10,30);

        image.setFont(new Font("Arial",Font.PLAIN,15));
        int yoffset = 50;
        ArrayList<String> lines = lines();
        int max_lines = (BLOCK_DIMENSION.height-yoffset)/15;
        for(int i = 0; i < lines.size(); i++ ) {
            if(i < max_lines) {
                image.drawString(lines.get(i), 10, yoffset);
                yoffset += 15;
            }else{
                image.drawString("....",10,yoffset);
                break;
            }
        }
        //System.out.println(toLines());

        graphics2D.drawImage(bufferedImage,x,y,null);
    }
    private String toLines(){
        String totalLine = "";
        ArrayList<String> lines = lines();
        for(int i = 0; i < lines.size(); i++){
            totalLine += lines.get(i) + " \n";
        }
        return totalLine;
    }
    private ArrayList<String> lines(){
        ArrayList<String> lines = new ArrayList<>();
        char[] keys = { '?' , ',' , '.', '/', '!', '-', '|', ' '};
        int offset = 0;
        int length = 0;
        String word = "";
        boolean newLine = false;
        for(int i = 0; i < message.toCharArray().length; i++){
            Character character = message.toCharArray()[i];
            offset /= MAX_CHAR;
            if(i % (MAX_CHAR+offset) == 0 && i > 0){
                newLine = true;
            }
            if(newLine || (message.toCharArray().length-1) == i){
                boolean contains = false;
                for(Character character1 : keys){
                    if(character == character1){
                        contains = true;
                        break;
                    }
                }
                if(((contains && (message.toCharArray().length-i) > MAX_CHAR/3.0) ||
                        (message.toCharArray().length-1) == i) || length > (MAX_CHAR*1.2)) {
                    if(character != ' '){
                        word += character;
                    }
                    lines.add(word);
                    length = 0;
                    word = "";
                    newLine = false;
                }else{
                    word += character;
                    offset++;
                    length++;
                }
            }else{
                word += character;
                length++;
            }
        }
        return lines;
    }

}

