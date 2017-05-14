/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slidingtileproject.SearchAlgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import slidingtileproject.Heuristics.HeuristicFunction;
import slidingtileproject.State;

/**
 *
 * @author Luke
 */
public class AStar implements SearchAlgorithm {

    private HeuristicFunction heuristic;

    public AStar(HeuristicFunction heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public State resolve(State start, State goal) {
        Set<State> closed = new HashSet<>();
        List<State> open = new ArrayList<>();
        open.add(start);
        start.setG(0);
        int li = heuristic.calculateHeuristic(start);
        start.setH(li);
        start.setF(li);
        int nodesExpanded = 0;
        while (!open.isEmpty()) {
            int currentF = Integer.MAX_VALUE;
            State currentState = null;
            for (State s : open) {
                // if the f values are the same it takes, deeper node has favor
                if (s.getF() <= currentF) {
                    currentState = s;
                    currentF = s.getF();
                }
            }
            nodesExpanded++;
            open.remove(currentState);
            closed.add(currentState);
            if (currentState.equals(goal)) {
                currentState.setNodesExpanded(nodesExpanded);
                return currentState;
            }
            for (State neighbour : currentState.findNeighbours()) {
                int lis = heuristic.calculateHeuristic(neighbour);
                neighbour.setH(lis);
                neighbour.setF(neighbour.getH() + neighbour.getG());
                if (!closed.contains(neighbour)) {
                    int index = open.indexOf(neighbour);
                    // open list already contains state; check if route 
                    // to the state improved
                    if (index != -1) {
                        if (neighbour.getG() < open.get(index).getG()) {
                            open.set(index, neighbour);
                        }
                    } else {
                        open.add(neighbour);
                    }
                }
            }
        }
        return null;
    }

}
