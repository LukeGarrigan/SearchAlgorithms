/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iterativedeepeningastar;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Luke
 */
public class IterativeDeepeningAStar {

    /**
     * @param args the command line arguments
     */
    static int[] GOAL = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};
    
    static LinearConflicts lin;
    
    public static void main(String[] args) {
        // Testing moves
        int[] fiveMoves = new int[]{2, 0, 3, 1, 5, 6, 4, 7, 8};
        int[] tenMovesTwo = new int[]{2, 3, 6, 1, 5, 8, 0, 4, 7};
        int[] fifteenMoves = new int[]{3, 6, 8, 2, 5, 0, 1, 4, 7};
        int[] twentyMoves = new int[]{0, 6, 8, 3, 5, 7, 2, 1, 4};
        int[] twentyFive = new int[]{6, 8, 7, 3, 5, 4, 2, 0, 1};
        int[] thirtyMoves = new int[]{8, 6, 7, 2, 5, 4, 0, 3, 1};

        int[] test = new int[]{6,1,0,8,4,2,3,5,7};
        //float man = manhattan(thirtyMoves); // manhattan distance of the current state
        lin = new LinearConflicts();
        float li = lin.linearConflict(thirtyMoves);
        IDAStar id = new IDAStar();
        State initialState = new State(thirtyMoves, 0, li, null, "null");
        id.IDAStar(initialState);
       // IDAStar(initialState);
    }
     
}
