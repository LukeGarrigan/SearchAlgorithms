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
        System.out.println("");
        state.printHeuristicArray();
        return total;
    }

    @Override
    public float calculateSingleHeuristic(State s) {
        System.out.println("STATE");
        s.printState();
        System.out.println("");
        //float currentHeuristic = s.getH();
        //System.out.println(" Current H" + currentHeuristic);
        int[] heuristics = s.getHeuristicArray();
        int movedPosition = s.getMovedPosition();
        int movedTile = s.getState()[movedPosition];
        int zeroPosition = s.getZero();

        float currentHeuristic = s.getH();

        // Calculates the heuristic for just the tile that was moved
        int expectedRow = (movedTile - 1) / 4;
        int expectedCol = (movedTile - 1) % 4;
        int numRow = movedPosition / 4;
        int numCol = movedPosition % 4;
        int newHeuristicValue = Math.abs(expectedRow - numRow)
                + Math.abs(expectedCol - numCol);

        // this should be zero because this the tile must have moved into the zero
        // position
        //int temp = heuristics[zeroPosition];
        // float before = 0;
        // for (int i = 0; i < heuristics.length; i++) {
        //     before += heuristics[i];
        //  }
        //System.out.println("BEFORE " + before);
        //System.out.println(newHeuristicValue + temp);
        heuristics[movedPosition] = newHeuristicValue;
        heuristics[zeroPosition] = 0;
        // float total = currentHeuristic + newHeuristicValue - temp;

        float total1 = 0;
        for (int i = 0; i < heuristics.length; i++) {
            total1 += heuristics[i];
        }
        s.setHeuristicArray(heuristics);
        s.printHeuristicArray();
        return total1;

    }
}
