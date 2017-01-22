/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package depthfirstsearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Timer;

/**
 *
 * @author Luke
 */
public class DepthFirstSearch {

    /**
     * @param args the command line arguments
     */
    static int[] GOAL = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};
    static Set<State> SEEN = new HashSet<>();

    public static void main(String[] args) {
        // TODO code application logic here
        Timer t = new Timer();
        float startTime = System.nanoTime();
        int[] problemState = new int[]{8, 6, 7, 2, 5, 4, 3, 0, 1};
        int[] puzzle = new int[]{2, 3, 5, 1, 4, 6, 0, 7, 8};
        int[] puzzles = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 8};
        State s = new State(puzzle, null, "null");
        DFS(s);
    }

    public static void DFS(State s) {

        Stack<State> stack = new Stack<>();
        stack.push(s);
        SEEN.add(s);

        while (!stack.isEmpty()) {
            State element = stack.pop();
            //System.out.println(Arrays.toString(element.getState()));
            element.findNeighbours();
            ArrayList<State> neighbours = element.getNeighbours();
            for (int i = 0; i < neighbours.size(); i++) {
                State n = neighbours.get(i);
                if (!SEEN.contains(n)) {
                    if (Arrays.equals(n.getState(), GOAL)) {
                        System.out.println("done");
                    }
                    stack.push(n);
                    SEEN.add(n);
                }
            }
        }

    }

}
