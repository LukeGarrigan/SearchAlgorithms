/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarflattened;

import java.util.Timer;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.binarySearch;
import java.util.TimerTask;
import javafx.scene.chart.Axis;

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
        // worlds most difficult sliding tile isntance ( 31 moves )
        int[] problemState = new int[]{8, 6, 7, 2, 5, 4, 3, 0, 1};
        int[] puzzle = new int[]{2, 3, 5, 1, 4, 6, 0, 7, 8};
        int[] puzzles = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 8};

        long startTime = System.nanoTime();
        int dist = manhattan(problemState);
        LinearConflicts l = new LinearConflicts();
       // int lin = l.linearConflict(problemState);
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
                System.out.println(Arrays.toString(currentState.getState()));
                if (Arrays.equals(currentState.getState(), GOAL)) {
                    printPath(currentState, startTime);
                    break;
                }
                OPENSET.remove(currentState);
                CLOSEDSET.add(currentState);
                // finds all the possible states from current state
                currentState.findNeighbours();
                ArrayList<State> neighbours = currentState.getNeighbours();
                for (int i = 0; i < neighbours.size(); i++) {
                    State neighbour = neighbours.get(i);
                    if (!CLOSEDSET.contains(neighbour)) {
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
        System.exit(0);
    }

    public static int manhattan(int[] puzz) {
        //System.out.println(Arrays.toString(puzz));
        int total = 0;
        for (int i = 1; i < puzz.length; i++) {

            int expectedRow = (i - 1) / 3;
            int expectedCol = (i - 1) % 3;
            int num = 0;
            for (int j = 0; j < puzz.length; j++) {
                if (puzz[j] == i) {
                    num = j + 1;
                    break;
                }
            }
            int numRow = (num - 1) / 3;
            int numCol = (num - 1) % 3;
            total += Math.abs(expectedRow - numRow)
                    + Math.abs(expectedCol - numCol);
        }
        return total;
    }

    public static void printPath(State currentState, float startTime) {
        float duration = (System.nanoTime() - startTime) / 1000000;
        System.out.println("Solved " + duration + " ms ");
        int count = 0;
        while (currentState.getPrevious() != null) {
            count++;
            System.out.print(currentState.getDirection() + " ");
            currentState = currentState.getPrevious();
        }
        System.out.println("\n" + count);
    }
}
