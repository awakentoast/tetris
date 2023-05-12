package com.example.tetris;


public class JPiece extends Piece {

    private static final int[][] shape = {{1, 1, 1, 0}, {0, 0, 1, 0}};

    public JPiece() {
        super(shape, Block.DARKBLUE);
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
