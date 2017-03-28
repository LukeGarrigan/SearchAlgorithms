/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdb15tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Luke
 */
public class RandomStates {

    public ArrayList<byte[]> RandomizeArray(byte[] array, int amount) {
        // Random number generator			
        ArrayList<byte[]> testValues = new ArrayList<>();
        while (testValues.size() < amount) {
            for (int i = 0; i < array.length; i++) {
                Random rgen = new Random();
                int randomPosition = rgen.nextInt(array.length);

                byte temp = array[i];
                array[i] = array[randomPosition];
                array[randomPosition] = temp;
            }
            if (isSolvable(array)) {
                byte[] t = array.clone();
                testValues.add(t);
            }
        }
        return testValues;
    }

    public boolean isSolvable(byte[] puzzle) {
        byte parity = 0;
        int gridWidth = (int) Math.sqrt(puzzle.length);
        byte row = 0; // the current row we are on
        byte blankRow = 0; // the row with the blank tile

        for (int i = 0; i < puzzle.length; i++) {
            if (i % gridWidth == 0) { // advance to next row
                row++;
            }
            if (puzzle[i] == 0) { // the blank tile
                blankRow = row; // save the row on which encountered
                continue;
            }
            for (int j = i + 1; j < puzzle.length; j++) {
                if (puzzle[i] > puzzle[j] && puzzle[j] != 0) {
                    parity++;
                }
            }
        }

        if (gridWidth % 2 == 0) { // even grid
            if (blankRow % 2 == 0) { // blank on odd row; counting from bottom
                return parity % 2 == 0;
            } else { // blank on even row; counting from bottom
                return parity % 2 != 0;
            }
        } else { // odd grid
            return parity % 2 == 0;
        }
    }

}
