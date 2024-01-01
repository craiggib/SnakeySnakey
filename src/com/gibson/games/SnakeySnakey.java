package com.gibson.games;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class SnakeySnakey extends JFrame {
    public static void main(String[] args) throws Exception {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SnakeySnakey().setVisible(true);
            }
        });
    }

    public SnakeySnakey() {
        initializeGame();
    }

    private void initializeGame() {

        this.setLayout((new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)));
        ScoreArea scoreArea = new ScoreArea();
        add(scoreArea);
        add(new GameBoard(scoreArea));

        setResizable(false);
        pack();

        setTitle("Snakey Snakey");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
