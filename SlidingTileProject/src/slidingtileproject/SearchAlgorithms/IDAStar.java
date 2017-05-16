/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slidingtileproject.SearchAlgorithms;

import slidingtileproject.SearchAlgorithms.SearchAlgorithm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import slidingtileproject.Heuristics.HeuristicFunction;
import slidingtileproject.State;

/**
 *
 * @author Luke
 */
public class IDAStar implements SearchAlgorithm {

    private HeuristicFunction heuristic;
    // gets created when IDAStar is invoked

    public IDAStar(HeuristicFunction heuristic) {
        this.heuristic = heuristic;
    }

    private State depthFirstSearch(State current, State goal, int currentCostBound) {
        if (current.equals(goal)) {
            return current;
        }

        for (State next : current.findNeighbours()) {
            next.setG(current.getG() + 1);
            int li = heuristic.calculateHeuristic(next);
            next.setH(li);
            int value = next.getG() + next.getH();
            if (value <= currentCostBound) {
                State result = depthFirstSearch(next, goal, currentCostBound);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    @Override
    public State resolve(State start, State goal) {
        // should already have heuristic because the initial state
        int nextCostBound = start.getH();
        State solution = null;
        
        while (solution == null) {
            int currentCostBound = nextCostBound;
            solution = depthFirstSearch(start, goal, currentCostBound);
            nextCostBound += 2;
        }
        return solution;
    }

    public int resolve1(State state) {
        int bound = state.getH();
        while (bound != -1) {
            bound = dfs(state, bound);
        }
        return bound;
    }

    public int dfs(State state, int bound) {
        int f = state.getG() + state.getH();
        // no need to check if it has a higher bound
        if (f > bound) {
            return f;
        }
        if (state.getH() < 1) {
            System.out.println("Moves taken " + state.getG());
            return -412414;
        }
        int min = 1000;
        for (State next : state.findNeighbours()) {
            next.setG(state.getG() + 1);
            int li = heuristic.calculateHeuristic(next);
            next.setH(li);
            int temp = dfs(next, bound);
            if (temp < min) {
                min = temp;
            }
        }
        return min;
    }

}
