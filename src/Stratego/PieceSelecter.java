package Stratego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Bart on 4-6-2016.
 */
public class PieceSelecter extends JDialog{
    public static Dimension SCREEN_SIZE = new Dimension(300,1000);
    public PieceSelecter(ArrayList<Piece> available, PieceListener pieceListener, Point location){
        super();
        TreeMap<Piece,Integer> sorted = new TreeMap<>();
        for(Piece piece : available){
            if(sorted.containsKey(piece)){
                sorted.replace(piece,sorted.get(piece)+1);
            }else {
                sorted.put(piece, 1);
            }
        }

        if(location == null){
            setLocationRelativeTo(null);
        }else{
            setLocation(location);
        }
        setSize(300,(sorted.keySet().size()*25)+50);
        setVisible(true);

        JPanel jPanel = new JPanel(new GridLayout(sorted.keySet().size(),1));
        setContentPane(jPanel);
        for(Piece piece : sorted.keySet()){
            String print = piece.getIdent();
            if(sorted.get(piece) > 1){
                print += " " + sorted.get(piece) + "x";
            }
            JButton jButton = new JButton(print);
            jPanel.add(jButton);
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    pieceListener.select(piece);
                    setVisible(false);
                }
            });
        }

    }
    public void dispose(){
        setVisible(false);
    }

}
abstract class PieceListener{
    public abstract void select(Piece piece);
}

