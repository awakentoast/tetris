package com.example.tetris;

import java.util.ArrayList;
import java.util.Arrays;

public class SquarePiece extends Piece{

    private static final int[][] shape1 = { {0 ,0 ,0 ,0},
                                            {0 ,1 ,1 ,0},
                                            {0 ,1 ,1 ,0},
                                            {0 ,0 ,0 ,0}};

    public SquarePiece() {
        super(new ArrayList<>(Arrays.asList(shape1, shape1, shape1, shape1)), Block.YELLOW);
    }

}
