package com.example.tetris;


public class ZPiece extends Piece {

    private static final int[][] shape = {{0,0,0,0},
                                          {0,0,0,0},
                                          {1,1,0,0},
                                          {0,1,1,0}};
    public ZPiece() {
        super(shape, Block.RED);
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
