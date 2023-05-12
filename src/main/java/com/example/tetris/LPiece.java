package com.example.tetris;


public class LPiece extends Piece {

    private static final int[][] shape = {{1, 0, 0, 0},
                                          {1, 1, 1, 0}};

    public LPiece() {
        super(shape, Block.ORANGE);
    }

    @Override
    public boolean canMoveRight() {
        return (getCurrentPos()[0] <= 6);
    }

    @Override
    public boolean canMoveLeft() {
        return (getCurrentPos()[0] >= 0);
    }
}
