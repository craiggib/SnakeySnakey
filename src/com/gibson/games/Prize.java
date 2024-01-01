package com.gibson.games;

import java.awt.Graphics;
import java.awt.Image;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;

public class Prize {

    // location
    private int prize_x, prize_y, boardHeight, boardWidth, tileSize;

    // graphics
    private Image prizeImage;

    public Prize(int boardWidth, int boardHeight, int tileSize) {
        ImageIcon imageIcon = new ImageIcon("src/com/gibson/games/resources/apple.png");
        prizeImage = imageIcon.getImage();
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.tileSize = tileSize;
    }

    public void setRandomLocation() {
        // generate a tile location
        // x location options = (0.. board width / tile size)
        // y location options = (0.. board width / tile size)
        prize_x = ThreadLocalRandom.current().nextInt(0, boardWidth / tileSize) * tileSize;
        prize_y = ThreadLocalRandom.current().nextInt(0, boardHeight / tileSize) * tileSize;
    }

    public void drawPrize(Graphics graphics) {
        graphics.drawImage(prizeImage, prize_x, prize_y, null);
        // System.out.println("prize location: " + prize_x + "," + prize_y);
    }

    public int getPrizeXLocation() {
        return prize_x;
    }

    public int getPrizeYLocation() {
        return prize_y;
    }
}
