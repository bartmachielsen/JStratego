package Stratego;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by Bart on 3-6-2016.
 */
public class Location implements Serializable{
    private Point location;
    private Dimension size;
    private boolean disabled = false;
    private boolean highlight = false;
    private Point virtual;

    public Location(Point virtual, Point location, Dimension size){
        this.location = location;
        this.size = size;
        this.virtual = virtual;
    }

    public Point getLocation() {
        return location;
    }

    public Dimension getSize() {
        return size;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    //public String toString(){
   //     return location.getX() + "|" + location.getY();
    //}

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    @Override
    public boolean equals(Object o) {
        Location location = (Location)o;
        return location.getLocation().x == this.getLocation().x && location.getLocation().y == this.getLocation().y;
    }

    @Override
    public String toString() {
        return virtual.x + "|" + virtual.y;
    }
}

