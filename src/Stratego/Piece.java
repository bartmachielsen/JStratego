package Stratego;

import java.awt.*;
import java.awt.geom.Ellipse2D;
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
    private boolean attackPrio = false;
    public enum Team{
        OWN,OPPONENT;
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

    public boolean weakness(Piece piece){
        if(defeaters != null){
            for(Piece piece1 : defeaters){
                if(piece.copy(Team.OPPONENT).equals(piece1.copy(Team.OPPONENT))){
                    return true;
                }
            }
        }
        return false;
    }
    public void draw(Graphics2D graphics2D){
        if(location == null) return;



        Rectangle2D rectangle = new Rectangle2D.Double(location.getLocation().getX(),
                                                   location.getLocation().getY(),
                                                   location.getSize().getWidth(),location.getSize().getHeight());


        if(team == Team.OWN) {
            String chosen = power + "";
            double xoffset = 1.6;
            if(ident != null){
                chosen = ident;
                xoffset = 4.0;
            }
            graphics2D.setColor(Color.black);
            graphics2D.fill(rectangle);
            graphics2D.setColor(Color.white);
            graphics2D.setFont(new Font("Arial", Font.BOLD, 30));
            graphics2D.drawString(chosen, (int) (location.getLocation().x + (location.getSize().width/xoffset) - (graphics2D.getFont().getSize() / 2)),
                                              (int) (location.getLocation().y + (location.getSize().height / 2.0) + (graphics2D.getFont().getSize() / 4)));

        }else{
            if(location.isHighlight()){
                graphics2D.setColor(Color.red);
            }else {
                graphics2D.setColor(Color.gray);
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
        return piece;
    }

    @Override
    public int compareTo(Object o) {
        Piece piece = (Piece)o;
        if(team == Team.OPPONENT && piece.getTeam() == Team.OWN){
            return 1;
        }
        if(team == Team.OWN && piece.getTeam() == Team.OPPONENT){
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
        if(piece.getPower() == power && piece.getTeam() == team && piece.getSpeed() == speed){
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
}

