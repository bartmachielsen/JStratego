package Stratego;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bart on 3-6-2016.
 */
public class Piece implements Comparable,Serializable{
    private int speed;
    private int power;
    private String ident = null;
    private Team team;
    private int duplicateID = 0;
    private boolean attackPrio = false;
    private boolean winPiece = false;
    public enum Team{
        SERVER, CLIENT,SYSTEM;
    }


    private Location location;

    //  TODO SPECIAL POWERS
    private ArrayList<Piece> defeaters = null;      //  only initialize if there are limited defeaters
    public Piece(int speed,int power, Team team){
        this.power = power;
        this.speed = speed;
        this.team = team;
    }
    public Piece(int speed, int power){
        this.power = power;
        this.speed = speed;
    }
    public Piece(int speed, int power, String ident){
        this.power = power;
        this.speed = speed;
        this.ident = ident;
    }
    public DualResult dual(Piece piece){
        return new DualResult(piece,this);
    }

    public boolean isWinPiece() {
        return winPiece;
    }

    public void setWinPiece(boolean winPiece) {
        this.winPiece = winPiece;
    }

    public boolean weakness(Piece piece){
        if(defeaters != null){
            for(Piece piece1 : defeaters){
                if(piece.copy(Team.CLIENT).equals(piece1.copy(Team.CLIENT))){
                    return true;
                }
            }
        }
        return false;
    }
    public void draw(Graphics2D graphics2D, Team team){
        if(location == null) return;



        Rectangle2D rectangle = new Rectangle2D.Double(location.getLocation().getX(),
                                                   location.getLocation().getY(),
                                                   location.getSize().getWidth(),location.getSize().getHeight());


        if(this.team == team) {
            String chosen = power + "";
            double xoffset = 1.6;
            if(ident != null){
                chosen = ident;
                xoffset = 4.0;
            }
            if(!winPiece){
                graphics2D.setColor(Color.black);
            }else{
                graphics2D.setColor(Color.green);
            }
            graphics2D.fill(rectangle);
            graphics2D.setColor(Color.white);

            int fontSize = 30;
            for(int i = 4; i < chosen.length(); i++){
               fontSize -=5;

            }
            if(fontSize < 5){
                fontSize = 5;
            }
            graphics2D.setFont(new Font("Arial", Font.BOLD, fontSize));
            graphics2D.drawString(chosen, (int) (location.getLocation().x + (location.getSize().width/xoffset) - (graphics2D.getFont().getSize() / 2)),
                                              (int) (location.getLocation().y + (location.getSize().height / 2.0) + (graphics2D.getFont().getSize() / 4)));

        }else{
            if(location.isHighlight()){
                graphics2D.setColor(Color.red);
            }else {
                graphics2D.setColor(new Color(112,0,0,100));
            }
            graphics2D.fill(rectangle);
        }

    }

    public int getPower() {
        return power;
    }


    public void setLocation(Location location) {
        this.location = location;
    }


    public void setDefeaters(ArrayList<Piece> defeaters) {
        this.defeaters = defeaters;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public Location getLocation() {
        return location;
    }


    public int getSpeed() {
        return speed;
    }



    public Team getTeam() {
        return team;
    }
    public String getIdentw(){
        if(ident == null){
            return ""+power;
        }else{
            return ident;
        }
    }
    public String getIdent(){
        if(ident == null){
            return team.name()+": "+power;
        }else{
            return team.name()+": "+ident;
        }
    }

    public Piece copy(Team team){
        Piece piece = new Piece(speed,power,team);
        piece.setLocation(location);
        piece.setIdent(ident);
        piece.setDefeaters(defeaters);
        piece.setAttackPrio(attackPrio);
        piece.setWinPiece(winPiece);
        return piece;
    }

    @Override
    public int compareTo(Object o) {
        Piece piece = (Piece)o;
        if(team == Team.CLIENT && piece.getTeam() == Team.SERVER){
            return 1;
        }
        if(team == Team.SERVER && piece.getTeam() == Team.CLIENT){
            return -1;
        }
        if(piece.getPower() > power){
            return 1;
        }else{
            if (piece.getPower() == power) {
                return 0;
            }else{
                return -1;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        Piece piece = (Piece)o;
        if(piece.getPower() == power &&
                piece.getTeam() == team &&
                piece.getSpeed() == speed &&
                piece.getDuplicateID() == duplicateID){
            return true;
        }else{
            return false;
        }
    }
    public void addWeakness(Piece piece){
        if(defeaters == null){
            defeaters = new ArrayList<>();
        }
        defeaters.add(piece);

    }


    @Override
    public String toString() {
        return "Piece{" +
                "speed=" + speed +
                ", power=" + power +
                ", ident='" + ident + '\'' +
                ", team=" + team +
                ", location=" + location +
                ", defeaters=" + defeaters +
                '}';
    }


    public boolean isAttackPrio() {
        return attackPrio;
    }

    public void setAttackPrio(boolean attackPrio) {
        this.attackPrio = attackPrio;
    }

    public int getDuplicateID() {
        return duplicateID;
    }

    public void setDuplicateID(int duplicateID) {
        this.duplicateID = duplicateID;
    }
}

