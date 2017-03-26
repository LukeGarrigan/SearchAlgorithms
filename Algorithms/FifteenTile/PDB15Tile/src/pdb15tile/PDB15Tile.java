/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdb15tile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Luke
 */
public class PDB15Tile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // TODO code application logic here
        //int[] wholeSet = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
        /*
        CreatePDB create = new CreatePDB();
        int[] pdb1 = new int[]{1, 0, 0, 0, 5, 6, 0, 0, 9, 10, 11, 12, 13, 0, 0, 0};
        int[] storedNums1 = new int[]{1, 5, 6, 9, 10, 11,12, 13};
        int[][][][][][][][] seven = new int[16][16][16][16][16][16][16][16];
        State s = new State(pdb1, "null", 0);
        s.setH(0);
        create.bfs2(seven, s, storedNums1, "PDB_8_1");
        */
        
        CreatePDB create = new CreatePDB();
        int[] pdb1 = new int[]{1, 0, 0, 0, 5, 6, 0, 0, 9, 10, 0, 0, 13, 0, 0, 0};
        int[] storedNums1 = new int[]{1, 5, 6, 9, 10, 13};
        int[][][][][][] six1 = new int[16][16][16][16][16][16];
        State s = new State(pdb1, "null", 0);
        s.setH(0);
        create.bfs2(six1, s, storedNums1, "PDB_6_1");

        int[] pdb2 = {0, 0, 0, 0, 0, 0, 7, 8, 0, 0, 11, 12, 0, 14, 15, 0};
        int[] storedNums2 = new int[]{7, 8, 11, 12, 14, 15};
        int[][][][][][] six2 = new int[16][16][16][16][16][16];
        State ss = new State(pdb2, "null", 0);
        ss.setH(0);
        create.bfs2(six2, ss, storedNums2, "PDB_6_2");
        int[] pdb3 = {0, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] storedNums3 = new int[]{2, 3, 4};
        int[][][] three = new int[16][16][16];
        State sss = new State(pdb3, "null", 0);
        sss.setH(0);
        create.bfs2(three, sss, storedNums3, "PDB_3_1");
        System.out.println("Stored!!");
         
 
        int[] fifteenMoves = new int[]{5, 1, 2, 3, 9, 7, 0, 4, 13, 6, 10, 8, 14, 15, 11, 12};
        //int[] test = {1, 2, 3, 4, 5, 6, 7, 8, 10, 0, 11, 12, 9, 13, 14, 15};
        int[] state = {7, 10, 2, 3, 12, 14, 13, 6, 9, 4, 1, 8, 11, 0, 5, 15};
        int[] sixtyFiveMoves = new int[]{11, 14, 9, 15, 7, 2, 8, 13, 3, 0, 5, 6, 12, 1, 10, 4};
        int[] fourtyMoves = new int[]{6, 7, 0, 11, 1, 5, 10, 4, 14, 13, 15, 2, 9, 8, 3, 12};

        RandomStates r = new RandomStates();
        ArrayList<int[]> testStates = r.RandomizeArray(fourtyMoves, 100);
        IDAStar ida = new IDAStar();
        
       
        for (int[] testState : testStates) {
            int zeroPos = 0;
            for (int i = 0; i < testState.length; i++) {
                if (testState[i] == 0) {
                    zeroPos = i;
                }
            }
            System.out.println(Arrays.toString(testState));
            State st = new State(testState, "null", zeroPos);
            s.setG(0);
            long startTime = System.currentTimeMillis();
            ida.resolve(st);
            long endTime = System.currentTimeMillis();
            float elapsedTime = endTime - startTime;
            System.out.println("Time Taken(s): " + elapsedTime / 1000);
            System.out.println("");

        }
         
    }

    public static class State implements Comparator<State> {

        private int zeroPosition;
        private int[] state;
        private int h;
        private int g;
        private String direction;

        public State(int[] state, String direction, int zeroPosition) {
            this.state = state;
            this.direction = direction;
            this.zeroPosition = zeroPosition;
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

        public String getDirection() {
            return direction;
        }

        public int getZeroPosition() {
            return zeroPosition;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public void setState(int[] state) {
            this.state = state;
        }

        public ArrayList<State> findNeighbours() {

            ArrayList<State> neighbours = new ArrayList<>();
            //for (int i = 0; i < state.length; i++) {
            //if (state[i] == 0) {
            if (zeroPosition % 4 != 0 && !direction.equals("right")) {
                int[] left = new int[16];
                System.arraycopy(state, 0, left, 0, left.length);
                int temp = left[zeroPosition];
                left[zeroPosition] = left[zeroPosition - 1];
                left[zeroPosition - 1] = temp;
                State newState = new State(left, "left", zeroPosition - 1);
                newState.setH(this.h + 1);
                neighbours.add(newState);

            }
            if (zeroPosition % 4 != 3 && !direction.equals("left")) {
                int[] right = new int[16];
                System.arraycopy(state, 0, right, 0, right.length);

                int temp = right[zeroPosition];
                right[zeroPosition] = right[zeroPosition + 1];
                right[zeroPosition + 1] = temp;
                State newState = new State(right, "right", zeroPosition + 1);
                newState.setH(this.h + 1);
                neighbours.add(newState);

            }
            if (zeroPosition > 3 && !direction.equals("down")) {

                int[] up = new int[16];
                System.arraycopy(state, 0, up, 0, up.length);
                int temp = up[zeroPosition];
                up[zeroPosition] = up[zeroPosition - 4];
                up[zeroPosition - 4] = temp;
                State newState = new State(up, "up", zeroPosition - 4);
                newState.setH(this.h + 1);
                neighbours.add(newState);

            }
            if (zeroPosition < 12 && !direction.equals("up")) {
                int[] down = new int[16];
                System.arraycopy(state, 0, down, 0, down.length);
                int temp = down[zeroPosition];
                down[zeroPosition] = down[zeroPosition + 4];
                down[zeroPosition + 4] = temp;
                State newState = new State(down, "down", zeroPosition + 4);
                newState.setH(this.h + 1);
                neighbours.add(newState);

            }
            // }
            return neighbours;
        }

        public ArrayList<State> findNeighbours2() {

            ArrayList<State> neighbours = new ArrayList<>();
            for (int i = 0; i < state.length; i++) {
                if (state[i] == 0) {
                    if (i % 4 != 0 && !direction.equals("right")) {
                        int[] left = new int[16];
                        System.arraycopy(state, 0, left, 0, left.length);
                        int temp = left[i];
                        left[i] = left[i - 1];
                        left[i - 1] = temp;
                        State newState = new State(left, "left", i - 1);
                        newState.setH(this.h + 1);
                        neighbours.add(newState);

                    }
                    if (i % 4 != 3 && !direction.equals("left")) {
                        int[] right = new int[16];
                        System.arraycopy(state, 0, right, 0, right.length);

                        int temp = right[i];
                        right[i] = right[i + 1];
                        right[i + 1] = temp;
                        State newState = new State(right, "right", i + 1);
                        newState.setH(this.h + 1);
                        neighbours.add(newState);

                    }
                    if (i > 3 && !direction.equals("down")) {

                        int[] up = new int[16];
                        System.arraycopy(state, 0, up, 0, up.length);
                        int temp = up[i];
                        up[i] = up[i - 4];
                        up[i - 4] = temp;
                        State newState = new State(up, "up", i - 4);
                        newState.setH(this.h + 1);
                        neighbours.add(newState);

                    }
                    if (i < 12 && !direction.equals("up")) {
                        int[] down = new int[16];
                        System.arraycopy(state, 0, down, 0, down.length);
                        int temp = down[i];
                        down[i] = down[i + 4];
                        down[i + 4] = temp;
                        State newState = new State(down, "down", i + 4);
                        newState.setH(this.h + 1);
                        neighbours.add(newState);

                    }
                }
            }
            // }
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

        @Override
        public int compare(State t, State t1) {
            int first = Math.round(t.getH());
            int second = Math.round(t1.getH());
            return Integer.compare(first, second);
        }

    }

    /*
    public static void bfs(Set<State> emptySet, State s) {
        Queue<State> q = new LinkedList<>();
        q.add(s);
        emptySet.add(s);
        State current;
        while (!q.isEmpty()) {
            current = q.poll();
            for (State neighbour : current.findNeighbours2()) {
                if (!emptySet.contains(neighbour)) {
                    emptySet.add(neighbour);
                    q.add(neighbour);
                }
            }
        }
    }
     */
}
