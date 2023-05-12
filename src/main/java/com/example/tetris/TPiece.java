package com.example.tetris;


public class TPiece extends Piece{

    private static final int[][] shape = {{0,1,0,0},
                                          {1,1,1,0}};

    public TPiece() {
        super(shape, Block.MAGENTA);
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
