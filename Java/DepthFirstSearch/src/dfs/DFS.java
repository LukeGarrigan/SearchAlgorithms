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

    public void dfsWithStack(State s) {
        this.stack.add(s);
        seenState.add(s);
        while (!stack.isEmpty()) {
            State state = this.stack.pop();
            state.findNeighbours();
            for (State v : state.getNeighbours()) {
            //    System.out.println(Arrays.toString(v.getState()));
                if (Arrays.equals(v.getState(), GOAL)) {
                    System.out.println("we made it");
                }
                v.setVisited(true);
                seenState.add(v);
                this.stack.push(v);
            }
        }
    }

}
