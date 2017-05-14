/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slidingtileproject.Experiments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import slidingtileproject.State;

/**
 *
 * @author Luke
 */
public class RandomStates {

    public ArrayList<State> RandomizeArray(int[] a, int amount) {
        int[] array = new int[a.length];
        System.arraycopy(a, 0, array, 0, array.length);

        // Random number generator			
        ArrayList<State> testValues = new ArrayList<>();
        while (testValues.size() < amount) {
            for (int i = 0; i < array.length; i++) {
                Random rgen = new Random();
                int randomPosition = rgen.nextInt(array.length);

                int temp = array[i];
                array[i] = array[randomPosition];
                array[randomPosition] = temp;
            }
            if (isSolvable2(array)) {

                int[] t = new int[array.length];
                System.arraycopy(array, 0, t, 0, t.length);
                int zeroPos = 0;
                for (int i = 0; i < t.length; i++) {
                    if (t[i] == 0) {
                        zeroPos = i;
                    }
                }
                State s = new State(t, 0, 0, null, "null");
                s.setZeroPosition(zeroPos);

                testValues.add(s);
            }
        }
        return testValues;
    }

    private boolean isSolvable2(int[] puzzle) {
        boolean parity = true;
        int gridWidth = (int) Math.sqrt(puzzle.length);
        boolean blankRowEven = true; // the row with the blank tile

        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] == 0) { // the blank tile
                blankRowEven = (i / gridWidth) % 2 == 0;
                continue;
            }
            for (int j = i + 1; j < puzzle.length; j++) {
                if (puzzle[i] > puzzle[j] && puzzle[j] != 0) {
                    parity = !parity;
                }
            }
        }

        // even grid with blank on even row; counting from top
        if (gridWidth % 2 == 0 && blankRowEven) {
            return !parity;
        }
        return parity;
    }

}
