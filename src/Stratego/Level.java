package Stratego;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import Event.StratEvent;

/**
 * Created by Bart on 3-6-2016.
 */
public class Level extends StratEvent {
    private ArrayList<Location> locations = new ArrayList<>();
    private Color rasterColor = Color.lightGray;
    private Piece highlighted = null;
    private int rows,colloms;
    private transient Image backgroundImage;
    private File filePath = new File("src/Resources/Background/background1.png");
    public Level(){
        turnChanged = false;

    }

    public void loadImage(File file){
        try{
            this.backgroundImage = ImageIO.read(file);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void dumpBlock(int x, int y){
        positiontoLocation(x,y).setDisabled(true);
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
                Location location = new Location(new Point(i,ii),new Point(i*calWidth,ii*calHeight),new Dimension(calWidth,calHeight));
                locations.add(location);
            }
        }
    }

    private BufferedImage getScaled(Image image){
        BufferedImage bufferedImage = new BufferedImage((int)GameScreen.SCREEN_SIZE.getWidth(),(int)GameScreen.SCREEN_SIZE.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.scale(bufferedImage.getWidth()/(double)image.getWidth(null),bufferedImage.getHeight()/(double)image.getHeight(null));
        graphics2D.drawImage(image,0,0,null);
        return bufferedImage;

    }



        /// todo draw raster by tiled
    public void drawRaster(Graphics2D graphics2D){
        if(backgroundImage != null || filePath != null){
            if(backgroundImage == null){
                loadImage(filePath);
            }
            graphics2D.drawImage(getScaled(backgroundImage),0,0,null);
        }
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
            if(location.isDisabled()){
                graphics2D.drawLine(location.getLocation().x,
                                    location.getLocation().y,
                                    location.getLocation().x + location.getSize().width,
                                    location.getLocation().y + location.getSize().height);
                graphics2D.drawLine(location.getLocation().x + location.getSize().width,
                                    location.getLocation().y,
                                    location.getLocation().x,
                                    location.getLocation().y + location.getSize().height);
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

    public void highLight(Piece piece, ArrayList<Piece> pieces){
        highlighted = piece;
        for(Location location : locations){
            location.setHighlight(false);
        }
        if(piece != null) {
            for (Location location : possibleWays(piece,pieces)) {
                if (!location.isDisabled()) {
                    location.setHighlight(true);
                }
            }
        }
    }

    private ArrayList<Location> possibleWays(Piece piece,ArrayList<Piece> pieces){
        ArrayList<Location> locations = new ArrayList<>();
        if(piece.getLocation() == null) return locations;

        int index = this.locations.indexOf(piece.getLocation());

        int row = indextoPosition(index).x;
        int column = indextoPosition(index).y;


        locations.addAll(loop(piece.getSpeed(),new Point(row,column),new Point(1,0),pieces));
        locations.addAll(loop(piece.getSpeed(),new Point(row,column),new Point(-1,0),pieces));
        locations.addAll(loop(piece.getSpeed(),new Point(row,column),new Point(0,1),pieces));
        locations.addAll(loop(piece.getSpeed(),new Point(row,column),new Point(0,-1),pieces));

        /**locations.addAll(loop(piece.getSpeed(),new Point(row,column),new Point(-1,-1),pieces));
        locations.addAll(loop(piece.getSpeed(),new Point(row,column),new Point(1,1),pieces));       ALL DIRECTIONS!
        locations.addAll(loop(piece.getSpeed(),new Point(row,column),new Point(-1,1),pieces));
        locations.addAll(loop(piece.getSpeed(),new Point(row,column),new Point(1,-1),pieces));**/
        return locations;
    }
    private ArrayList<Location> loop(int amount,Point point, Point increment, ArrayList<Piece> pieces){
        ArrayList<Location> locations = new ArrayList<>();
        amount--;
        point.x += increment.x;
        point.y += increment.y;

        if(amount < 0 || positiontoLocation(point.x,point.y) == null){
            return locations;
        }else{
            Location location = positiontoLocation(point.x,point.y);

            if(location.isDisabled() || contains(location,pieces)){
                return locations;
            }
            locations.add(location);
            locations.addAll(loop(amount,point,increment,pieces));
            return locations;
        }
    }
    private boolean contains(Location location, ArrayList<Piece> pieces){
        for(Piece piece : pieces){
            if(piece.getLocation() != null) {
                if (piece.getLocation().equals(location)) {
                    return true;
                }
            }
        }
        return false;
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

