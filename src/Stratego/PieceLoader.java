package Stratego;

import Event.StratEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.corba.se.impl.orbutil.ObjectWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Bart on 3-6-2016.
 */
public class PieceLoader extends StratEvent implements Serializable {
    private TreeMap<Piece,Integer> pieces = new TreeMap<>();
    public PieceLoader(){
       loadTestPieces();
    }
    private void loadTestPieces(){
        pieces = new TreeMap<>();
        Piece king = new Piece(1,10, "King");
        Piece spy = new Piece(1,1,"Spy");
        king.addWeakness(spy);
        pieces.put(king,1);
        pieces.put(spy,1);
        pieces.put(new Piece(1,9,"General"),1);
        //pieces.put(new Piece(1,8),2);
        //pieces.put(new Piece(1,7),3);
        //pieces.put(new Piece(1,6),4);
        //pieces.put(new Piece(1,5),4);
        //pieces.put(new Piece(1,4),5);
        Piece miner = new Piece(5,3,"Miner");
        pieces.put(new Piece(2,2),1);
        Piece bomb = new Piece(0,999,"Bom");
        bomb.addWeakness(miner);
        pieces.put(bomb,1);
        pieces.put(miner,1);
    }

    public TreeMap<Piece, Integer> getPieces() {
        return pieces;
    }

    public void setPieces(TreeMap<Piece, Integer> pieces) {
        this.pieces = pieces;
    }

    public void save(File file){
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static PieceLoader load(File file){
        try{
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            Object o = objectInputStream.readObject();
            if(o instanceof PieceLoader){
                return (PieceLoader)o;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new PieceLoader();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PieceLoader{");
        sb.append("pieces=").append(pieces);
        sb.append('}');
        return sb.toString();
    }
}

