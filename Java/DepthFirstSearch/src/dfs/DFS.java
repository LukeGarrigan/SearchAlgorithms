/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Luke
 */
public class DFS {

    // done
    static int[] GOAL = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};
    private Stack<State> stack = new Stack<>();
    static ArrayList<State> seenState = new ArrayList<>();
    float startTime = System.nanoTime();

        // Testing moves
        int[] fiveMoves = new int[]{2, 0, 3, 1, 5, 6, 4, 7, 8};
        int[] tenMovesTwo = new int[]{2, 3, 6, 1, 5, 8, 0, 4, 7};
        int[] fifteenMoves = new int[]{3, 6, 8, 2, 5, 0, 1, 4, 7};
        int[] twentyMoves = new int[]{0, 6, 8, 3, 5, 7, 2, 1, 4};
    static int nodesExpanded =0;
    public void dfsWithStack(State s) {
        this.stack.add(s);
        seenState.add(s);
        while (!stack.isEmpty()) {
            nodesExpanded = nodesExpanded +1;
            State state = this.stack.pop();
            state.findNeighbours();
            for (State v : state.getNeighbours()) {

                //    System.out.println(Arrays.toString(v.getState()));
                if (Arrays.equals(v.getState(), GOAL)) {
                    System.out.println("we made it");
                    printPath(state,startTime);
                }
                v.setVisited(true);
                seenState.add(v);
                this.stack.push(v);
            }
        }

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
        System.out.println("Nodes Expanded "+ nodesExpanded);
    }
}
