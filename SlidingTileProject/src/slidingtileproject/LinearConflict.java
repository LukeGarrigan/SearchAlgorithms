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
public class LinearConflict extends Manhattan {

    @Override
    public int calculateHeuristic(State puzz) {
        int heuristic = 0;

        // if there is a previous then we don't need to recalculate the 
        // entire heuristics for each element, so there already is an existing
        // array which contains the heuristic estimates
        if (puzz.getPrevious() != null) {
            heuristic += super.calculateSingleHeuristic(puzz);
        } else {

            heuristic += super.calculateHeuristic(puzz);
        }
        heuristic += linearVerticalConflict(puzz);
        heuristic += linearHorizontalConflict(puzz);

        return heuristic;
    }

    private int linearVerticalConflict(State s) {
        int state[] = s.getState();
        int dimension = 4;
        int linearConflict = 0;
        int count = 0;
        for (int row = 0; row < dimension; row++) {
            int max = -1;
            for (int column = 0; column < dimension; column++) {
                int cellValue = state[count];
                count++;
                //int cellValue = newState[row][column];
                //is tile in its goal row ?
                if (cellValue != 0 && (cellValue - 1) / dimension == row) {
                    if (cellValue > max) {
                        max = cellValue;
                    } else {
                        //linear conflict, one tile must move up or down to
                        // allow the other to pass by and then back up
                        //add two moves to the manhattan distance
                        linearConflict += 2;
                    }
                }

            }

        }
        return linearConflict;
    }

    private int linearHorizontalConflict(State s) {
        int[] state = s.getState();
        int dimension = 4;
        int linearConflict = 0;
        int count = 0;
        for (int column = 0; column < dimension; column++) {
            int max = -1;
            for (int row = 0; row < dimension; row++) {
                int cellValue = state[count];
                count++;
                //is tile in its goal row ?
                if (cellValue != 0 && cellValue % dimension == column + 1) {
                    if (cellValue > max) {
                        max = cellValue;
                    } else {
                        //linear conflict, one tile must move left or right to allow the other to pass by and then back up
                        //add two moves to the manhattan distance
                        linearConflict += 2;
                    }
                }

            }

        }
        return linearConflict;
    }

}
