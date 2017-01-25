/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iterativedeepeningastar;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Luke
 */
public class IterativeDeepeningAStar {

    /**
     * @param args the command line arguments
     */
    static int[] GOAL = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};
    static ArrayList<State> SEENSTATES = new ArrayList<>();

    public static void main(String[] args) {
        // worlds most difficult puzzle takes 31 moves
        int[] problemState = new int[]{8, 6, 7, 2, 5, 4, 3, 0, 1};
        int[] puzzle = new int[]{2, 3, 5, 1, 4, 6, 0, 7, 8};
        // this one takes 12 moves
        int[] twelve = new int[]{1, 2, 3, 6, 0, 8, 7, 5, 4};
        // this one takes sixteen moves
        int[] sixteen = new int[]{8, 4, 2, 5, 1, 3, 7, 6, 0};
        // this one takes 18 moves
        int[] eighteen = new int[]{3, 5, 0, 7, 1, 2, 8, 6, 4};
        // this one takes 20 moves
        int[] twenty = new int[]{5, 3, 0, 8, 2, 1, 4, 6, 7};
        int[] twentyTwo = new int[]{3, 5, 7, 2, 8, 5, 0, 1, 4};

        float man = manhattan(twentyTwo); // manhattan distance of the current state
        State initialState = new State(twentyTwo, 0, man, null, "null");
        IDAStar(initialState);
    }

    public static void IDAStar(State currentState) {
        long startTime = System.nanoTime();
        float threshold = currentState.getH();

        while (true) {

            State t = search(currentState, 0, threshold);
            SEENSTATES.add(t);
            if (Arrays.equals(t.getState(), GOAL)) {
                float duration = (System.nanoTime() - startTime) / 1000000;
                System.out.println("Solved " + duration + " ms ");
                int count = 0;
                while (t.getPrevious() != null) {
                    count++;
                    System.out.println(Arrays.toString(t.getState()));
                    System.out.print(t.getDirection() + " ");
                    t = t.getPrevious();
                }
                System.out.println("\n" + count + " move(s)");
                break;
            }
            // System.out.println(Arrays.toString(t.getState()));

            // assign a new threshold
            threshold = t.getF();

        }
    }

    public static State search(State currentState, float g, float threshold) {
        // setting the heuristic value for this state
        //  System.out.println(Arrays.toString(currentState.getState()));

        System.out.println(Arrays.toString(currentState.getState()) + " " + threshold);
        float h = manhattan(currentState.getState());
        currentState.setH(h);
        currentState.setG(g);

        // calculating the F value 
        float f = currentState.getG() + currentState.getH();
        currentState.setF(f);
        // System.out.println("F =: "+currentState.getF()+" G=: "+ currentState.getG() +" H =: "+ currentState.getH());
        if (f > threshold) {
            //System.out.println("REDOING");
            // returns the state closest to the goal state
            // this makes it deepen iteratively 
            return currentState;

        }
        // check whether its a goal as f could be less than threshold but a goal
        if (Arrays.equals(currentState.getState(), GOAL)) {

            System.out.println("FOUND");
            return currentState;

        }
        // finds all the neighbours for the current node 
        // and then stores them into an arraylist 
        currentState.findNeighbours();
        ArrayList<State> successors = currentState.getNeighbours();
        // creates a minimum f value, and a State object which 
        // will be used for the state with the lowest f value
        float min = 500000;
        State minState = null;

        for (State neighbour : successors) {
    
                State t = search(neighbour, g + 1, threshold);
                if (Arrays.equals(t.getState(), GOAL)) {
                    return neighbour;
                }
                if (t.getF() < min) {
                    min = t.getF();
                    minState = t;
                }
            
        }

        return minState;
    }

    public static float manhattan(int[] puzz) {
        //System.out.println(Arrays.toString(puzz));
        int total = 0;
        for (int i = 1; i < puzz.length; i++) {
            int expectedRow = (i - 1) / 3;
            int expectedCol = (i - 1) % 3;
            int num = 0;
            for (int j = 0; j < puzz.length; j++) {
                if (puzz[j] == i) {
                    num = j + 1;
                    break;
                }
            }
            int numRow = (num - 1) / 3;
            int numCol = (num - 1) % 3;
            total += Math.abs(expectedRow - numRow)
                    + Math.abs(expectedCol - numCol);
        }
        return total;
    }

}
