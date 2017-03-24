/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdb15tile;

import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Luke
 */
public class IDAStar {

    private int nextCostBound;
    int[] GOAL = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};

    CalculatePDBHeuristic pdb = new CalculatePDBHeuristic();

   
    public PDB15Tile.State depthFirstSearch(PDB15Tile.State current, int currentCostBound) {
        if (Arrays.equals(current.getState(), GOAL)) {
            System.out.println("GOALL");
            return current;
        }

        for (PDB15Tile.State next : current.findNeighbours()) {
            int h = pdb.calculate(next.getState());
            next.setG(current.getG() + 1);
            next.setH(h);
            int value = next.getG() + next.getH();
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

    public PDB15Tile.State resolve(PDB15Tile.State start) throws IOException, ClassNotFoundException {
        // should already have heuristic because the initial state
        pdb.getStoredFiles();
        int h = pdb.calculate(start.getState());
        start.setH(h);
        nextCostBound = start.getH();
        PDB15Tile.State solution = null;

        while (solution == null) {
            int currentCostBound = nextCostBound;
            solution = depthFirstSearch(start, currentCostBound);
            nextCostBound += 2;
        }
        return solution;
    }
}
