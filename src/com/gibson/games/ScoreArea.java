package com.gibson.games;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JPanel;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ScoreArea extends JPanel {

    private final int SCOREAREA_WIDTH = 300;
    private final int SCOREAREA_HEIGHT = 30;

    private int scoreToDraw = 0;
    private int highScore = 0;
    private JSONObject gameJson;
    private final String gameDataFilePath = "src/com/gibson/games/resources/gameData.json";

    public ScoreArea() {
        setBackground(Color.white);
        setPreferredSize(new Dimension(SCOREAREA_WIDTH, SCOREAREA_HEIGHT));
        setVisible(true);

        try (FileInputStream inputStream = new FileInputStream(
                new File(gameDataFilePath))) {
            if (inputStream != null) {

                JSONTokener tokener = new JSONTokener(inputStream);
                gameJson = new JSONObject(tokener);
                highScore = gameJson.getInt("highScore");
            }
        } catch (JSONException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void drawGameStats(Graphics graphics) {

        // player score
        String msg = "Score: " + scoreToDraw;
        Font small = new Font("Helvetica", Font.BOLD, 14);

        graphics.setColor(Color.black);
        graphics.setFont(small);
        graphics.drawString(msg, 10, 20);

        // high score
        msg = "High Score: " + highScore;

        graphics.setColor(Color.black);
        graphics.setFont(small);
        graphics.drawString(msg, 480, 20);

        // System.out.println(scoreToDraw);
    }

    public int getHighScore() {
        return highScore;
    }

    public void updateHighScore(int highScore) {
        this.highScore = highScore;
        gameJson.put("highScore", highScore);

        try (FileWriter file = new FileWriter(gameDataFilePath)) {
            file.write(gameJson.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setScoreToDraw(int score) {
        scoreToDraw = score;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGameStats(g);
    }

}
