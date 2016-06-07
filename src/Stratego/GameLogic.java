package Stratego;

import ServerConnector.Data;

import java.awt.*;

/**
 * Created by Bart on 3-6-2016.
 */
public class GameLogic {
    /** GAMELOGIC **/
    private StrategoData strategoData;
    private Level level;
    private PieceSelecter pieceSelecter = null;
    public Piece.Team team = Piece.Team.OWN;
    public GameLogic(StrategoData strategoData, Level level) {
        this.strategoData = strategoData;
        this.level = level;
    }

    public Piece.Team getTeam() {
        return team;
    }

    public void setTeam(Piece.Team team) {
        this.team = team;
    }

    public StrategoData getStrategoData() {
        return strategoData;
    }

    public Level getLevel() {
        return level;
    }

    public void clicked(Location location, Point point){
        Piece occupant = null;
        for(Piece piece : strategoData.getPieces()){
            if(piece.getLocation() == location){
                occupant = piece;
            }
            System.out.println(piece.getLocation());
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
        }else if(occupant != null){
            return;
        }

        if(strategoData.getAvailable(team).size() > 0 && occupant == null){     //  FOR TESTING PURPOSES --> CHANGE TO OWN
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

        //System.out.println(strategoData.stats());


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

}

