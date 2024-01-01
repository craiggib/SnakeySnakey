package com.gibson.games;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

public class GameBoard extends JPanel {

    // play area constants
    private final int BOARD_WIDTH = 600; // pixels
    private final int BOARD_HEIGHT = 600; // pixels
    private final int TILE_SIZE = 40; // pixels
    private final int TILE_COUNT = (BOARD_WIDTH * BOARD_HEIGHT) / (TILE_SIZE * TILE_SIZE);
    private final int STARTING_TILE = 5;
    private final int TICK_DELAY = 140;

    private Snake snake;
    private Prize prize;

    private Timer gameTimer;
    private boolean gameActive;
    Clip backgroundMusic, powerUpSound, gameOverSound;
    private ScoreArea scoreArea;
    private int score = 0;

    public GameBoard(ScoreArea scoreArea) {
        initializeBoard();
        this.scoreArea = scoreArea;
    }

    private void initializeBoard() {
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        initializeGame();
    }

    private void initializeGame() {
        snake = new Snake(TILE_COUNT, STARTING_TILE, TILE_SIZE);
        prize = new Prize(BOARD_WIDTH, BOARD_HEIGHT, TILE_SIZE);
        prize.setRandomLocation();

        addKeyListener(new DirectionKeyListener(snake));
        addKeyListener(new ControlKeyListener(this));

        gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new GameTimer(), TICK_DELAY, TICK_DELAY);

        try {
            initializeSound();
            backgroundMusic.loop(100);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        gameActive = true;
    }

    public void restartGame() {
        score = 0;
        scoreArea.setScoreToDraw(0);
        initializeGame();
    }

    private void initializeSound()
            throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        // background music
        File f = new File("lib/sounds/backgroundMusic.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        backgroundMusic = AudioSystem.getClip();
        backgroundMusic.open(audioIn);

        // power up
        f = new File("lib/sounds/powerup.wav");
        audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        powerUpSound = AudioSystem.getClip();
        powerUpSound.open(audioIn);

        // game over
        f = new File("lib/sounds/gameover.wav");
        audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        gameOverSound = AudioSystem.getClip();
        gameOverSound.open(audioIn);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGameBoard(g);
    }

    private void drawGameBoard(Graphics graphics) {
        if (gameActive) {
            snake.drawSnake(graphics);
            prize.drawPrize(graphics);
            scoreArea.repaint();
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(graphics);
        }
    }

    private void gameOver(Graphics graphics) {

        gameOverSound.start();
        backgroundMusic.stop();
        snake = null;
        prize = null;
        gameTimer.cancel();

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics smallMetrics = getFontMetrics(small);

        graphics.setColor(Color.white);
        graphics.setFont(small);
        graphics.drawString(msg, (BOARD_WIDTH - smallMetrics.stringWidth(msg)) / 2, BOARD_HEIGHT / 2);

        if (score > scoreArea.getHighScore()) {
            scoreArea.updateHighScore(score);

            msg = "New High Score!!";

            graphics.setColor(Color.red);
            Font big = new Font("Helvetica", Font.BOLD, 18);
            graphics.setFont(big);
            FontMetrics largeMetrics = getFontMetrics(big);
            graphics.drawString(msg, (BOARD_WIDTH - largeMetrics.stringWidth(msg)) / 2, BOARD_HEIGHT / 2 + 20);
        }

        msg = "Press space bar to try again";

        graphics.setColor(Color.white);
        graphics.setFont(small);
        graphics.drawString(msg, (BOARD_WIDTH - smallMetrics.stringWidth(msg)) / 2, BOARD_HEIGHT / 2 + 50);

    }

    protected void GameLoopTick() {
        snake.move();
        checkSnakeLocation();
        if (!gameActive) {
            gameTimer.cancel();
        }
    }

    private void checkSnakeLocation() {
        // if we are at the edge of the board width (left or right)
        if (snake.getSnakeXPosition()[0] >= BOARD_WIDTH || snake.getSnakeXPosition()[0] < 0) {
            gameActive = false;
        }
        if (snake.getSnakeYPosition()[0] >= BOARD_HEIGHT || snake.getSnakeYPosition()[0] < 0) {
            gameActive = false;
        }

        // if we have run into our self, we ded
        if (snake.getSnakeLength() > 4) {
            for (int i = snake.getSnakeLength(); i > 0; i--) {
                if (snake.getSnakeXPosition()[0] == snake.getSnakeXPosition()[i]
                        && snake.getSnakeYPosition()[0] == snake.getSnakeYPosition()[i]) {
                    gameActive = false;
                    break;
                }
            }
        }

        // if we hit an apple, increase the length
        if (snake.getSnakeXPosition()[0] == prize.getPrizeXLocation()
                && snake.getSnakeYPosition()[0] == prize.getPrizeYLocation()) {

            score++;
            snake.grow();
            powerUpSound.start();
            powerUpSound.setFramePosition(0);
            prize.setRandomLocation();
            scoreArea.setScoreToDraw(score);

        }

    }

    public boolean isGameActive() {
        return gameActive;
    }

    public class GameTimer extends TimerTask {

        @Override
        public void run() {
            GameLoopTick();
            repaint();
        }

    }

}
