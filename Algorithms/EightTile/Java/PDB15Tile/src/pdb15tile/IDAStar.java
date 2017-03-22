/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdb15tile;

import java.util.Arrays;
import static pdb15tile.PDB15Tile.explored1;
import static pdb15tile.PDB15Tile.getPatternPosition;

/**
 *
 * @author Luke
 */
public class IDAStar {

    private float nextCostBound;
    int[] GOAL = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};

    public PDB15Tile.State depthFirstSearch(PDB15Tile.State current, float currentCostBound) {
        if (Arrays.equals(current.getState(), GOAL)) {
            System.out.println("GOALL");
            return current;
        }

        for (PDB15Tile.State next : current.findNeighbours()) {
            float h = PDB15Tile.getPDBHeuristic2(next.getState());
          
            next.setG(current.getG()+1);
            next.setH(h);
            float value = next.getG() + next.getH();
            //System.out.println(Arrays.toString(next.getState()) + " " + next.getH());
            if (value <= currentCostBound) {
                PDB15Tile.State result = depthFirstSearch(next, currentCostBound);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public PDB15Tile.State resolve(PDB15Tile.State start) {
        // should already have heuristic because the initial state
        nextCostBound = start.getH();
        PDB15Tile.State solution = null;

        while (solution == null) {
            float currentCostBound = nextCostBound;
            solution = depthFirstSearch(start, currentCostBound);
            nextCostBound += 2;
        }
        return solution;
    }
}
