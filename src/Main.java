import ServerConnector.ConnectionSettings;
import ServerConnector.Node;
import Stratego.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Bart on 3-6-2016.
 */
public class Main implements ActionListener {
    private Timer timer;
    private GameScreen gameScreen;
    private StrategoData strategoData;
    private GameLogic gameLogic;


    public Main(ConnectionSettings connectionSettings){


        Level level = new Level();
        level.generate(18,10);


        // HARDCODED UNWALKABLE PLACES
        level.dumpBlock(7,2);
        level.dumpBlock(8,2);
        level.dumpBlock(9,2);
        level.dumpBlock(10,2);
        level.dumpBlock(7,3);
        level.dumpBlock(8,3);
        level.dumpBlock(9,3);
        level.dumpBlock(10,3);

        level.dumpBlock(7,6);
        level.dumpBlock(8,6);
        level.dumpBlock(9,6);
        level.dumpBlock(10,6);
        level.dumpBlock(7,7);
        level.dumpBlock(8,7);
        level.dumpBlock(9,7);
        level.dumpBlock(10,7);


        level.loadImage(new File("src/Resources/Background/background1.png"));
        strategoData = new StrategoData(level);
        Node node =  connectionSettings.getConnection(strategoData);


        if(connectionSettings.getType() == ConnectionSettings.Type.SERVER) {
            PieceLoader pieceLoader = PieceLoader.load(new File("Pieces.save"));
            if (pieceLoader == null) {
                pieceLoader = new PieceLoader();
            }
            pieceLoader.save(new File("Pieces.save"));


            strategoData.loadPieces(pieceLoader);
            strategoData.sendData(pieceLoader);
        }



        gameLogic = new GameLogic(strategoData,level);

        if(connectionSettings.getType() == ConnectionSettings.Type.SERVER){
            gameLogic.setTeam(Piece.Team.SERVER);
        }else{
            gameLogic.setTeam(Piece.Team.CLIENT);
        }







        gameScreen = new GameScreen(gameLogic);


        timer = new Timer(GameScreen.FRAME_RATE,this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        gameLogic.update();
        gameScreen.repaint();
        strategoData.quePol();
    }

}

