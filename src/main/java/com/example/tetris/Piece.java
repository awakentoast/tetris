package com.example.tetris;

import java.util.ArrayList;
import java.util.Random;

public abstract class Piece {

    static Random r = new Random();
    private int[][] shape;
    private final int colour;
    protected int[] currentPos = {3, 0};
    protected ArrayList<int[][]> shapes;
    
    private int currentShape = 0;
    
    


    protected Piece(ArrayList<int[][]> shapes, int colour) {
        this.shape = shapes.get(0);
        this.shapes = shapes;
        this.colour = colour;
    }

    public int[][] getShape() {
        return shape;
    }
    
    public void rotateClockWise() {
        currentShape = currentShape == 3 ? 0 : ++currentShape;
    }
    
    
    public void rotateCounterClockWise() {
        currentShape = currentShape == 0 ? 3 : --currentShape;
    }
    
    public void setNewShape() {
        shape = shapes.get(currentShape);
    }
    
    public void setManualNewShape(int shapeIndex) {
        shape = shapes.get(shapeIndex);
    }

    public int getColour() {
        return colour;
    }

    public int[] getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int[] currentPos) {
        this.currentPos = currentPos;
    }

    public static Piece getRandomPiece() {
        return switch (r.nextInt(7)) {
            case 0 -> new JPiece();
            case 1 -> new LongPiece();
            case 2 -> new LPiece();
            case 3 -> new SPiece();
            case 4 -> new SquarePiece();
            case 5 -> new TPiece();
            case 6 -> new ZPiece();
            default -> throw new IllegalArgumentException("Invalid piece:");
        };
    }

    protected boolean pieceCanMove(int[][] screen, String direction) {
        int xNew = 0;
        int yNew = 0;
        int[][] tempScreen = new int[screen.length][screen[0].length];

        for (int i = 0; i < screen.length; i++) {
            tempScreen[i] = screen[i].clone();
        }

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (shape[y][x] != 0) {
                    tempScreen[y + currentPos[1]][x + currentPos[0]] = 0;
                }
            }
        }
       
        switch (direction) {
            case "LEFT" -> {
                xNew = currentPos[0] - 1;
                yNew = currentPos[1];
            }

            case "RIGHT" -> {
                xNew = currentPos[0] + 1;
                yNew = currentPos[1];
            }

            case "DOWN" -> {
                xNew = currentPos[0];
                yNew = currentPos[1] + 1;
            }

            default -> System.out.println("error in pieceCanMove class Piece");
        }


        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (shape[y][x] == 1) {
                    if (x + xNew > 9 || y + yNew > 20 || x + xNew < 0) {
                        return false;
                    }
                    if (tempScreen[y + yNew][x + xNew] != 0) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    public boolean pieceCanRotate(int[][] screen, String rotation) {
        int[][] tempScreen = new int[screen.length][screen[0].length];
        
        for (int i = 0; i < screen.length; i++) {
            tempScreen[i] = screen[i].clone();
        }
        
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (shape[y][x] != 0) {
                    tempScreen[y + currentPos[1]][x + currentPos[0]] = 0;
                }
            }
        }
        
        int[][] tempShape = new int[4][4];
        
        switch (rotation) {
            case "COUNTER CLOCKWISE" -> {
                rotateCounterClockWise();
                tempShape = shapes.get(currentShape);
                rotateClockWise();
            }
            
            case "CLOCKWISE" -> {
                rotateClockWise();
                tempShape = shapes.get(currentShape);
                rotateCounterClockWise();
            }
            
            default -> System.out.println("error in piece can rotate");
        }
        
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (tempShape[y][x] == 1) {
                    if (x + currentPos[0] > 9 || y + currentPos[1] > 20 || x + currentPos[0] < 0) {
                        return false;
                    }
                    if (tempScreen[y + currentPos[1]][x + currentPos[0]] != 0) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
}
