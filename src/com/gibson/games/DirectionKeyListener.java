package com.gibson.games;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DirectionKeyListener extends KeyAdapter {

    private Snake snake;

    public DirectionKeyListener(Snake snake) {
        this.snake = snake;
    }

    private Direction TranslateKeyToDirection(int key) {
        switch (key) {
            case KeyEvent.VK_LEFT:
                return Direction.LEFT;
            case KeyEvent.VK_RIGHT:
                return Direction.RIGHT;
            case KeyEvent.VK_UP:
                return Direction.UP;
            case KeyEvent.VK_DOWN:
                return Direction.DOWN;
            default:
                return snake.getDirection();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        Direction inputKey = TranslateKeyToDirection(key);

        // invalid directional changes are left when going right (and vice versa) and up
        // when going down (and vice versa)
        if (inputKey == snake.getDirection())
            return;
        if (inputKey == Direction.LEFT && snake.getDirection() == Direction.RIGHT)
            return;
        if (inputKey == Direction.RIGHT && snake.getDirection() == Direction.LEFT)
            return;
        if (inputKey == Direction.UP && snake.getDirection() == Direction.DOWN)
            return;
        if (inputKey == Direction.DOWN && snake.getDirection() == Direction.UP)
            return;

        snake.setDirection(inputKey);
        // System.out.println(requestedDirection);
    }
}
