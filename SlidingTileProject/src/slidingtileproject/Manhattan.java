/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slidingtileproject;

import java.util.Arrays;

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
        int[] heuristicArray = new int[puzz.length];
        for (int j = 0; j < puzz.length; j++) {
            int tileValue = puzz[j];
            if (tileValue != 0) {
                int expectedRow = (tileValue - 1) / 4;
                int expectedCol = (tileValue - 1) % 4;
                int numRow = j / 4;
                int numCol = j % 4;
                int currentHeuristic = Math.abs(expectedRow - numRow)
                        + Math.abs(expectedCol - numCol);

                heuristicArray[j] = currentHeuristic;
                total += currentHeuristic;
            } else {
                heuristicArray[j] = 0;
            }
        }
        state.setHeuristicArray(heuristicArray);
        state.setH(total);
        System.out.println("WHOLE : " + Arrays.toString(heuristicArray) + " TOTAL " + total);
        return total;
    }

    @Override
    public float calculateSingleHeuristic(State s) {
        int currentHeuristic = (int) s.getH();
        System.out.println("C" + currentHeuristic);
        int[] heuristics = s.getHeuristicArray();
        int movedPosition = s.getMovedPosition();
        int movedTile = s.getState()[movedPosition];

        // Calculates the heuristic for just the tile that was moved
        int expectedRow = (movedTile - 1) / 4;
        int expectedCol = (movedTile - 1) % 4;
        int numRow = movedPosition / 4;
        int numCol = movedPosition % 4;
        int newHeuristicValue = Math.abs(expectedRow - numRow)
                + Math.abs(expectedCol - numCol);

       
        // Taking away the old heuristic value that the zero has moved to
        int temp = currentHeuristic - heuristics[s.getZero()];
       // heuristics[s.getZero()] = 0;

        heuristics[movedPosition] = newHeuristicValue;
        int total = temp + newHeuristicValue;

        s.setHeuristicArray(heuristics);
        s.setH(total);
        System.out.println("SINGLE : " + Arrays.toString(heuristics) + " TOTAL " + total);
        return total;
    }
}
