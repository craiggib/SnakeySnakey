package com.gibson.games;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class Snake {

    // snake constants
    private final int SNAKE_INITIAL_SIZE = 3;

    // snake vector
    // theoritically its possible to build a snake the size of the board, but
    // unlikely
    private int snake_position_x[];// = new int[TILE_COUNT];
    private int snake_position_y[];// = new int[TILE_COUNT];
    private int snake_size;
    private int tileSize;
    private Direction currentDirection;

    // graphics
    private Image snake_head, snake_body;

    public Snake(int playableTiles, int startingPosition, int gameBoardTileSize) {
        snake_position_x = new int[playableTiles];
        snake_position_y = new int[playableTiles];
        snake_size = SNAKE_INITIAL_SIZE;
        tileSize = gameBoardTileSize;

        for (int i = 0; i < snake_size; i++) {
            snake_position_x[i] = (startingPosition * tileSize) - i * tileSize;
            snake_position_y[i] = (startingPosition * tileSize);
        }

        ImageIcon imageIcon = new ImageIcon("src/com/gibson/games/resources/body.png");
        snake_body = imageIcon.getImage();

        imageIcon = new ImageIcon("src/com/gibson/games/resources/head.png");
        snake_head = imageIcon.getImage();

        currentDirection = Direction.RIGHT;

    }

    public void drawSnake(Graphics graphics) {
        // printSnakeVector();
        for (int i = 0; i < snake_size; i++) {
            if (i == 0) {
                graphics.drawImage(snake_head, snake_position_x[i], snake_position_y[i], null);
            } else {
                graphics.drawImage(snake_body, snake_position_x[i], snake_position_y[i], null);
            }
        }
    }

    private void printSnakeVector() {
        System.out.println("snake vector ");
        for (int i = 0; i < snake_size; i++) {
            System.out.print(i);
            System.out.println(": " + snake_position_x[i] + "," + snake_position_y[i]);
        }
    }

    public void move() {
        for (int i = snake_size; i > 0; i--) {
            snake_position_x[i] = snake_position_x[(i - 1)];
            snake_position_y[i] = snake_position_y[(i - 1)];
        }
        // System.out.println("snake direction " + currentDirection);

        switch (currentDirection) {
            case Direction.LEFT:
                snake_position_x[0] -= tileSize;
                break;
            case Direction.RIGHT:
                snake_position_x[0] += tileSize;
                break;
            case Direction.UP:
                snake_position_y[0] -= tileSize;
                break;
            case Direction.DOWN:
                snake_position_y[0] += tileSize;
                break;
        }
    }

    public Direction getDirection() {
        return currentDirection;
    }

    public void setDirection(Direction direction) {
        if (direction == currentDirection)
            return;
        if (direction == Direction.LEFT && currentDirection == Direction.RIGHT)
            return;
        if (direction == Direction.RIGHT && currentDirection == Direction.LEFT)
            return;
        if (direction == Direction.UP && currentDirection == Direction.DOWN)
            return;
        if (direction == Direction.DOWN && currentDirection == Direction.UP)
            return;

        currentDirection = direction;
    }

    public int[] getSnakeXPosition() {
        return snake_position_x;
    }

    public int[] getSnakeYPosition() {
        return snake_position_y;
    }

    public int getSnakeLength() {
        return snake_size;
    }

    public void grow() {
        snake_size++;
    }

}
