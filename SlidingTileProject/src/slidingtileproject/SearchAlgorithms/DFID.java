/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slidingtileproject.SearchAlgorithms;

import slidingtileproject.State;

/**
 *
 * @author Luke
 */
public class DFID implements SearchAlgorithm {

    @Override
    public State resolve(State start, State goal) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            State found = dls(start, goal, i);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    int nodesExpanded =0;
    private State dls(State state, State goal, int depth) {
        nodesExpanded++;
        if (depth == 0 && state.equals(goal)) {
            state.setNodesExpanded(nodesExpanded);
            return state;
        }
        if (depth > 0) {
            for (State neighbour : state.findNeighbours()) {
                State found = dls(neighbour, goal, depth - 1);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
