/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package depthfirstsearch;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 *
 * @author Luke
 */
public class DepthFirstSearch {

    /**
     * @param args the command line arguments
     */
    static LinkedHashSet<int[][]> OPEN = new LinkedHashSet<>();
    static HashSet<int[][]> CLOSED = new HashSet<>();

    static int[][] puz = new int[][]{
        {1, 2, 3}, // [0][0]  [0][1] [0][2]
        {4, 5, 6}, // [1][0]  [1][1] [1][2]
        {7, 8, 0} // [2][0]  [2][1] [2][2]
    };
    static boolean GOAL = false;

    public static void main(String[] args) {
        // TODO code application logic here
        //System.out.println(Arrays.deepToString(puz));
        int[][] puzzle = initialisePuzzle10Moves();
        OPEN.add(puzzle);
        while (OPEN.isEmpty() == false && GOAL == false) {
            move();
        }

    }

    static void move() {
        int[][] puzzle = OPEN.iterator().next();
        // find position of 0
        OPEN.remove(puzzle);
        // System.out.println(OPEN.stream());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (puzzle[i][j] == 0) {
                   // System.out.println(Arrays.deepToString(puzzle));
                    if (Arrays.deepEquals(puzzle, puz)) {
                        GOAL = true;
                        System.out.println("We did it");
                    } else {
                        CLOSED.add(puzzle);
                        int[][] temp;
                        temp = right(puzzle, i, j);
                        if (temp != null) {
                            OPEN.add(temp);
                            // System.out.println(Arrays.deepToString(temp));
                        }

                        temp = left(puzzle, i, j);
                        if (temp != null) {
                            OPEN.add(temp);
                            // System.out.println(Arrays.deepToString(temp));
                        }

                        temp = down(puzzle, i, j);
                        if (temp != null) {
                            OPEN.add(temp);
                            // System.out.println(Arrays.deepToString(temp));
                        }

                        temp = up(puzzle, i, j);
                        if (temp != null) {
                            OPEN.add(temp);
                            //System.out.println(Arrays.deepToString(temp));
                        }
                    }
                }
            }
        }
    }

    static int[][] left(int[][] puzzle, int i, int j) {
        // Move left
        int[][] left = new int[3][3];

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                left[col][row] = puzzle[col][row];
            }
        }
        if (j > 0) {
            int temp = left[i][j - 1];
            left[i][j - 1] = 0;
            left[i][j] = temp;
        } else {
            return null;
        }
        return left;
    }

    static int[][] right(int[][] puzzle, int i, int j) {
        int[][] right = new int[3][3];

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                right[col][row] = puzzle[col][row];
            }
        }

        // Move right
        if (j < 2) {
            int temp = right[i][j + 1];
            right[i][j + 1] = 0;
            right[i][j] = temp;
        } else {
            return null;
        }
        return right;
    }

    static int[][] down(int[][] puzzle, int i, int j) {
        int[][] down = new int[3][3];

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                down[col][row] = puzzle[col][row];
            }
        }
        // Move right
        if (i < 2) {
            int temp = down[i + 1][j];
            down[i + 1][j] = 0;
            down[i][j] = temp;
        } else {
            return null;
        }
        return down;
    }

    static int[][] up(int[][] puzzle, int i, int j) {
        int[][] up = new int[3][3];

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                up[col][row] = puzzle[col][row];
            }
        }

        // Move right
        if (i > 0) {
            int temp = up[i - 1][j];
            up[i - 1][j] = 0;
            up[i][j] = temp;
        } else {
            return null;
        }
        return up;
    }

    static boolean isGoal(int[][] puzzle) {
        return puzzle == puz;
    }

    static int[][] initialisePuzzle10Moves() {
        int[][] problemState = new int[][]{
            {2, 3, 5},
            {1, 4, 6},
            {0, 7, 8}
        };

        return problemState;
    }
    static int[][] initialisePuzzle20Moves() {
        int[][] problemState = new int[][]{
            {4, 7, 3},
            {1, 6, 8},
            {2, 5, 0}
        };

        return problemState;
    }

}
