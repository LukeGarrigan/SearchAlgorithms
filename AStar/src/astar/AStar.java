/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 *
 * @author Luke
 */
public class AStar {

    /**
     * @param args the command line arguments
     */
    static LinkedHashSet<State> OPEN = new LinkedHashSet<>();
    static HashSet<State> CLOSED = new HashSet<>();

    static int[][] puz = new int[][]{
        {1, 2, 3}, // [0][0]  [0][1] [0][2]
        {4, 5, 6}, // [1][0]  [1][1] [1][2]
        {7, 8, 0} // [2][0]  [2][1] [2][2]
    };

    static boolean GOAL = false;

    public static void main(String[] args) {
        // TODO code application logic here
        int[][] puzzle = initialisePuzzle10Moves();
        int man = manhattanDistance(puzzle);
        State firstState = new State(puzzle, 0, man, null);
        OPEN.add(firstState);
        while (OPEN.isEmpty() == false) {

            // Finds the node in the open list which has the smallest F score
            int smallestFScore = 10000;
            State tempState = null;
            State currentState = null;

            if (OPEN.size() == 1) {
                currentState = OPEN.iterator().next();
            } else {
                Iterator<State> it = OPEN.iterator();
                State secondTemp = null;
                while (it.hasNext()) {
                    tempState = it.next();
                    if (tempState.getFScore() < smallestFScore) {
                        smallestFScore = tempState.getFScore();
                        currentState = tempState;
                    }
                }
            }
            //System.out.println("hello");
            // going to need to reconstruct the path here

            if (Arrays.deepEquals(currentState.getState(), puz)) {
                System.out.println("All done and dustesd");
                break;
            }
            OPEN.remove(currentState);
            CLOSED.add(currentState);

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int s = currentState.getState()[i][j];
                    if (s == 0) {
                        // System.out.println(Arrays.deepToString(puzzle));
                        int[][] temp;

                        temp = right(currentState.getState(), i, j);

                        if (temp != null) {
                            int previousGScore = currentState.getGScore();
                            State newNeighbour = new State(temp, previousGScore + 1, man, currentState);

                            if (CLOSED.contains(newNeighbour)) {
                                continue;
                            } else {
                                OPEN.add(newNeighbour);
                            }
                        }

                        temp = left(currentState.getState(), i, j);
                        if (temp != null) {
                            int previousGScore = currentState.getGScore();
                            State newNeighbour = new State(temp, previousGScore + 1, man, currentState);

                            if (CLOSED.contains(newNeighbour)) {
                                continue;
                            } else {
                                OPEN.add(newNeighbour);
                            }
                        }

                        temp = down(currentState.getState(), i, j);

                        if (temp != null) {
                            int previousGScore = currentState.getGScore();
                            State newNeighbour = new State(temp, previousGScore + 1, man, currentState);

                            if (CLOSED.contains(newNeighbour)) {
                                continue;
                            } else {
                                OPEN.add(newNeighbour);
                            }
                        }

                        temp = up(currentState.getState(), i, j);

                        if (temp != null) {
                            int previousGScore = currentState.getGScore();
                            State newNeighbour = new State(temp, previousGScore + 1, man, currentState);

                            if (CLOSED.contains(newNeighbour)) {
                                continue;
                            } else {
                                OPEN.add(newNeighbour);
                            }
                        }
                    }
                }
            }

        }
    }

    static class State {

        int[][] state;
        int g, h, f;
        State previousState;

        public State(int[][] state, int g, int h, State previousState) {
            this.state = state;
            this.g = g;
            this.h = h;
            this.f = g + h;
            this.previousState = previousState;
        }

        public int[][] getState() {
            return this.state;
        }

        public int getFScore() {
            return this.f;
        }

        public int getGScore() {
            return this.g;
        }
    }

    // come back to the manhattan distance
    static int manhattanDistance(int[][] puzzle) {
        int total = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (puzzle[i][j] == puz[i][j]) {
                    total = total + 0;
                } else {
                    total = total + 1;
                }
            }
        }
        return total;
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
