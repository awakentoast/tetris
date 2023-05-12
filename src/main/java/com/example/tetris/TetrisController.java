package com.example.tetris;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TetrisController {


    Timer timer = new Timer();
    private boolean moved = false;
    private volatile boolean gameHasNotEnded = true;
    Random r = new Random();

    private int[][] screen = new int[20][10];


    private Piece currentBlock;

    @FXML
    private Canvas canvas;
    @FXML
    private GraphicsContext graphicsContext;



    private void drawScreen() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicsContext.setStroke(Color.GRAY);

        for (int x = 1; x < 10; x++) {
            graphicsContext.strokeLine((double) x * Block.SIZE, 1, (double) x * Block.SIZE, 999);
        }
        for (int y = 1; y < 20; y++) {
            graphicsContext.strokeLine(0, (double) y * Block.SIZE,500, (double) y * Block.SIZE);
        }

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 10; x++) {
                if (screen[y][x] != 0) {
                    switch (screen[y][x]) {
                        case Block.DARKBLUE -> graphicsContext.setFill(Color.DARKBLUE);
                        case Block.TEAL -> graphicsContext.setFill(Color.TEAL);
                        case Block.ORANGE -> graphicsContext.setFill(Color.ORANGE);
                        case Block.GREEN -> graphicsContext.setFill(Color.GREEN);
                        case Block.YELLOW -> graphicsContext.setFill(Color.YELLOW);
                        case Block.MAGENTA -> graphicsContext.setFill(Color.MAGENTA);
                        case Block.RED -> graphicsContext.setFill(Color.RED);
                        default -> System.out.println("you did wrong in draw board switch color assignment to gc");
                    }
                    graphicsContext.fillRect((double) x * Block.SIZE, (double) y * Block.SIZE,  (double) (x + 1) * Block.SIZE, (double) (y + 1) * Block.SIZE);
                }
            }
        }
    }

    private void tick() {
        int[] currentPos = currentBlock.getCurrentPos();
        currentPos[1]++;
        currentBlock.setCurrentPos(currentPos);
        drawScreen();
    }

    public void gameplayLoop() {
//        System.out.println("im here2");
//        while (gameHasNotEnded) {
//
//            Timeline fiveSecondsWonder = new Timeline(
//                    new KeyFrame(Duration.seconds(5), event -> System.out.println("this is called every 5 seconds on UI thread")));
//            fiveSecondsWonder.setCycleCount(Animation.INDEFINITE);
//            fiveSecondsWonder.play();
//
//
//        }
    }


    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();

        currentBlock = Piece.getPiece(Piece.allPieces[r.nextInt(6)]);
        System.out.println("im here");
        drawScreen();
        gameplayLoop();
    }

    private void updateBlockPosition(String direction) {
        int[][] shapeBlock = currentBlock.getShape();
        int colour = currentBlock.getColour();
        int[] currentPos = currentBlock.getCurrentPos();

        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 4; x++) {
                switch (direction) {
                    case "RIGHT" -> screen[y + currentPos[1]][x + currentPos[0] - 1] = 0;
                    case "LEFT" -> screen[y + currentPos[1]][x + currentPos[0] + 1] = 0;
                    default -> System.out.println("error in updateBlockPosition switch case");
                }
                if (shapeBlock[y][x] == 1) {
                    if (screen[y + currentPos[1]][x + currentPos[0]] == 0) {
                        screen[y + currentPos[1]][x + currentPos[0]] = colour;
                    }
                }
            }
        }
    }


    public void controls(KeyEvent keyEvent) {
        if (!moved) {
            int[] currentPos = currentBlock.getCurrentPos();
            if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
                if (currentBlock.canMoveRight()) {
                    currentPos[0]++;
                    currentBlock.setCurrentPos(currentPos);
                    updateBlockPosition("RIGHT");
                }
            } else if (keyEvent.getCode().equals(KeyCode.LEFT)) {
                if (currentBlock.canMoveLeft()) {
                    currentPos[0]--;
                    currentBlock.setCurrentPos(currentPos);
                    updateBlockPosition("LEFT");
                }
            }
            moved = true;
        }
    }
}