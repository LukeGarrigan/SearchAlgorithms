/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slidingtileproject;

import java.util.Arrays;

/**
 *
 * @author Luke
 */
public class SequenceAlign extends Manhattan {

    int[] goalState = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};

    @Override
    public int calculateHeuristic(State puzz) {
        int heuristic = 0;
        // if there is a previous then we don't need to recalculate the 
        // entire heuristics for each element, so there already is an existing
        // array which contains the heuristic estimates
        //if (puzz.getPrevious() != null) {
        //    heuristic += super.calculateSingleHeuristic(puzz);
        // } else {
        heuristic += super.calculateHeuristic(puzz);
        //  }
        //System.out.println(Arrays.toString(puzz.getState()));
        //System.out.println("Just man " + heuristic);
        heuristic += sequenceAlign(goalState, puzz.getState());
       // System.out.println("With " + heuristic);
        return heuristic;
    }

    public int sequenceAlign(int[] goal, int[] test) {
        int gap = 1;
        // The penalties to apply
        int substitution = 1, match = 0;

        int[][] opt = new int[goal.length + 1][goal.length + 1];

        // First of all, compute insertions and deletions at 1st row/column
        for (int i = 1; i <= goal.length; i++) {
            opt[i][0] = opt[i - 1][0] + gap;
        }
        for (int j = 1; j <= test.length; j++) {
            opt[0][j] = opt[0][j - 1] + gap;
        }

        for (int i = 1; i <= goal.length; i++) {
            for (int j = 1; j <= test.length; j++) {
                int scoreDiag = opt[i - 1][j - 1] + (goal[i - 1] == test[j - 1] ? match : substitution); // different symbol
                int scoreLeft = opt[i][j - 1] + gap; // insertion
                int scoreUp = opt[i - 1][j] + gap; // deletion
                // we take the minimum
                opt[i][j] = Math.min(Math.min(scoreDiag, scoreLeft), scoreUp);
            }
        }
        return opt[goal.length][test.length];
    }

}
