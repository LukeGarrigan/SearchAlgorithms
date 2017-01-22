/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadthfirstsearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;

/**
 *
 * @author Luke
 */
public class BreadthFirstSearch {

    /**
     * @param args the command line arguments
     */
    static int[] GOAL = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};
    static Set<State> SET = new HashSet<>();
    static Queue<State> QUEUE = new LinkedList<>();

    public static void main(String[] args) {
        // TODO code application logic here
        Timer t = new Timer();
        float startTime = System.nanoTime();
        int[] problemState = new int[]{8, 6, 7, 2, 5, 4, 3, 0, 1};
        int[] puzzle = new int[]{2, 3, 5, 1, 4, 6, 0, 7, 8};
        int[] puzzles = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 8};
        int dist = manhattan(puzzle);

        State initialState = new State(puzzle, null, "null");
        QUEUE.add(initialState);
        State current = null;
        while (!QUEUE.isEmpty()) {
            current = QUEUE.remove();
            if (Arrays.equals(current.getState(), GOAL)) {
                printPath(current, startTime);
                break;
            }
            current.findNeighbours();
            ArrayList<State> neighbours = current.getNeighbours();
            for (State n : neighbours) {
                if (!SET.contains(n)) {
                    SET.add(n);
                    QUEUE.add(n);
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
        while (currentState.getPreviousState() != null) {
            count++;
            System.out.print(currentState.getDirection() + " ");
            currentState = currentState.getPreviousState();
        }
        System.out.println("\n" + count);
    }

}
