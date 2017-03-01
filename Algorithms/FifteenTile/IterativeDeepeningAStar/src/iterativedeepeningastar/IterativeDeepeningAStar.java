/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iterativedeepeningastar;

import static iterativedeepeningastar.IDAStar.statesExpanded;
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
    public static void main(String[] args) {

        int[] test = new int[]{3, 4, 10, 2, 1, 9, 12, 11, 5, 6, 13, 15, 7, 0, 8, 14};

        // All the testing states for sliding tile 4x4
        int[] fiveMoves = new int[]{1, 0, 2, 3, 5, 6, 7, 4, 9, 10, 11, 8, 13, 14, 15, 12};
        int[] tenMoves = new int[]{5, 1, 2, 3, 9, 6, 7, 4, 13, 10, 11, 8, 14, 0, 15, 12};
        int[] fifteenMoves = new int[]{5, 1, 2, 3, 9, 7, 0, 4, 13, 6, 10, 8, 14, 15, 11, 12};
        int[] twentyMoves = new int[]{5, 1, 3, 4, 9, 7, 2, 8, 13, 6, 10, 12, 14, 15, 11, 0};
        int[] twentyFiveMoves = new int[]{5, 1, 3, 4, 7, 2, 8, 12, 9, 6, 10, 0, 13, 14, 15, 11};
        int[] thirtyMoves = new int[]{5, 2, 1, 3, 7, 0, 8, 4, 9, 6, 10, 12, 13, 14, 15, 11};
        int[] thirtyFiveMoves = new int[]{5, 0, 2, 1, 7, 6, 8, 3, 9, 10, 15, 4, 13, 14, 11, 12};
        int[] fourtyMoves = new int[]{6, 7, 0, 11, 1, 5, 10, 4, 14, 13, 15, 2, 9, 8, 3, 12}; 
        int[] fourtyFiveMoves = new int[]{11, 3, 9, 2, 1, 10, 0, 7, 13, 5, 12, 8, 6, 15, 4, 14};
        int[] fiftyMoves = new int[]{12, 6, 3, 4, 9, 5, 14, 1, 8, 11, 13, 7, 2, 0, 15, 10};
        int[] fiftyFiveMoves = new int[]{6, 13, 4, 15, 3, 9, 0, 5, 7, 11, 8, 10, 12, 2, 1, 14};
        int[] sixtyMoves = new int[]{8, 14, 1, 5, 7, 12, 15, 6, 0, 11, 10, 9, 13, 2, 3, 4}; 
        int[] sixtyFiveMoves = new int[]{11, 14, 9, 15, 7, 2, 8, 13, 3, 0, 5, 6, 12, 1, 10, 4};
        
        LinearConflicts lin = new LinearConflicts();
        float li = lin.linearConflict(thirtyFiveMoves);
       
        IDAStar id = new IDAStar();
        double startTime = System.nanoTime();
        State initialState = new State(thirtyFiveMoves, 0, li, null, "null");
        State goal = id.IDAStar(initialState);
        printPath(goal,startTime);

    }
       public static void printPath(State currentState, double startTime) {
        double duration = (System.nanoTime() - startTime) / 1000000;
        System.out.println("Solved " + duration + " ms ");
        int count = 0;
        while (currentState.getPreviousState() != null) {
            count++;
            System.out.print(currentState.getDirection() + " ");
            currentState = currentState.getPreviousState();
        }
        System.out.println("\nNumber of moves: " + count);
        System.out.println("States expanded " + statesExpanded);
    }

}
