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

/**
 *
 * @author Luke
 */
public class PDB {

    /**
     * @param args the command line arguments
     */
    static Set<State> explored = new HashSet<State>();

    public static void main(String[] args) {
        // TODO code application logic here
        int[] startingState = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        State startingStatey = new State(startingState, 0);
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
           System.out.println(explored.size());
        // Now these values are stored in the explored 
        // want to perform some heuristic search on them

        // Step 1: retrieve the H value for a given configuration
        int[] test = {0, 8, 5, 6, 4, 3, 7, 1, 2};
        State pState = new State(test, 0);
        for (State s : explored) {
            if (s.equals(pState)) {
                System.out.println(Arrays.toString(s.getState()) + " " + s.getH());
                s.setG(0);
                IDAStar ida = new IDAStar();
               // ida.depthFirstSearch(s, s.getH());
                ida.resolve(s);
            }
        }

    }

    public static class State {

        private int[] state;
        float h;
        float g;
        
        public State(int[] state, float h) {
            this.state = state;
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
