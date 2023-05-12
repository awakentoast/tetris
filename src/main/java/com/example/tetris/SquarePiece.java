package com.example.tetris;

public class SquarePiece extends Piece{

    private static final int[][] shape = {{0,1,1,0},
                                          {0,1,1,0}};

    public SquarePiece() {
        super(shape, Block.YELLOW);
    }

    @Override
    public boolean canMoveRight() {
        return (getCurrentPos()[0] <= 6);
    }

    @Override
    public boolean canMoveLeft() {
        return (getCurrentPos()[0] >= -1);
    }
}
