package com.example.tetris;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.*;

public class TetrisController {
    
    private boolean gameStarted = false;
    private int[][] screen;
    private Piece currentPiece;
    private Piece holdPiece;
    private boolean pieceHasBeenHeldThisTurn = false;
    
    
    Timer timer = new Timer();
    Random r = new Random();
    

    
    @FXML
    private Canvas holdPieceCanvas;
    
    @FXML
    private Canvas tetrisScreen;
    @FXML
    private GraphicsContext tetrisDraw;
    @FXML
    private GraphicsContext holdPieceDraw;

    @FXML
    private Text startText;

    @FXML
    private Text instructions;
    




    private void drawScreen() {
        tetrisDraw.setFill(Color.BLACK);
        tetrisDraw.fillRect(0, 0, tetrisScreen.getWidth(), tetrisScreen.getHeight());
        tetrisDraw.setFill(Color.WHITE);
        tetrisDraw.fillRect(0, 0, tetrisScreen.getWidth(), (double) 4 * Block.SIZE);
        tetrisDraw.setStroke(Color.GRAY);

        //draw vertical lines
        for (int x = 1; x < 10; x++) {
            tetrisDraw.strokeLine((double) x * Block.SIZE, (double) 1 + 4 * Block.SIZE, (double) x * Block.SIZE, 999);
        }
        
        //draw horizontal lines
        for (int y = 4; y < 24; y++) {
            tetrisDraw.strokeLine(0, (double) y * Block.SIZE,500, (double) y * Block.SIZE);
        }
        
        tetrisDraw.setStroke(Color.BLACK);
        
        for (int y = 0; y < 24; y++) {
            for (int x = 0; x < 10; x++) {
                if (screen[y][x] != 0) {
                    switch (screen[y][x]) {
                        case Block.DARKBLUE -> tetrisDraw.setFill(Color.DARKBLUE);
                        case Block.TEAL -> tetrisDraw.setFill(Color.TEAL);
                        case Block.ORANGE -> tetrisDraw.setFill(Color.ORANGE);
                        case Block.GREEN -> tetrisDraw.setFill(Color.GREEN);
                        case Block.YELLOW -> tetrisDraw.setFill(Color.YELLOW);
                        case Block.MAGENTA -> tetrisDraw.setFill(Color.MAGENTA);
                        case Block.RED -> tetrisDraw.setFill(Color.RED);
                        default -> System.out.println("you did wrong in draw board switch color assignment to gc");
                    }

                    tetrisDraw.fillRect((double) x * Block.SIZE + 1, (double) y * Block.SIZE + 1, (double) Block.SIZE - 2, (double) Block.SIZE - 2);
                    tetrisDraw.strokeRect((double) x * Block.SIZE + 2, (double) y * Block.SIZE + 2, (double) Block.SIZE - 3, (double) Block.SIZE - 3);
                }
            }
        }
    }

    private void checkForTetris() {
        ArrayList<Integer> linesToClear = new ArrayList<>();
        int lineIndex = -1;
        for (int[] line : screen) {
            lineIndex++;
            if (Arrays.stream(line).noneMatch(i -> i == 0)) {
                System.out.println("none match 0 " + lineIndex);
                linesToClear.add(lineIndex);
            }
        }
        if (!linesToClear.isEmpty()) {
            for (int i = linesToClear.get(linesToClear.size() - 1) - linesToClear.size(); i >= 0; i--) {
                screen[i + linesToClear.size()] = screen[i].clone();
            }
        }
    }

    private void tick() {
        if (currentPiece.pieceCanMove(screen,"DOWN")) {
            updateBlockPosition("DOWN");
        } else {
            pieceCantMoveAnymore();
        }
    }

