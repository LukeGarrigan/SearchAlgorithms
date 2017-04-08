/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slidingtileproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

/**
 *
 * @author Luke
 */
public class IterativeDeepeningAStar implements SearchAlgorithm, UseHeuristic {

    private HeuristicFunction heuristic;
    static int[] GOAL = new int[]{1, 2, 3, 4, 5, 6, 7, 8,
        9, 10, 11, 12, 13, 14, 15, 0};

    private float nextCostBound;
    // gets created when IDAStar is invoked

    static int statesExpanded = 0;

    public IterativeDeepeningAStar(HeuristicFunction heuristic) {
        this.heuristic = heuristic;
    }

    public State depthFirstSearch(State current, float currentCostBound) {
        statesExpanded++;
        if (Arrays.equals(current.getState(), GOAL)) {
            return current;
        }

        for (State next : current.findNeighbours()) {
            next.setG(current.getG() + 1);
            System.out.println("");
            System.out.println(Arrays.toString(current.getState()));
            System.out.println(Arrays.toString(next.getState()));
            float li = heuristic.calculateSingleHeuristic(next);
            float lis = heuristic.calculateHeuristic(next);

            System.out.println(next.getDirection());
            if (li != lis) {
                System.out.println(">...................>");
            }
            System.out.println("");
            //System.out.println("Actual: " + li + " Single: " + lis);
            next.setH(li);
            //System.out.println("");
            //System.out.println(Arrays.toString(next.getState()) + " " + next.getH());
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

    @Override
    public State resolve(State start) {
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

    @Override
    public void setHeuristic(HeuristicFunction h) {
        this.heuristic = h;
    }

}
