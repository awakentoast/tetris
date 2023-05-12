package com.example.tetris;


public class SPiece extends Piece {

    private static final int[][] shape = {{0,1,1,0},
                                          {1,1,0,0}};

    public SPiece() {
        super(shape, Block.GREEN);
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