    public void gameplayLoop() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 0, 1000);
        
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                drawScreen();
            }
        }, 0, 17);
    }

    private void updateBlockPosition(String direction) {
        int colour = currentPiece.getColour();
        int[] currentPos = currentPiece.getCurrentPos();
        int[][] shapeBlock = currentPiece.getShape();


        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (shapeBlock[y][x] != 0) {
                    screen[y + currentPos[1]][x + currentPos[0]] = 0;
                }
            }
        }

        switch (direction) {
            case "DOWN" -> currentPos[1]++;

            case "RIGHT" -> currentPos[0]++;

            case "LEFT" -> currentPos[0]--;

            default -> System.out.println("error in updateBlockPosition switch case");
        }

        currentPiece.setCurrentPos(currentPos);

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (shapeBlock[y][x] == 1) {
                    screen[y + currentPos[1]][x + currentPos[0]] = colour;
                }
            }
        }
    }
    
    private void rotateBlock(String rotation) {
        int colour = currentPiece.getColour();
        int[] currentPos = currentPiece.getCurrentPos();
        int[][] shapeBlock = currentPiece.getShape();
        
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (shapeBlock[y][x] != 0) {
                    screen[y + currentPos[1]][x + currentPos[0]] = 0;
                }
            }
        }
        
        switch (rotation) {
            case "COUNTER CLOCKWISE" -> {
                currentPiece.rotateCounterClockWise();
                currentPiece.setNewShape();
            }
            
            case "CLOCKWISE" -> {
                currentPiece.rotateClockWise();
                currentPiece.setNewShape();
            }
            
            default -> System.out.println("error in rotateBlock switch case");
        }
        shapeBlock = currentPiece.getShape();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (shapeBlock[y][x] == 1) {
                    screen[y + currentPos[1]][x + currentPos[0]] = colour;
                }
            }
        }
    }
    
    private void removeCurrentPieceFromScreen() {
        int[] currentPos = currentPiece.getCurrentPos();
        int[][] shapeBlock = currentPiece.getShape();
        
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (shapeBlock[y][x] != 0) {
                    screen[y + currentPos[1]][x + currentPos[0]] = 0;
                }
            }
        }
    }
    
    
    private void pieceCantMoveAnymore() {
        checkForTetris();
        currentPiece = Piece.getRandomPiece();
        int[][] shapePiece = currentPiece.getShape();
        int colour = currentPiece.getColour();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (shapePiece[y][x] == 1) {
                    screen[y][x + 3] = colour;
                }
            }
        }
        pieceHasBeenHeldThisTurn = false;
    }


    public void controls(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER) && !gameStarted) {
            gameStarted = true;
            startText.setText("");
            int[][] shape = currentPiece.getShape();
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    if (shape[y][x] == 1) {
                        screen[y][x + 3] = currentPiece.getColour();
                    }
                }
            }
            gameplayLoop();
        }

        if (gameStarted) {

            if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
                if (currentPiece.pieceCanMove(screen, "RIGHT")) {
                    updateBlockPosition("RIGHT");
                }
            }

            if (keyEvent.getCode().equals(KeyCode.LEFT)) {
                if (currentPiece.pieceCanMove(screen, "LEFT")) {
                    updateBlockPosition("LEFT");
                }
            }

            if (keyEvent.getCode().equals(KeyCode.DOWN)) {
                if (currentPiece.pieceCanMove(screen, "DOWN")) {
                    updateBlockPosition("DOWN");
                }
            }

            if (keyEvent.getCode().equals(KeyCode.SPACE)) {
                while (currentPiece.pieceCanMove(screen, "DOWN")) {
                    updateBlockPosition("DOWN");
                }

                pieceCantMoveAnymore();
            }
            
            if (keyEvent.getCode().equals(KeyCode.C)) {
                if (currentPiece.pieceCanRotate(screen, "CLOCKWISE")) {
                    rotateBlock("CLOCKWISE");
                }
            }
            
            if (keyEvent.getCode().equals(KeyCode.X)) {
                if (currentPiece.pieceCanRotate(screen, "COUNTER CLOCKWISE")) {
                    rotateBlock("COUNTER CLOCKWISE");
                }
            }
            
            if (keyEvent.getCode().equals(KeyCode.SHIFT)) {
                if (!pieceHasBeenHeldThisTurn) {
                    pieceHasBeenHeldThisTurn = true;
                    removeCurrentPieceFromScreen();
                    if (holdPiece == null) {
                        holdPiece = currentPiece;
                        holdPiece.setManualNewShape(0);
                        currentPiece = Piece.getRandomPiece();
                    } else {
                        Piece tempPiece = currentPiece;
                        currentPiece = holdPiece;
                        holdPiece = tempPiece;
                        holdPiece.setManualNewShape(0);
                        
                    }
                    drawHoldPiece();
                }
            }
        }
    }
    
    private void drawHoldPiece() {
        holdPieceDraw.setFill(Color.WHITE);
        holdPieceDraw.fillRect(0, 0, holdPieceCanvas.getWidth(), holdPieceCanvas.getHeight());
        int colour = holdPiece.getColour();
        int[][] shapePiece = holdPiece.getShape();
        
        switch (colour) {
            case Block.DARKBLUE -> holdPieceDraw.setFill(Color.DARKBLUE);
            case Block.TEAL -> holdPieceDraw.setFill(Color.TEAL);
            case Block.ORANGE -> holdPieceDraw.setFill(Color.ORANGE);
            case Block.GREEN -> holdPieceDraw.setFill(Color.GREEN);
            case Block.YELLOW -> holdPieceDraw.setFill(Color.YELLOW);
            case Block.MAGENTA -> holdPieceDraw.setFill(Color.MAGENTA);
            case Block.RED -> holdPieceDraw.setFill(Color.RED);
            default -> System.out.println("you did wrong in draw board switch color assignment to gc");
        }
        
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (shapePiece[y][x] == 1) {
                    holdPieceDraw.fillRect((double) x * Block.SIZE + 1, (double) y * Block.SIZE + 1, (double) Block.SIZE - 2, (double) Block.SIZE - 2);
                    holdPieceDraw.strokeRect((double) x * Block.SIZE + 2, (double) y * Block.SIZE + 2, (double) Block.SIZE - 3, (double) Block.SIZE - 3);
                }
            }
        }
        
    }

    public void initialize() {
        tetrisDraw = tetrisScreen.getGraphicsContext2D();
        holdPieceDraw = holdPieceCanvas.getGraphicsContext2D();
        screen = new int[24][10];
        tetrisScreen.setFocusTraversable(true);
        setInstructions();
        
        drawScreen();
        currentPiece = Piece.getRandomPiece();
    }

    private void setInstructions() {
        instructions.setText("""
                Left arrow: move left
                Right arrow: move right
                Arrow down: move block one down
                Spacebar: move piece to bottom
                x: rotate anti-clockwise
                c: rotate clockwise
                Shift: swap with held piece, or if empty stash piece
                """
        );
    }

    private void printScreen() {
        for (int[] a : screen) {
            System.out.println(Arrays.toString(a));
        }
    }
}
