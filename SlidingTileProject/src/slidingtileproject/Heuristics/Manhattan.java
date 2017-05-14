/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slidingtileproject.Heuristics;

import java.util.Arrays;
import slidingtileproject.State;

/**
 *
 * @author Luke
 */
public class Manhattan implements HeuristicFunction {

    @Override
    public int calculateHeuristic(State state) {
        // Expected row and col depend on size of puzz nxn
        int[] puzz = state.getState();
        int total = 0;
        int dimension = (int) Math.sqrt(puzz.length);
        int[] heuristicArray = new int[puzz.length];
        for (int j = 0; j < puzz.length; j++) {
            int tileValue = puzz[j];
            if (tileValue != 0) {
                int expectedRow = (tileValue - 1) / dimension;
                int expectedCol = (tileValue - 1) % dimension;
                int numRow = j / dimension;
                int numCol = j % dimension;
                int currentHeuristic = Math.abs(expectedRow - numRow)
                        + Math.abs(expectedCol - numCol);

                heuristicArray[j] = currentHeuristic;
                total += currentHeuristic;
            } else {
                heuristicArray[j] = 0;
            }
        }

        state.setHeuristicArray(heuristicArray);
        // state.printHeuristicArray();
        return total;
    }

    public int calculateSingleHeuristic(State s) {

        //float currentHeuristic = s.getH();
        //System.out.println(" Current H" + currentHeuristic);
        int[] heuristics = s.getHeuristicArray();
        int movedPosition = s.getMovedPosition();
        int movedTile = s.getState()[movedPosition];
        int zeroPosition = s.getZero();
        // Calculates the heuristic for just the tile that was moved
        int expectedRow = (movedTile - 1) / 4;
        int expectedCol = (movedTile - 1) % 4;
        int numRow = movedPosition / 4;
        int numCol = movedPosition % 4;
        int heuristic = (int) s.getH();

        int temp = heuristic - heuristics[zeroPosition];

        int newHeuristicValue = Math.abs(expectedRow - numRow)
                + Math.abs(expectedCol - numCol);
        heuristics[movedPosition] = newHeuristicValue;
        heuristics[zeroPosition] = 0;
        int total1 = temp + newHeuristicValue;
        //for (int i = 0; i < heuristics.length; i++) {
        //    total1 += heuristics[i];
        // }

        s.setHeuristicArray(heuristics);
        return total1;

    }
}
