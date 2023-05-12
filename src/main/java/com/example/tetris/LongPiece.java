package com.example.tetris;


public class LongPiece extends Piece {

    private static final int[][] shape = {{0,0,0,0},
                                          {1,1,1,1}};

    public LongPiece() {
        super(shape, Block.TEAL);
    }

    @Override
    public boolean canMoveRight() {
        return (getCurrentPos()[0] <= 5);
    }

    @Override
    public boolean canMoveLeft() {
        return (getCurrentPos()[0] >= 0);
    }
}
