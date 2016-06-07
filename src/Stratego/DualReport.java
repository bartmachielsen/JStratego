package Stratego;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Bart on 4-6-2016.
 */
public class DualReport extends JPanel {
    private static Dimension SCREEN_SIZE = new Dimension(500,500);
    private DualResult dualResult;
    public DualReport(DualResult dualResult){
        this.dualResult = dualResult;
        setLayout(new GridLayout(5,1));
        initDialog();
        initPanel();
        initLosers();
    }
    private void initDialog(){
        JDialog jDialog = new JDialog();
        jDialog.setVisible(true);
        jDialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        jDialog.setSize(SCREEN_SIZE);
        jDialog.setLocationRelativeTo(null);
        jDialog.setContentPane(this);
    }
    private void initPanel(){
        JPanel versusPanel = new JPanel();
        versusPanel.setLayout(new GridLayout(1,3));
        versusPanel.add(namePanel(dualResult.getOpponent()));

        JPanel panel = new JPanel(new FlowLayout());
        JLabel jlabel = new JLabel("VS");
        jlabel.setFont(new Font("Arial",Font.BOLD,45));
        if(dualResult.getLosers().contains(dualResult.getOpponent())){
            jlabel.setForeground(Color.red);
        }else{
            jlabel.setForeground(Color.green);
        }
        panel.add(jlabel);
        versusPanel.add(panel);


        versusPanel.add(namePanel(dualResult.getPiece()));
        add(versusPanel);
    }
    private JPanel namePanel(Piece piece){
        JPanel info = new JPanel();
        JLabel type = new JLabel("Piece ");
        info.add(type);
        JLabel label = new JLabel(piece.getIdentw());
        label.setFont(new Font("Arial",Font.BOLD,35));
        info.add(label);
        return info;
    }
    private void initLosers(){
        JPanel content = new JPanel(new FlowLayout());
        JPanel loserPanel = new JPanel(new GridLayout(dualResult.getLosers().size(),1));
        content.add(loserPanel);
        for(Piece piece : dualResult.getLosers()){
            JLabel label = new JLabel(piece.getIdent() + " LOST");
            label.setForeground(Color.red);
            loserPanel.add(label);
        }
        add(content);
    }
}

