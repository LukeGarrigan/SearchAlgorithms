/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sequencingalignmentforheuristic;

import java.util.Arrays;

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
        int[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
        int[] test = {5, 0, 2, 1, 7, 6, 8, 3, 9, 10, 15, 4, 13, 14, 11, 12};

        // Splits up the arrays and performs the sequence alignment
        int sizeOfNewArrays = (int) Math.sqrt(goal.length);
        System.out.println(sizeOfNewArrays);
        int incrementer = 0;
        int total = 0;

        // this does it for the horizontal values
        for (int i = 1; i <= sizeOfNewArrays; i++) {
            int[] testSection = new int[sizeOfNewArrays];
            int[] goalSection = new int[sizeOfNewArrays];
            for (int j = 0; j < sizeOfNewArrays; j++) {
                testSection[j] = test[j + incrementer];
                goalSection[j] = goal[j + incrementer];
            }
            System.out.println(Arrays.toString(testSection));
            System.out.println(Arrays.toString(goalSection));
            incrementer += sizeOfNewArrays;
            System.out.println("");
            total += sequenceAlign(goalSection, testSection);
        }
        System.out.println(total);

        System.out.println("");
        for (int i = 0; i < sizeOfNewArrays; i++) {
            int[] testSection = new int[sizeOfNewArrays];
            int[] goalSection = new int[sizeOfNewArrays];
            int increment = 0;
            for (int j = 0; j < sizeOfNewArrays; j++) {
                testSection[j] = test[i + increment];
                goalSection[j] = goal[i + increment];
                increment += 4;
            }
            System.out.println(Arrays.toString(testSection));
            System.out.println(Arrays.toString(goalSection));

            total += sequenceAlign(goalSection, testSection);
            System.out.println("");
        }

        System.out.println(total + "\n");
    }

    public static int sequenceAlign(int[] goal, int[] test) {
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
