/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;

/**
 *
 * @author Luke
 */
public class PDB {

    /**
     * @param args the command line arguments
     */
    static Set<State> explored = new HashSet<State>();
    static int[] storedNums = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    static int nine[][][][][][][][][];

    public static void main(String[] args) {
        // TODO code application logic here
        int[] startingState = {1, 2, 3, 4, 5, 6, 7, 8, 0};

        nine = new int[9][9][9][9][9][9][9][9][9];
        State startingStatey = new State(startingState, 0);
        bfs2(nine, startingStatey, storedNums);
        System.out.println("done");

        int[] test = {0, 8, 5, 6, 4, 3, 7, 1, 2};
        RandomStates t = new RandomStates();
        ArrayList<int[]> testStates = t.RandomizeArray(test, 100000);
        long startTime = System.currentTimeMillis();
        for (int[] states : testStates) {
            State pState = new State(states, 0);
            pState.setG(0);
            IDAStar id = new IDAStar();
            id.resolve(pState);
        }
        long stopTime = System.currentTimeMillis();
        float elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime / 1000 + " Seconds");
        // HERE ARE TESTS FOR USING AN ARRAYLIST RATHER THAN SPARSE INDEXING
        /*
        
            
        /*
        Queue<State> q = new LinkedList<>();

        q.add(startingStatey);
        explored.add(startingStatey);
        State current;
        while (!q.isEmpty()) {
            current = q.poll();
            for (State neighbour : current.findNeighbours()) {
                if (!explored.contains(neighbour)) {
                    explored.add(neighbour);
                    q.add(neighbour);
                }
            }
        }
         // Now these values are stored in the explored 
        // want to perform some heuristic search on them
        // Step 1: retrieve the H value for a given configuration
        for (int[] states : testStates) {
            State pState = new State(states, 0);
            for (State s : explored) {
                if (s.equals(pState)) {
                    s.setG(0);
                    IDAStar ida = new IDAStar();
                    long startTime = System.currentTimeMillis();
                    ida.resolve(s);
                    long stopTime = System.currentTimeMillis();
                    float elapsedTime = stopTime - startTime;
                    System.out.println(elapsedTime / 1000 + " Seconds");
                }
            }
        }
         */
    }

    public static int calculate(int[] currentState) {
        int total = 0;
        int[] temp = new int[9];
        for (int i = 0; i < storedNums.length; i++) {
            int value = getPatternPosition(storedNums[i], currentState);
            temp[i] = value;
        }
        total += nine[temp[0]][temp[1]][temp[2]][temp[3]][temp[4]][temp[5]][temp[6]][temp[7]][temp[8]];
        return total;
    }

    public static void bfs2(int[][][][][][][][][] nine, State s, int[] storedNums) {
        Queue<State> q = new LinkedList<>();
        int[] positions = new int[storedNums.length];
        for (int i = 0; i < storedNums.length; i++) {
            positions[i] = getPatternPosition(storedNums[i], s.getState());
        }
        nine[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]][positions[6]][positions[7]][positions[8]] = 0;
        q.add(s);
        State current;
        while (!q.isEmpty()) {
            current = q.poll();
            for (State neighbour : current.findNeighbours()) {
                for (int i = 0; i < storedNums.length; i++) {
                    positions[i] = getPatternPosition(storedNums[i], neighbour.getState());
                }
                if (nine[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]][positions[6]][positions[7]][positions[8]] == 0) {
                    nine[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]][positions[6]][positions[7]][positions[8]] = neighbour.getH();
                    q.add(neighbour);
                }
            }
        }

    }

    public static int getPatternPosition(int value, int[] tilesInPattern) {
        for (int i = 0; i < 9; i++) {
            int tile = tilesInPattern[i];
            if (tile == value) {
                return i;
            }
        }
        return -1; //not found
    }

    public static class State {

        private int[] state;
        int h;
        int g;

        public State(int[] state, int h) {
            this.state = state;
            this.h = h;
        }

        public void setH(int h) {
            this.h = h;
        }

        public int getH() {
            return h;
        }

        public int[] getState() {
            return state;
        }

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
        }

        public ArrayList<State> findNeighbours() {

            ArrayList<State> neighbours = new ArrayList<>();
            for (int i = 0; i < state.length; i++) {
                if (state[i] == 0) {
                    if (i % 3 != 0) {
                        int[] left = new int[9];
                        System.arraycopy(state, 0, left, 0, left.length);
                        int temp = left[i];
                        left[i] = left[i - 1];
                        left[i - 1] = temp;
                        State newState = new State(left, h + 1);
                        neighbours.add(newState);

                    }
                    if (i % 3 != 2) {
                        int[] right = new int[9];
                        System.arraycopy(state, 0, right, 0, right.length);

                        int temp = right[i];
                        right[i] = right[i + 1];
                        right[i + 1] = temp;
                        State newState = new State(right, h + 1);
                        neighbours.add(newState);

                    }
                    if (i > 2) {

                        int[] up = new int[9];
                        System.arraycopy(state, 0, up, 0, up.length);
                        int temp = up[i];
                        up[i] = up[i - 3];
                        up[i - 3] = temp;
                        State newState = new State(up, h + 1);
                        neighbours.add(newState);

                    }
                    if (i < 6) {
                        int[] down = new int[9];
                        System.arraycopy(state, 0, down, 0, down.length);
                        int temp = down[i];
                        down[i] = down[i + 3];
                        down[i + 3] = temp;
                        State newState = new State(down, h + 1);
                        neighbours.add(newState);

                    }

                }
            }
            return neighbours;
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof State) && Arrays.equals(((State) o).state, state);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(state);
        }
    }
}
