package com.example.tetris;


import java.util.ArrayList;
import java.util.Arrays;

public class TPiece extends Piece{
    
    private static final int[][] shape1 = { {0, 0, 0, 0},
                                            {0, 1, 0, 0},
                                            {1, 1, 1, 0},
                                            {0, 0, 0, 0}};
    
    private static final int[][] shape2 = { {0, 0, 0, 0},
                                            {0, 1, 0, 0},
                                            {0, 1, 1, 0},
                                            {0, 1, 0, 0}};
    
    
    private static final int[][] shape3 = { {0, 0, 0, 0},
                                            {0, 0, 0, 0},
                                            {1, 1, 1, 0},
                                            {0, 1, 0, 0}};
    
    
    
    
    private static final int[][] shape4 = { {0, 0, 0, 0},
                                            {0, 1, 0, 0},
                                            {1, 1, 0, 0},
                                            {0, 1, 0, 0}};
    
    public TPiece() {
        super(new ArrayList<>(Arrays.asList(shape1, shape2, shape3, shape4)), Block.MAGENTA);
    }

}
