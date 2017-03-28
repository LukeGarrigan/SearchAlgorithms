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

        
        CreatePDB create = new CreatePDB();
        byte[] pdb1 = new byte[]{1, 2, 3, 4, 0, 6, 7, 8, 0, 0, 0, 0, 0, 0, 0, 0};
        byte[] storedNums1 = new byte[]{1, 2, 3, 4, 6, 7, 8};
        byte[][][][][][][] seven = new byte[16][16][16][16][16][16][16];
        State s = new State(pdb1, "null", 0);
        s.setH(0);
        create.bfs2(seven, s, storedNums1, "PDB_7_1");

        byte[] pdb2 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 9, 10, 11, 12, 13, 14, 15, 0};
        byte[] storedNums2 = new byte[]{9, 10, 11, 12, 13, 14, 15};
        byte[][][][][][][] seven2 = new byte[16][16][16][16][16][16][16];
        State ss = new State(pdb2, "null", 0);
        s.setH(0);
        create.bfs2(seven2, ss, storedNums2, "PDB_7_2");

        byte[] pdb3 = new byte[]{0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        byte[] storedNums3 = new byte[]{5};
        byte[] one = new byte[16];
        State sss = new State(pdb3, "null", 0);
        s.setH(0);
        create.bfs2(one, sss, storedNums3, "PDB_1_1");
        

        /*
        CreatePDB create = new CreatePDB();
        byte[] pdb1 = new byte[]{1, 0, 0, 0, 5, 6, 0, 0, 9, 10, 0, 0, 13, 0, 0, 0};
        byte[] storedNums1 = new byte[]{1, 5, 6, 9, 10, 13};
        byte[][][][][][] six1 = new byte[16][16][16][16][16][16];
        byte zero = 0;
        State s = new State(pdb1, "null", 0);
        s.setH(zero);
        create.bfs2(six1, s, storedNums1, "PDB_6_1");

        byte[] pdb2 = {0, 0, 0, 0, 0, 0, 7, 8, 0, 0, 11, 12, 0, 14, 15, 0};
        byte[] storedNums2 = new byte[]{7, 8, 11, 12, 14, 15};
        byte[][][][][][] six2 = new byte[16][16][16][16][16][16];
        State ss = new State(pdb2, "null", 0);
        ss.setH(zero);
        create.bfs2(six2, ss, storedNums2, "PDB_6_2");
         
         
       // CreatePDB create = new CreatePDB();
        byte[] pdb3 = {0, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        byte[] storedNums3 = new byte[]{2, 3, 4};
        byte[][][] three = new byte[16][16][16];
        State sss = new State(pdb3, "null", 0);
        sss.setH(zero);
        create.bfs2(three, sss, storedNums3, "PDB_3_1");
        System.out.println("Stored!!");
        */
        
        byte[] fourtyMoves = new byte[]{6, 7, 0, 11, 1, 5, 10, 4, 14, 13, 15, 2, 9, 8, 3, 12};
        RandomStates r = new RandomStates();
        ArrayList<byte[]> testStates = r.RandomizeArray(fourtyMoves, 100);
   
        
        IDAStar ida = new IDAStar();

        for (byte[] testState : testStates) {
            byte zeroPos = 0;
            for (byte i = 0; i < testState.length; i++) {
                if (testState[i] == 0) {
                    zeroPos = i;
                    System.out.println(zeroPos);
                }
            }
            System.out.println(Arrays.toString(testState));
            State st = new State(testState, "null", zeroPos);
            st.setG(0);
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
        private byte[] state;
        private byte h;
        private int g;
        private String direction;
        
        public State(byte[] state, String direction, int zeroPosition) {
            this.state = state;
            this.direction = direction;
            this.zeroPosition = zeroPosition;
        }

        public void setH(int h) {
            this.h = (byte) h;
        }

        public byte getH() {
            return h;
        }

        public byte[] getState() {
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

        public void setState(byte[] state) {
            this.state = state;
        }

        public ArrayList<State> findNeighbours() {

            ArrayList<State> neighbours = new ArrayList<>();
            //for (int i = 0; i < state.length; i++) {
            //if (state[i] == 0) {
            if (zeroPosition % 4 != 0 && !direction.equals("right")) {
                byte[] left = new byte[16];
                System.arraycopy(state, 0, left, 0, left.length);
                byte temp = left[zeroPosition];
                left[zeroPosition] = left[zeroPosition - 1];
                left[zeroPosition - 1] = temp;
                State newState = new State(left, "left", zeroPosition - 1);
                neighbours.add(newState);

            }
            if (zeroPosition % 4 != 3 && !direction.equals("left")) {
                byte[] right = new byte[16];
                System.arraycopy(state, 0, right, 0, right.length);

                byte temp = right[zeroPosition];
                right[zeroPosition] = right[zeroPosition + 1];
                right[zeroPosition + 1] = temp;
                State newState = new State(right, "right", zeroPosition + 1);
                neighbours.add(newState);

            }
            if (zeroPosition > 3 && !direction.equals("down")) {

                byte[] up = new byte[16];
                System.arraycopy(state, 0, up, 0, up.length);
                byte temp = up[zeroPosition];
                up[zeroPosition] = up[zeroPosition - 4];
                up[zeroPosition - 4] = temp;
                State newState = new State(up, "up", zeroPosition - 4);
                neighbours.add(newState);

            }
            if (zeroPosition < 12 && !direction.equals("up")) {
                byte[] down = new byte[16];
                System.arraycopy(state, 0, down, 0, down.length);
                byte temp = down[zeroPosition];
                down[zeroPosition] = down[zeroPosition + 4];
                down[zeroPosition + 4] = temp;
                State newState = new State(down, "down", zeroPosition + 4);
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
                        byte[] left = new byte[16];
                        System.arraycopy(state, 0, left, 0, left.length);
                        byte temp = left[i];
                        left[i] = left[i - 1];
                        left[i - 1] = temp;
                        State newState = new State(left, "left", i - 1);
                        newState.setH(this.h +1);
                        neighbours.add(newState);

                    }
                    if (i % 4 != 3 && !direction.equals("left")) {
                        byte[] right = new byte[16];
                        System.arraycopy(state, 0, right, 0, right.length);

                        byte temp = right[i];
                        right[i] = right[i + 1];
                        right[i + 1] = temp;
                        State newState = new State(right, "right", i + 1);
                        newState.setH(this.h +1);
                        neighbours.add(newState);

                    }
                    if (i > 3 && !direction.equals("down")) {

                        byte[] up = new byte[16];
                        System.arraycopy(state, 0, up, 0, up.length);
                        byte temp = up[i];
                        up[i] = up[i - 4];
                        up[i - 4] = temp;
                        State newState = new State(up, "up", i - 4);
                        newState.setH(this.h +1);
                        neighbours.add(newState);

                    }
                    if (i < 12 && !direction.equals("up")) {
                        byte[] down = new byte[16];
                        System.arraycopy(state, 0, down, 0, down.length);
                        byte temp = down[i];
                        down[i] = down[i + 4];
                        down[i + 4] = temp;
                        State newState = new State(down, "down", i + 4);
                        newState.setH(this.h +1);
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
