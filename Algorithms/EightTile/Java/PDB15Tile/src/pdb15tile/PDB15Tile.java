/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdb15tile;

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
    static Set<State> explored1 = new HashSet<>();
    static Set<State> explored2 = new HashSet<>();
    static Set<State> explored3 = new HashSet<>();

    static List<State> sortedList1;
    static List<State> sortedList2;
    static List<State> sortedList3;
    static int[] stored1 = {1, 5, 6, 9, 10, 13};
    static int[] stored2 = {7, 8, 11, 12, 14, 15};
    static int[] stored3 = {2, 3, 4};

    public static void main(String[] args) {
        // TODO code application logic here
        //int[] wholeSet = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
        int[] pdb1 = {1, 0, 0, 0, 5, 6, 0, 0, 9, 10, 0, 0, 13, 0, 0, 0};
        int[] pdb2 = {0, 0, 0, 0, 0, 0, 7, 8, 0, 0, 11, 12, 0, 14, 15, 0};
        int[] pdb3 = {0, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        State startingState1 = new State(pdb1, "null", 15);
        State startingState2 = new State(pdb2, "null", 15);
        State startingState3 = new State(pdb3, "null", 15);
        bfs(explored1, startingState1);
        System.out.println(explored1.size());
        bfs(explored2, startingState2);
        System.out.println(explored2.size());
        bfs(explored3, startingState3);
        System.out.println(explored3.size());

        // for (State x : explored3) {
        //    System.out.println(Arrays.toString(x.getState()) + "  zeroPos: " + x.getZeroPosition());
        //}
        sortedList1 = new ArrayList(explored1);
        sortedList2 = new ArrayList(explored2);
        sortedList3 = new ArrayList(explored3);
        Collections.sort(sortedList1,
                (o1, o2) -> Integer.compare(Math.round(o1.getH()), Math.round(o2.getH())));
        Collections.sort(sortedList2,
                (o1, o2) -> Integer.compare(Math.round(o1.getH()), Math.round(o2.getH())));

        Collections.sort(sortedList3,
                (o1, o2) -> Integer.compare(Math.round(o1.getH()), Math.round(o2.getH())));

        // timer 
        System.out.println("Start");
        int[] fifteenMoves = new int[]{5, 1, 2, 3, 9, 7, 0, 4, 13, 6, 10, 8, 14, 15, 11, 12};
        //int[] test = {1, 2, 3, 4, 5, 6, 7, 8, 10, 0, 11, 12, 9, 13, 14, 15};
        int[] state = {7, 10, 2, 3, 12, 14, 13, 6, 9, 4, 1, 8, 11, 0, 5, 15};
        int[] sixtyFiveMoves = new int[]{11, 14, 9, 15, 7, 2, 8, 13, 3, 0, 5, 6, 12, 1, 10, 4};
        float h = getPDBHeuristic(state);
        IDAStar ida = new IDAStar();
        State s = new State(state, "null", 6);

        s.setH(h);
        s.setG(0);
        ida.resolve(s);

    }

    public static float getPDBHeuristic(int[] currentState) {
        float total = 0;
        /////////////////////////////////////////////////////////////////////
        //////////////////// For the first set//////////////////////////////
        /////////////////////////////////////////////////////////////////////
        int[] empty1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < stored1.length; i++) {
            int value = getPatternPosition(stored1[i], currentState);
            empty1[value] = stored1[i];
            //System.out.println(value);g
        }

        for (State x : sortedList1) {
            if (Arrays.equals(x.getState(), empty1)) {
                total += x.getH();
                break;
            }
        }

        /////////////////////////////////////////////////////////////////////
        //////////////////// For the second set//////////////////////////////
        /////////////////////////////////////////////////////////////////////
        int[] empty2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < stored2.length; i++) {
            int value = getPatternPosition(stored2[i], currentState);
            empty2[value] = stored2[i];

        }

        for (State x : sortedList2) {
            if (Arrays.equals(x.getState(), empty2)) {
                total += x.getH();
                break;
            }
        }

        /////////////////////////////////////////////////////////////////////
        //////////////////// For the third set//////////////////////////////
        /////////////////////////////////////////////////////////////////////
        int[] empty3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < stored3.length; i++) {
            int value = getPatternPosition(stored3[i], currentState);
            empty3[value] = stored3[i];
            //System.out.println(value);
        }

        for (State x : sortedList3) {
            if (Arrays.equals(x.getState(), empty3)) {
                total += x.getH();
                break;
            }
        }
        return total;

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
            for (State neighbour : current.findNeighbours2()) {
                if (!emptySet.contains(neighbour)) {
                    emptySet.add(neighbour);
                    q.add(neighbour);
                }
            }
        }
    }

    public static class State implements Comparator<State> {

        private int zeroPosition;
        private int[] state;
        private float h;
        private float g;
        private String direction;

        public State(int[] state, String direction, int zeroPosition) {
            this.state = state;
            this.direction = direction;
            this.zeroPosition = zeroPosition;
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

}
