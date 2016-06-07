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
}

