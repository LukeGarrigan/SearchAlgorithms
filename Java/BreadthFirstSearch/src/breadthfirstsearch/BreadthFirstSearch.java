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
        int[] thirtyOneMoves = new int[]{8, 6, 7, 2, 5, 4, 3, 0, 1};
        int[] tenMoves = new int[]{2, 3, 5, 1, 4, 6, 0, 7, 8};
        int[] twentyFour = new int[]{8, 5, 1, 3, 4, 7, 0, 6, 2};
        int[] oneMove = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 8};

        // Testing moves
        int[] fiveMoves = new int[]{2, 0, 3, 1, 5, 6, 4, 7, 8};
        int[] tenMovesTwo = new int[]{2, 3, 6, 1, 5, 8, 0, 4, 7};
        int[] fifteenMoves = new int[]{3, 6, 8, 2, 5, 0, 1, 4, 7};
        int[] twentyMoves = new int[]{0, 6, 8, 3, 5, 7, 2, 1, 4};

        int[] eighteen = new int[]{6, 1, 3, 7, 0, 8, 5, 2, 4};
        State initialState = new State(tenMoves, null, "null");
        QUEUE.add(initialState);
        State current = null;
        int nodeCount = 0;
        while (!QUEUE.isEmpty()) {
           
            current = QUEUE.remove();
            if (Arrays.equals(current.getState(), GOAL)) {
                printPath(current, startTime);
                break;
            }
            SET.add(current);

            current.findNeighbours();
            ArrayList<State> neighbours = current.getNeighbours();
            for (State n : neighbours) {
                 nodeCount = nodeCount + 1;
                if (!SET.contains(n)) {
                    SET.add(n);

                    if (!QUEUE.contains(n)) {
                        QUEUE.add(n);
                    }
                }
            }
        }
        System.out.println(nodeCount);
        System.exit(0);
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
