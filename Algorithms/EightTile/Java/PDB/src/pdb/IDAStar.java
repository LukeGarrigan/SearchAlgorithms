/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdb;

import java.util.Arrays;
import static pdb.PDB.explored;

/**
 *
 * @author Luke
 */
public class IDAStar {

    private int nextCostBound;
    int[] GOAL = {1, 2, 3, 4, 5, 6, 7, 8, 0};

    public PDB.State depthFirstSearch(PDB.State current, int currentCostBound) {
        if (Arrays.equals(current.getState(), GOAL)) {
            return current;
        }

        for (PDB.State next : current.findNeighbours()) {
            // we need to find the actual next value
            /*
            for (PDB.State s : explored) {
                if (s.equals(next)) {
                    next = s;
                }
            }
             */
            next.setH(PDB.calculate(next.getState()));
            next.setG(current.getG() + 1);
            //System.out.println(Arrays.toString(next.getState()) + " H: " + next.getH() + " G: " + next.getG());

            //Manhattan m = new Manhattan();
            //float li = heuristic.calculateHeuristic(next.getState());
            //next.setH(li);
            int value = next.getG() + next.getH();

            if (value <= currentCostBound) {
                PDB.State result = depthFirstSearch(next, currentCostBound);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public PDB.State resolve(PDB.State start) {
        // should already have heuristic because the initial state
        start.setH(PDB.calculate(start.getState()));

        nextCostBound = start.getH();
        PDB.State solution = null;

        while (solution == null) {
            int currentCostBound = nextCostBound;
            solution = depthFirstSearch(start, currentCostBound);
            nextCostBound += 2;
        }
        return solution;
    }
}
