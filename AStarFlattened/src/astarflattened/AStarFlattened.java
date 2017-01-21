/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarflattened;

import java.util.Timer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimerTask;

/**
 *
 * @author Luke
 */
public class AStarFlattened {

    /**
     * @param args the command line arguments
     */
    static int[] GOAL = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};

    static ArrayList<State> OPENSET = new ArrayList<>();
    static ArrayList<State> CLOSEDSET = new ArrayList<>();

    public static void main(String[] args) {
        // TODO code application logic here
        Timer t = new Timer();
        int[] problemState = new int[]{3, 2, 1, 7, 5, 8, 0, 6, 4};
        int[] puzzle = new int[]{2, 3, 5, 1, 4, 6, 0, 7, 8};
        int[] puzzles = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 8};

        long startTime = System.nanoTime();
        int dist = manhattan(problemState);
        State initialState = new State(problemState, 0, dist, null, "null");

        OPENSET.add(initialState);
        while (OPENSET.isEmpty() == false) {

            if (OPENSET.size() > 0) {
                int winner = 0;
                for (int i = 0; i < OPENSET.size(); i++) {
                    if (OPENSET.get(i).getF() < OPENSET.get(winner).getF()) {
                        winner = i;
                    }
                }

                State currentState = OPENSET.get(winner);

                // System.out.println(currentState.getF() + " = " + currentState.getG() + " + " + currentState.getH() );
                // State currentState = new State(OPENSET.get(winner));
                //System.out.println(Arrays.toString(currentState.getState()));
                if (Arrays.equals(currentState.getState(), GOAL)) {
                    float duration = (System.nanoTime() - startTime)/1000000;
                    System.out.println("Winner " + duration);

                    break;
                }
                OPENSET.remove(currentState);
                CLOSEDSET.add(currentState);

                //System.out.println(Arrays.toString(currentState.getState()));
                // finds all the possible states from current state
                currentState.findNeighbours();
                //System.out.println(OPENSET.size() + " "+ CLOSEDSET.size());
                ArrayList<State> neighbours = currentState.getNeighbours();
                //System.out.println("Next");

                for (int i = 0; i < neighbours.size(); i++) {
                    State neighbour = neighbours.get(i);

                    if (!CLOSEDSET.contains(neighbour)) {
                        //  System.out.println(Arrays.toString(neighbour.getState()));
                        float tempG = currentState.getG() + 1;

                        if (OPENSET.contains(neighbour)) {
                            if (tempG < neighbour.getG()) {
                                neighbour.setG(tempG);
                            }
                        } else {
                            neighbour.setG(tempG);
                            OPENSET.add(neighbour);
                        }
                        int man = manhattan(neighbour.getState());
                        neighbour.setH(man);
                        neighbour.setF(neighbour.getG() + neighbour.getH());

                    }

                }
            }

        }
    }

    public static int calculateManhattanDistance(int[] tile) {
        int tiles[][] = {
            {tile[0], tile[1], tile[2]},
            {tile[3], tile[4], tile[5]},
            {tile[6], tile[7], tile[8]}
        };
        int manhattanDistanceSum = 0;
        for (int x = 0; x < 3; x++) // x-dimension, traversing rows (i)
        {
            for (int y = 0; y < 3; y++) { // y-dimension, traversing cols (j)
                int value = tiles[x][y]; // tiles array contains board elements
                if (value != 0) { // we don't compute MD for element 0
                    int targetX = (value - 1) / 3; // expected x-coordinate (row)
                    int targetY = (value - 1) % 3; // expected y-coordinate (col)
                    int dx = x - targetX; // x-distance to expected coordinate
                    int dy = y - targetY; // y-distance to expected coordinate
                    manhattanDistanceSum += Math.abs(dx) + Math.abs(dy);
                }
            }
        }

        return manhattanDistanceSum;
    }

    public static int manhattan(int[] puzz) {
        //System.out.println(Arrays.toString(puzz));
        int total = 0;
        for (int i = 1; i < puzz.length; i++) {
            int expectedRow = (i - 1) / 3;
            int expectedCol = (i - 1) % 3;
            //. System.out.println(expectedRow + " " +expectedCol);
            int num = 0;
            for (int j = 0; j < puzz.length; j++) {
                if (puzz[j] == i) {
                    num = j + 1;
                    break;
                }
            }
            int numRow = (num - 1) / 3;
            int numCol = (num - 1) % 3;
            // System.out.println(numRow + " " +numCol + " \n");

            total += Math.abs(expectedRow - numRow)
                    + Math.abs(expectedCol - numCol);
        }
        // System.out.println("\nTotal = "+total);
        return total;
    }
}
