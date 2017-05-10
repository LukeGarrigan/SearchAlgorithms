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

    public IterativeDeepeningAStar(HeuristicFunction heuristic) {
        this.heuristic = heuristic;
    }

    public State depthFirstSearch(State current, float currentCostBound) {
        if (Arrays.equals(current.getState(), GOAL)) {
            return current;
        }

        for (State next : current.findNeighbours()) {
            next.setG(current.getG() + 1);
            float li = heuristic.calculateHeuristic(next);
            next.setH(li);
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

    public float resolve1(State state) {
        float bound = state.getH();
        while (bound != -1) {
            bound = dfs(state, bound);
        }
        return bound;
    }

    public float dfs(State state, float bound) {
        float f = state.getG() + state.getH();
        // no need to check if it has a higher bound
        if (f > bound) {
            return f;
        }
        if (state.getH() < 1) {
            System.out.println("Moves taken " + state.getG());
            return -412414;
        }
        float min = Float.MAX_VALUE;
        for (State next : state.findNeighbours()) {
            next.setG(state.getG() + 1);
            float li = heuristic.calculateHeuristic(next);
            next.setH(li);
            float temp = dfs(next, bound);
            if (temp < min) {
                min = temp;
            }
        }
        return min;
    }

    @Override
    public void setHeuristic(HeuristicFunction h
    ) {
        this.heuristic = h;
    }

}
