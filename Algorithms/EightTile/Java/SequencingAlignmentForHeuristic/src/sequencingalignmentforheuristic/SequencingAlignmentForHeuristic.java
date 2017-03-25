/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sequencingalignmentforheuristic;

/**
 *
 * @author Luke
 */
public class SequencingAlignmentForHeuristic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int[] goal = {1, 2, 3};
        int[] test = {1, 3, 2,};
        // The penalties to apply
        int gap = 1, substitution = 1, match = 0;

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
                int scoreDiag = opt[i - 1][j - 1]
                        + (goal[i - 1] == test[j - 1]
                                ? match
                                : // same symbol
                                substitution); // different symbol
                int scoreLeft = opt[i][j - 1] + gap; // insertion
                int scoreUp = opt[i - 1][j] + gap; // deletion
                // we take the minimum
                opt[i][j] = Math.min(Math.min(scoreDiag, scoreLeft), scoreUp);
            }
        }

        for (int i = 0; i <= goal.length; i++) {
            for (int j = 0; j <= test.length; j++) {
                System.out.print(opt[i][j] + "\t");
            }
            System.out.println();
        }

    }
}
