/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdb15tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
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
    static Set<State> explored1 = new HashSet<State>();
    static Set<State> explored2 = new HashSet<State>();
    static Set<State> explored3 = new HashSet<State>();
    static int[] stored1 = {1, 5, 6, 9, 10, 13};
    static int[] stored2 = {7, 8, 11, 12, 14, 15};
    static int[] stored3 = {2, 3, 4};

    public static void main(String[] args) {
        // TODO code application logic here
        //int[] wholeSet = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
        int[] pdb1 = {1, 0, 0, 0, 5, 6, 0, 0, 9, 10, 0, 0, 13, 0, 0, 0};
        int[] pdb2 = {0, 0, 0, 0, 0, 0, 7, 8, 0, 0, 11, 12, 0, 14, 15, 0};
        int[] pdb3 = {0, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        State startingState1 = new State(pdb1, 0);
        State startingState2 = new State(pdb2, 0);
        State startingState3 = new State(pdb3, 0);
        // bfs(explored, startingState1);

        bfs(explored1, startingState1);
        bfs(explored2, startingState2);
        bfs(explored3, startingState3);

        int amountStored = explored1.size() + explored2.size() + explored3.size();
        System.out.println("Amount stored " + amountStored);

        float total1 = 0, total2 = 0, total3 = 0;

        int[] blar = {7, 10, 2, 3, 12, 14, 13, 6, 9, 4, 1, 8, 11, 0, 5, 15};
        /////////////////////////////////////////////////////////////////////
        //////////////////// For the first set//////////////////////////////
        /////////////////////////////////////////////////////////////////////
        int[] empty1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < stored1.length; i++) {
            int value = getPatternPosition(stored1[i], blar);
            empty1[value] = stored1[i];
            //System.out.println(value);
        }

        for (State x : explored1) {
            if (Arrays.equals(x.getState(), empty1)) {
                total1 = x.getH();
            }
        }

        /////////////////////////////////////////////////////////////////////
        //////////////////// For the second set//////////////////////////////
        /////////////////////////////////////////////////////////////////////
        int[] empty2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < stored2.length; i++) {
            int value = getPatternPosition(stored2[i], blar);
            empty2[value] = stored2[i];

        }

        for (State x : explored2) {
            if (Arrays.equals(x.getState(), empty2)) {
                total2 = x.getH();
            }
        }

        /////////////////////////////////////////////////////////////////////
        //////////////////// For the third set//////////////////////////////
        /////////////////////////////////////////////////////////////////////
        int[] empty3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < stored3.length; i++) {
            int value = getPatternPosition(stored3[i], blar);
            empty3[value] = stored3[i];
            //System.out.println(value);
        }

        for (State x : explored3) {
            if (Arrays.equals(x.getState(), empty3)) {
                total3 = x.getH();
            }
        }

        IDAStar ida = new IDAStar();
        State s = new State(blar, total1 + total2 + total3);
        s.setG(0);
        ida.resolve(s);
    }

    public static int getPatternPosition(int value, int[] tilesInPattern) {
        for (int i = 0; i < 16; i++) {
            int tile = tilesInPattern[i];
            if (tile == value) {
                return i;
            }
        }
        return -1; //not found
    }

    public static void bfs(Set<State> emptySet, State s) {
        Queue<State> q = new LinkedList<>();
        q.add(s);
        emptySet.add(s);
        State current;
        while (!q.isEmpty()) {
            current = q.poll();
            for (State neighbour : current.findNeighbours()) {
                if (!emptySet.contains(neighbour)) {
                    emptySet.add(neighbour);
                    q.add(neighbour);

                }
            }
        }
    }

    public static class State {

        private int[] state;
        private float h;
        private float g;

        public State(int[] state, float h) {
            this.state = state;
            this.h = h;
        }

        public void setH(float h) {
            this.h = h;
        }

        public float getH() {
            return h;
        }

        public int[] getState() {
            return state;
        }

        public float getG() {
            return g;
        }

        public void setG(float g) {
            this.g = g;
        }

        public ArrayList<State> findNeighbours() {

            ArrayList<State> neighbours = new ArrayList<>();
            for (int i = 0; i < state.length; i++) {
                if (state[i] == 0) {
                    if (i % 4 != 0) {
                        int[] left = new int[16];
                        System.arraycopy(state, 0, left, 0, left.length);
                        int temp = left[i];
                        left[i] = left[i - 1];
                        left[i - 1] = temp;
                        State newState = new State(left, h + 1);
                        neighbours.add(newState);

                    }
                    if (i % 4 != 3) {
                        int[] right = new int[16];
                        System.arraycopy(state, 0, right, 0, right.length);

                        int temp = right[i];
                        right[i] = right[i + 1];
                        right[i + 1] = temp;
                        State newState = new State(right, h + 1);
                        neighbours.add(newState);

                    }
                    if (i > 3) {

                        int[] up = new int[16];
                        System.arraycopy(state, 0, up, 0, up.length);
                        int temp = up[i];
                        up[i] = up[i - 4];
                        up[i - 4] = temp;
                        State newState = new State(up, h + 1);
                        neighbours.add(newState);

                    }
                    if (i < 12) {
                        int[] down = new int[16];
                        System.arraycopy(state, 0, down, 0, down.length);
                        int temp = down[i];
                        down[i] = down[i + 4];
                        down[i + 4] = temp;
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
