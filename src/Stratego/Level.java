package Stratego;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by Bart on 3-6-2016.
 */
public class Level {
    private ArrayList<Location> locations = new ArrayList<>();
    private Color rasterColor = Color.lightGray;
    private Piece highlighted = null;
    private int rows,colloms;
    public Level(){

    }

    public void dumpBlocks(int amount){
        for(int i = 0; i < amount; i++){
            int number = (int)(Math.random()*locations.size());
            while(locations.get(number).isDisabled()){
                number = (int)(Math.random()*locations.size());
            }
            locations.get(number).setDisabled(true);

        }
    }
    public void generate(int width, int height){
        locations = new ArrayList<>();

        int calWidth = GameScreen.SCREEN_SIZE.width/width;
        int calHeight = GameScreen.SCREEN_SIZE.height/height;
        this.colloms = height;
        this.rows = width;
        for(int i = 0; i < width; i++){
            for(int ii = 0; ii < height; ii++){
                Location location = new Location(new Point(i*calWidth,ii*calHeight),new Dimension(calWidth,calHeight));
                locations.add(location);
            }
        }
    }
        /// todo draw raster by tiled
    public void drawRaster(Graphics2D graphics2D){
        for(int i = 0; i < locations.size(); i++){
            Location location = locations.get(i);
            Shape drawShape;
            if(location.isHighlight()) {
                drawShape = new Ellipse2D.Double(location.getLocation().getX() + location.getSize().getWidth()/4,
                                                 location.getLocation().getY() + location.getSize().getHeight()/4,
                                                 location.getSize().getWidth()/2, location.getSize().getHeight()/2);
                graphics2D.setColor(Color.darkGray);
                graphics2D.fill(drawShape);
            }
                drawShape = new Rectangle2D.Double(location.getLocation().getX(),
                                                   location.getLocation().getY(),
                                                   location.getSize().getWidth(), location.getSize().getHeight());
                graphics2D.setColor(Color.black);
                graphics2D.draw(drawShape);




        }
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void highLight(Piece piece){
        highlighted = piece;
        for(Location location : locations){
            location.setHighlight(false);
        }
        if(piece != null) {
            for (Location location : possibleWays(piece)) {
                location.setHighlight(true);
                System.out.println(location);
            }
        }
    }

    private ArrayList<Location> possibleWays(Piece piece){
        ArrayList<Location> locations = new ArrayList<>();
        if(piece.getLocation() == null) return locations;

        int index = this.locations.indexOf(piece.getLocation());

        int row = indextoPosition(index).x;
        int column = indextoPosition(index).y;


        locations.addAll(loop(piece.getSpeed(),row,column,1,0));
        locations.addAll(loop(piece.getSpeed(),row,column,-1,0));
        locations.addAll(loop(piece.getSpeed(),row,column,0,1));
        locations.addAll(loop(piece.getSpeed(),row,column,0,-1));
        return locations;
    }
    private ArrayList<Location> loop(int amount, int x, int y, int positionX, int positionY){
        ArrayList<Location> locations = new ArrayList<>();
        amount--;
        x += positionX;
        y += positionY;

        if(amount < 0 || positiontoLocation(x,y) == null){
            return locations;
        }else{
            locations.add(positiontoLocation(x,y));
            locations.addAll(loop(amount,x,y,positionX,positionY));
            return locations;
        }
    }


    private Location positiontoLocation(int x, int y){
        int index = (x*colloms)+y;
        if(index >= locations.size() || index < 0 || y < 0 || x < 0 || x >= rows || y >= colloms) {
            return null;
        }else{
            return locations.get((x*colloms)+y);
        }
    }
    private Point indextoPosition(int index){
        int row = index/colloms;
        int column = index-(row*colloms);
        return new Point(row,column);
    }


    public Piece getHighlighted() {
        return highlighted;
    }
}

