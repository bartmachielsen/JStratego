import ServerConnector.ConnectionSettings;
import Stratego.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Bart on 3-6-2016.
 */
public class Main implements ActionListener {
    private ConnectionSettings connectionSettings = new ConnectionSettings(5012);
    private Timer timer;
    private GameScreen gameScreen;
    private GameLogic gameLogic;

    public static void main(String[] args) {
        new Main();
    }
    public Main(){

        //PieceLoader pieceLoader = new PieceLoader();
        //pieceLoader.save(new File("Pieces.save"));
        PieceLoader pieceLoader = PieceLoader.load(new File("Pieces.save"));
        if(pieceLoader == null){
            pieceLoader = new PieceLoader();
        }
        pieceLoader.save(new File("Pieces.save"));

        StrategoData strategoData = new StrategoData(pieceLoader);


        if(connectionSettings.getType() == ConnectionSettings.Type.SERVER){
            GameLogic.team = Piece.Team.OWN;
        }else{
            GameLogic.team = Piece.Team.OPPONENT;
        }


        //connectionSettings.getConnection(strategoData);     //  TODO USE OF CONNECTIONSETTINGS NECCESAIRY ???

        Level level = new Level();
        level.generate(18,10);

        gameLogic = new GameLogic(strategoData,level);

        gameScreen = new GameScreen(gameLogic);


        timer = new Timer(GameScreen.FRAME_RATE,this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        gameLogic.update();
        gameScreen.repaint();
    }

}

