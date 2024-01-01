package com.gibson.games;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ControlKeyListener extends KeyAdapter {
    private GameBoard gameBoard;

    public ControlKeyListener(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    private Controls TranslateKeyToControls(int key) {
        switch (key) {
            case KeyEvent.VK_SPACE:
                return Controls.SPACE;
            default:
                return Controls.UNKNOWN;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        Controls inputKey = TranslateKeyToControls(key);
        if (!gameBoard.isGameActive() && inputKey == Controls.SPACE) {
            gameBoard.restartGame();
        }
    }
}