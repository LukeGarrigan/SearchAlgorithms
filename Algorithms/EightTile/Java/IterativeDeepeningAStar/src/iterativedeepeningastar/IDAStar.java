/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iterativedeepeningastar;

import static iterativedeepeningastar.IterativeDeepeningAStar.GOAL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Luke
 */
public class IDAStar {

    static int[] GOAL = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};

    private float nextCostBound;

    public State IDAStar(State start) {
        // should already have heuristic because the initial state
        nextCostBound = start.getH();
        State solution = null;

        while (solution == null) {
            float currentCostBound = nextCostBound;
            solution = depthFirstSearch(start, currentCostBound);
            nextCostBound += 2;
        }
        return solution;
    }

    public State depthFirstSearch(State current, float currentCostBound) {
      
        if (Arrays.equals(current.getState(), GOAL)) {
            System.out.println("First check");
        }

        current.findNeighbours();
        ArrayList<State> successors = current.getNeighbours();

        for (State next : successors) {
            next.setG(current.getG()+1);
            LinearConflicts l = new LinearConflicts();
            next.setH(l.linearConflict(next.getState()));
            float value = next.getG() + next.getH();
            if (value <= currentCostBound) {
                State result = depthFirstSearch(next, currentCostBound);
                if (result != null) {
                    return result;
                }
            }

        }
        return null;
    }

}
