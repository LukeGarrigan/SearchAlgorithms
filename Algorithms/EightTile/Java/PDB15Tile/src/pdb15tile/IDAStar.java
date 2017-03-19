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
            return current;
        }

        for (PDB15Tile.State next : current.findNeighbours()) {
            float total1 = 0;
            float total2 = 0;
            float total3 = 0;

            /////////////////////////////////////////////////////////////////////
            //////////////////// For the first set//////////////////////////////
            /////////////////////////////////////////////////////////////////////
            int[] empty1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            for (int i = 0; i < PDB15Tile.stored1.length; i++) {
                int value = getPatternPosition(PDB15Tile.stored1[i], next.getState());
                empty1[value] = PDB15Tile.stored1[i];
            }

            for (PDB15Tile.State x : explored1) {
                if (Arrays.equals(x.getState(), empty1)) {
                    total1 = x.getH();
                }
            }

            /////////////////////////////////////////////////////////////////////
            //////////////////// For the second set//////////////////////////////
            /////////////////////////////////////////////////////////////////////
            int[] empty2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            for (int i = 0; i < PDB15Tile.stored2.length; i++) {
                int value = getPatternPosition(PDB15Tile.stored2[i], next.getState());
                empty2[value] = PDB15Tile.stored2[i];
            }

            for (PDB15Tile.State x : PDB15Tile.explored2) {
                if (Arrays.equals(x.getState(), empty2)) {
                    total2 = x.getH();
                }
            }

            /////////////////////////////////////////////////////////////////////
            //////////////////// For the third set//////////////////////////////
            /////////////////////////////////////////////////////////////////////
            int[] empty3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            for (int i = 0; i < PDB15Tile.stored3.length; i++) {
                int value = getPatternPosition(PDB15Tile.stored3[i], next.getState());
                empty3[value] = PDB15Tile.stored3[i];
            }

            for (PDB15Tile.State x : PDB15Tile.explored3) {
                if (Arrays.equals(x.getState(), empty3)) {
                    total3 = x.getH();
                }
            }
            next.setH(total1 + total2 + total3);
            next.setG(current.getG() + 1);
            System.out.println(Arrays.toString(next.getState()) + " H: " + next.getH() + " G: " + next.getG());
            float value = next.getG() + next.getH();

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
