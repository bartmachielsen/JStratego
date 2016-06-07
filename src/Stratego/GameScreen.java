package Stratego;

import javafx.geometry.Bounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 * Created by Bart on 3-6-2016.
 */
public class GameScreen extends JPanel implements MouseListener {
    public static Dimension SCREEN_SIZE = new Dimension(1280,720);
    public static int FRAME_RATE = 1000/60;

    private JFrame jFrame;
    private GameLogic gameLogic;
    /** GAMELOGIC **/

    public GameScreen(GameLogic gameLogic){
        this.gameLogic = gameLogic;
        initFrame();
    }
    private void initFrame(){
        jFrame = new JFrame(gameLogic.team.name());
        jFrame.setSize(SCREEN_SIZE);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.setContentPane(this);
        jFrame.addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.scale(getWidth()/SCREEN_SIZE.getWidth(),getHeight()/SCREEN_SIZE.getHeight());
        /**if(gameLogic.getTeam() == Piece.Team.OPPONENT){      //  TODO ROTATE THE SCREEN TO GIVE BETTER EXPERIENCE
            graphics2D.translate((SCREEN_SIZE.getWidth()/2.0),(SCREEN_SIZE.getHeight()/2.0));
            graphics2D.rotate(Math.toRadians(180.0));
            graphics2D.translate(-(SCREEN_SIZE.getWidth()/2.0),-(SCREEN_SIZE.getHeight()/2.0));
        }**/
        gameLogic.getLevel().drawRaster(graphics2D);

        for(Piece piece : gameLogic.getStrategoData().getPieces()){
            piece.draw(graphics2D,gameLogic.getTeam());
        }

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        for(Location location : gameLogic.getLevel().getLocations()) {
            int x = mouseEvent.getX();
            int y = mouseEvent.getY();
            /**if(gameLogic.getTeam() == Piece.Team.OPPONENT){
                x = (int)(SCREEN_SIZE.getWidth()-((SCREEN_SIZE.getWidth()-x)));
                y = (int)(SCREEN_SIZE.getHeight()-((SCREEN_SIZE.getHeight()-y)));
            }**/
            int difX = x-location.getLocation().x;
            int difY = y-location.getLocation().y;

            if(difX >= 0 && difX <= location.getSize().width && difY >= 0 && difY <= (location.getSize().height)){
                gameLogic.clicked(location,getPieceSelecterPosition());
                return;
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    public Point getPieceSelecterPosition(){
        return new Point(jFrame.getX()+jFrame.getWidth(),jFrame.getY());
    }
}

