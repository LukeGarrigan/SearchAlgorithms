/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iterativedeepeningdfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

/**
 *
 * @author Luke
 */
public class IDDFS {

    static int[] GOAL = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};

    Timer t = new Timer();
    float startTime = System.nanoTime();
    int count = 0;

    public void IDDFS(State root) {
        // Keep on looping until a goal state is found
        for (int depth = 15; depth < Integer.MAX_VALUE; depth++) {
            // System.out.println("Current depth " + depth);
            State found = DLS(root, depth);
            if (found != null) {
                float duration = (System.nanoTime() - startTime) / 1000000;
                System.out.println("Solved " + duration + " ms ");
                System.out.println(count);
                break;
            }
        }
    }

    public State DLS(State node, int depth) {
        if (depth == 0 && Arrays.equals(node.getState(), GOAL)) {
            return node;
        }
        if (depth > 0) {
            node.findNeighbours();
            ArrayList<State> neighbours = node.getNeighbours();
            for (State neighbour : neighbours) {
                count = count + 1;
                State found = DLS(neighbour, depth - 1);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
