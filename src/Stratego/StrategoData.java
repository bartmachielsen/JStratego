package Stratego;

import Event.StratEvent;
import ServerConnector.Data;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by Bart on 3-6-2016.
 */
public class StrategoData extends Data {
    private ArrayList<Piece> pieces = new ArrayList<>();
    private ArrayList<DualResult> dualResults = new ArrayList<>();
    private ArrayList<Move> moves = new ArrayList<>();
    private LinkedList<StratEvent> updateQueue = new LinkedList<>();
    private TurnListener turnListener;
    private Piece.Team team = Piece.Team.OWN;
    private Level level;

    public StrategoData(Level level){
        this.level = level;
    }

    public void loadPieces(PieceLoader pieceLoader){
        for(Piece piece : pieceLoader.getPieces().keySet()){
            for(int i = 0; i < pieceLoader.getPieces().get(piece); i++){
                pieces.add(piece.copy(Piece.Team.OWN));
            }
        }
        for(Piece piece : pieceLoader.getPieces().keySet()){
            for(int i = 0; i < pieceLoader.getPieces().get(piece); i++){
                pieces.add(piece.copy(Piece.Team.OPPONENT));
            }
        }

    }

    public void setRandomPosition(ArrayList<Location> positions) {
        for(Piece piece : pieces) {
            boolean good = false;
            Location position = null;
            while(!good){
                position = positions.get((int) (Math.random() * positions.size()));
                boolean found = false;
                for(Piece piece1 : pieces){
                    if(piece1.getLocation() == position){
                        found = true;
                    }
                }
                if(!found){
                    good = true;
                }
            }
            piece.setLocation(position);

        }
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }
    public ArrayList<Piece> getAvailable(Piece.Team team){
        ArrayList<Piece> pieces = new ArrayList<>();
        for(Piece piece : this.pieces){
            if(piece.getLocation() == null && (piece.getTeam() == team || team == null)){
                pieces.add(piece);
            }
        }
        return pieces;
    }

    public void addDualResult(DualResult dualResult){
        updateQueue.add(dualResult);
        addLocalDual(dualResult);
        if(dualResult.getMove() != null){
            addMove(dualResult.getMove());
        }
    }
    public void addMove(Move move){
        updateQueue.add(move);
        addLocalMove(move);
    }
    private void addLocalMove(Move move){
        Piece piece = matching(move.getPiece());
        if(piece != null){
            Location location = matching(move.getLocation());
            if(location != null){
                piece.setLocation(location);
            }
        }
        moves.add(move);
    }
    private void addLocalDual(DualResult dualResult){
        dualResults.add(dualResult);
        for(Piece piece : dualResult.getLosers()){
            Piece match = matching(piece);
            if(match != null){
                pieces.remove(match);
            }
        }
        if(dualResult.getMove() != null){
            if(!moves.contains(dualResult.getMove())){
                addLocalMove(dualResult.getMove());
            }
        }
    }
    @Override
    public void DataReceived(Object object) {
        System.out.println(object);
        if(object instanceof StratEvent){
            StratEvent stratEvent = (StratEvent)object;
            if(stratEvent.isTurnChanged() && turnListener != null){
                if(team == Piece.Team.OPPONENT){
                    team = Piece.Team.OWN;
                }else{
                    team = Piece.Team.OPPONENT;
                }
                turnListener.TurnChanged(team);
            }
            if(object instanceof DualResult){
                addLocalDual((DualResult)object);
            }
            if(object instanceof  Move){
                addLocalMove((Move)object);
            }

            if(object instanceof PieceLoader){
                loadPieces((PieceLoader)object);
            }

        }
    }

    public void quePol(){
        StratEvent stratEvent = updateQueue.poll();
        if(stratEvent != null){
            sendData(stratEvent);
        }
    }
    public String stats(){
        return "Stats: " + dualResults.size() + " duals | " + moves.size() + " moves";
    }

    @Nullable
    private Piece matching(Piece piece){
        for(Piece piece1 : pieces){
            if(piece1.equals(piece)){
                return piece1;
            }
        }
        return null;
    }
    private Location matching(Location location){
        for(Location location1 : level.getLocations()){
            if(location.equals(location1)){
                return location1;
            }
        }
        return null;
    }

    public void setTurnListener(TurnListener turnListener) {
        this.turnListener = turnListener;
    }
}

