package Stratego;

import Event.StratEvent;

import java.io.Serializable;

/**
 * Created by Bart on 3-6-2016.
 */
public class Move extends StratEvent implements Serializable {
    private Piece piece;
    private Location old;
    private Location location;
    private boolean placing;
    public Move(Piece piece,Location old, Location location){
        this.piece = piece;
        this.old = old;
        this.location = location;
        turnChanged = true;
    }

    public Piece getPiece() {
        return piece;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isPlacing() {
        return placing;
    }

    public void setPlacing(boolean placing) {
        this.placing = placing;
        this.turnChanged = !placing;
    }
    public void changeTurn(boolean turn){
        this.turnChanged = turn;
    }
    public Message getMessage(){
        return new Message("Moved a piece from "+ old + " to " + location + "!",piece.getTeam());
    }
    public Message getUMessage(){
        return new Message("Moved " + piece.getIdentw() + " from " + old + " to " + location + "!",piece.getTeam());
    }
    public Message getPMessage(){
        return new Message("Placed a piece on " + location + "!",piece.getTeam());
    }
    public Message getPUMessage(){
        return new Message("Placed " + piece.getIdentw() + " on " + location + "!",piece.getTeam());
    }
}

