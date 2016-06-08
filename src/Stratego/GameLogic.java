package Stratego;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Bart on 3-6-2016.
 */
public class GameLogic implements TurnListener {

    public static boolean FIRST_PLACE_ALL = true;


    /**
     * GAMELOGIC
     **/
    private StrategoData strategoData;
    private Level level;
    private PieceSelecter pieceSelecter = null;
    private int turns = 0;
    private boolean allPlaced = false;
    private boolean GAME_ENDED = false;
    public Piece.Team team = Piece.Team.SERVER;

    public GameLogic(StrategoData strategoData) {
        this.strategoData = strategoData;
        strategoData.addLocalMessage(new Message("Welcome! - JStratego : Bart Machielsen", Piece.Team.SYSTEM));
        this.level = strategoData.getLevel();
        strategoData.setTurnListener(this);
        if(FIRST_PLACE_ALL){
            strategoData.addLocalMessage(new Message("First place all the pieces to start playing!!", Piece.Team.SYSTEM));
        }

    }

    public Piece.Team getTeam() {
        return team;
    }

    public void setTeam(Piece.Team team) {
        if (team == Piece.Team.CLIENT) {
            turns--;
        }
        this.team = team;
    }

    public StrategoData getStrategoData() {
        return strategoData;
    }

    public Level getLevel() {
        return level;
    }

    public void clicked(Location location, Point point) {
        if (FIRST_PLACE_ALL) {
            if (strategoData.getAvailable(team).size() > 0 && !location.isDisabled()) {    //  for final release! first put all pieces!
                piecePlacer(location, point);
                return;
            }
        }

        if(!getTurn()) return;

        Piece occupant = null;
        for(Piece piece : strategoData.getPieces()){
            if(piece.getLocation() == location){
                occupant = piece;
            }
        }
        if(location.isHighlight()) {
            Piece piece = level.getHighlighted();
            if (piece != null) {
                if (occupant == null) {
                    strategoData.addMove(new Move(piece,piece.getLocation(),location));
                } else {
                    DualResult dualResult = piece.dual(occupant);
                    dualResult.visualise();
                    strategoData.addDualResult(dualResult);
                }
                level.highLight(null);
                return;
            }
        }


        if(occupant != null && occupant.getTeam() == team){
            level.highLight(occupant);
            return;
        }else if(occupant == null){
            piecePlacer(location,point);
            return;
        }


    }
    public void piecePlacer(Location location, Point point){
        if(strategoData.getAvailable(team).size() > 0){
            for(Piece piece : strategoData.getPieces()){
                if(piece.getLocation() == location){
                    return;
                }
            }
            if(pieceSelecter != null){
                pieceSelecter.dispose();
            }
            pieceSelecter = new PieceSelecter(strategoData.getAvailable(team), new PieceListener() {
                @Override
                public void select(Piece piece) {
                    Move move = new Move(piece,piece.getLocation(),location);
                    move.setPlacing(true);
                    strategoData.addMove(move);
                }
            },point);
        }
    }
    public void update(){
        if(strategoData.getAvailable(team).size() == 0 && !allPlaced){
            if(FIRST_PLACE_ALL){
                strategoData.addMessage(new Message("Ready to Play! all pieces are placed!",team));
                strategoData.addLocalMessage(new Message("Ready to Play! all pieces are placed!", Piece.Team.SYSTEM));
            }
            allPlaced = true;
        }
        for(DualResult dualResult : strategoData.getDualResults()){
            if(dualResult.gameEnded()){
                GAME_ENDED = true;
                winnerScreen(dualResult.getOpponent().getTeam());
            }
        }
        for(Location location : level.getLocations()){
            for(Piece piece : strategoData.getPieces()){
                if(piece.getLocation() == location && location.isHighlight()){
                    if(level.getHighlighted().getTeam() == piece.getTeam()){
                        location.setHighlight(false);
                    }
                }
            }
        }
    }

    @Override
    public void TurnChanged() {
        turns++;
    }
    public ArrayList<Message> getMessages(){
        return strategoData.getMessages();
    }
    public void addMessage(Message message){
        strategoData.addMessage(message);
        strategoData.addLocalMessage(message);
    }
    public boolean getTurn(){
        return (turns % 2 == 0) && (allPlaced||!FIRST_PLACE_ALL);
    }

    public void winnerScreen(Piece.Team team){
        System.out.println(team.name() + " WON!");
        System.exit(0);     //  TODO FANCINESS UPDATE
    }
}

