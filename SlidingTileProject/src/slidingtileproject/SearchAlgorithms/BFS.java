/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slidingtileproject.SearchAlgorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import slidingtileproject.State;

/**
 *
 * @author Luke
 */
public class BFS implements SearchAlgorithm {

    @Override
    public State resolve(State start, State goal) {
        Set<State> s = new HashSet<>();
        LinkedList<State> q = new LinkedList<>();
        s.add(start);
        q.add(start);
        int nodesExpanded = 0;
        while (q.size() > 0) {
            nodesExpanded++;
            State currentState = q.removeFirst();
            if (currentState.equals(goal)) {
                return currentState;
            }
            for (State neighbour : currentState.findNeighbours()) {
                if (!s.contains(neighbour)) {
                    s.add(neighbour);
                    q.add(neighbour);
                }
            }
        }
        return null;
    }
}
