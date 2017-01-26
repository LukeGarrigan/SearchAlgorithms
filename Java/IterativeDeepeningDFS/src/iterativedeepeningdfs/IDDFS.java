/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iterativedeepeningdfs;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Luke
 */
public class IDDFS {

    static int[] GOAL = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};

    public void IDDFS(State root) {

        for (int depth = 0; depth < Integer.MAX_VALUE; depth++) {
            State found = DLS(root, depth);
            if (found != null) {
                System.out.println("Goal found");
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
            for (State n : neighbours) {
                State found = DLS(n, depth - 1);
                if (found != null) {
                    System.out.println(Arrays.toString(found.getState()));
                    return found;
                }
            }
        }
        return null;
    }

}
