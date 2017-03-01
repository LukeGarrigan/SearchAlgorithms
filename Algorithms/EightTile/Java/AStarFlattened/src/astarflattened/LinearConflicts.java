/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarflattened;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


import java.util.ArrayList;

/**
 *
 * @author Luke
 */
public class LinearConflicts extends Manhattan {

    public int linearConflict(int[] s) {
        int heuristic = super.manhattan(s);
        int n =heuristic;
      
        
        heuristic += linearVerticalConflict(s);
        heuristic += linearHorizontalConflict(s);
        return heuristic;

    }

    private int linearVerticalConflict(int[] state) {
        int dimension = 3;
        int linearConflict = 0;
        int[][] newState = {
            {state[0], state[1], state[2]},
            {state[3], state[4], state[5]},
            {state[6], state[7], state[8]}

        };
        for (int row = 0; row < dimension; row++) {
            int max = -1;
            for (int column = 0; column < dimension; column++) {
                int cellValue = newState[row][column];
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

    private int linearHorizontalConflict(int[] state) {

        int dimension = 3;
        int linearConflict = 0;
        int[][] newState = {
            {state[0], state[1], state[2]},
            {state[3], state[4], state[5]},
            {state[6], state[7], state[8]}

        };
        for (int column = 0; column < dimension; column++) {
            int max = -1;
            for (int row = 0; row < dimension; row++) {
                int cellValue = newState[row][column];
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
