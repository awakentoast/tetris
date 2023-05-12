package com.example.tetris;


public abstract class Piece {

    public static final String[] allPieces = {"JPiece", "LongPiece", "LPiece", "SPiece", "SquarePiece", "TPiece", "ZPiece"};

    private final int[][] shape;
    private final int colour;
    private int[] currentPos = {3,0};



    protected Piece(int[][] shape, int colour) {
        this.shape = shape;
        this.colour = colour;
    }

    public abstract boolean  canMoveRight();

    public abstract boolean canMoveLeft();

    public int[][] getShape() {
        return shape;
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

    public static Piece getPiece(String piece) {
        return switch (piece) {
            case "JPiece" -> new JPiece();
            case "LongPiece" -> new LongPiece();
            case "LPiece" -> new LPiece();
            case "SPiece" -> new SPiece();
            case "SquarePiece" -> new SquarePiece();
            case "TPiece" -> new TPiece();
            case "ZPiece" -> new ZPiece();
            default -> throw new IllegalArgumentException("Invalid piece: " + piece);
        };
    }



}
