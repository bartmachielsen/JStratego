package Stratego;

import Event.StratEvent;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Bart on 4-6-2016.
 */
public class DualResult extends StratEvent {
    private ArrayList<Piece> losers = new ArrayList<>();
    private Piece piece;
    private Piece opponent;
    private Move move;
    public DualResult(Piece piece, Piece opponent){
        this.piece = piece;
        this.opponent = opponent;
        generateResult();
        turnChanged  = true;
    }
    public void generateResult(){
        move = new Move(opponent,opponent.getLocation(),piece.getLocation());
        if(piece.weakness(opponent)){
            losers.add(piece);
            return;
        }
        if(opponent.weakness(piece)){
            losers.add(opponent);
            move = null;
            return;
        }
        if(piece.getPower() < opponent.getPower()){
            losers.add(piece);
        }else if(piece.getPower() > opponent.getPower()){
                losers.add(opponent);
            }else{
            losers.add(piece);
            losers.add(opponent);
            move = null;
        }
    }

    public ArrayList<Piece> getLosers() {
        return losers;
    }
    public String toString(){
        return "Dual: " + piece.toString() + " vs " + opponent.toString() + " | loser(s)" + losers;
    }
    public void visualise(){
        new DualReport(this);
    }


    public Piece getPiece() {
        return piece;
    }

    public Piece getOpponent() {
        return opponent;
    }


    public Move getMove() {
        return move;
    }


}

