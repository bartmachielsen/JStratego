package Stratego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Created by Bart on 3-6-2016.
 */
public class GameScreen extends JPanel implements MouseListener {
    public static Dimension SCREEN_SIZE = new Dimension(1280,720);
    public static int FRAME_RATE = 1000/60;

    private JFrame jFrame;
    private GameLogic gameLogic;
    private MessageScreen messageScreen;
    /** GAMELOGIC **/

    public GameScreen(GameLogic gameLogic){
        this.gameLogic = gameLogic;
        initFrame();
        messageScreen = new MessageScreen(gameLogic.getMessages()) {
            @Override
            public void MessageEntered(Message message) {
                if(message.getTeam() == null){
                    message.setTeam(gameLogic.getTeam());
                }
                gameLogic.addMessage(message);
            }
        };
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
        /**if(gameLogic.getTeam() == Piece.Team.CLIENT){      //  TODO ROTATE THE SCREEN TO GIVE BETTER EXPERIENCE
            graphics2D.translate((SCREEN_SIZE.getWidth()/2.0),(SCREEN_SIZE.getHeight()/2.0));
            graphics2D.rotate(Math.toRadians(180.0));
            graphics2D.translate(-(SCREEN_SIZE.getWidth()/2.0),-(SCREEN_SIZE.getHeight()/2.0));
        }**/

        BufferedImage bufferedImage = new BufferedImage((int)((getWidth()/4.0)*3.0),getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D image = (Graphics2D) bufferedImage.getGraphics();
        image.scale((getWidth()-(getWidth()/4))/SCREEN_SIZE.getWidth(),getHeight()/SCREEN_SIZE.getHeight());
        gameLogic.getLevel().drawRaster(image);
        for(Piece piece : gameLogic.getStrategoData().getPieces()){
            piece.draw(image,gameLogic.getTeam());
        }
        graphics2D.drawImage(bufferedImage,0,0,null);

        graphics2D.translate((getWidth()/4.0)*3.0,0);
        graphics2D.setColor(Color.black);
        messageScreen.turnEffect(gameLogic.getTurn());
        messageScreen.draw(graphics2D,getWidth()/4,getHeight());

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getX() > (getWidth()/4*3.0)){
            messageScreen.clicked(mouseEvent.getX(),mouseEvent.getY());
        }else{

            for (Location location : gameLogic.getLevel().getLocations()) {
                int x = (int) ((mouseEvent.getX() / ((SCREEN_SIZE.getWidth() / 4.0) * 3.0)) * (SCREEN_SIZE.getWidth()));
                int y = mouseEvent.getY();  //  SOMETHING WRONG WITH THE HEIGHT ??
                /**if(gameLogic.getTeam() == Piece.Team.CLIENT){
                 x = (int)(SCREEN_SIZE.getWidth()-((SCREEN_SIZE.getWidth()-x)));
                 y = (int)(SCREEN_SIZE.getHeight()-((SCREEN_SIZE.getHeight()-y)));
                 }**/
                int difX = x - location.getLocation().x;
                int difY = y - location.getLocation().y;

                if (difX >= 0 && difX <= location.getSize().width && difY >= 0 && difY <= (location.getSize().height)) {
                    gameLogic.clicked(location, getPieceSelecterPosition());
                    return;
                }
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

