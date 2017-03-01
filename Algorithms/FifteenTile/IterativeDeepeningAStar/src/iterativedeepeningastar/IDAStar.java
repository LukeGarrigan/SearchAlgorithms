/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iterativedeepeningastar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

/**
 *
 * @author Luke
 */
public class IDAStar {

    static int[] GOAL = new int[]{1, 2, 3, 4, 5, 6, 7, 8,
        9, 10, 11, 12, 13, 14, 15, 0};

    private float nextCostBound;
    LinearConflicts l = new LinearConflicts();
    // gets created when IDAStar is invoked
    Timer t = new Timer();
    
    static int statesExpanded = 0;

    public State IDAStar(State start) {
        // should already have heuristic because the initial state
        nextCostBound = start.getH();
        State solution = null;

        while (solution == null) {
            System.out.println(nextCostBound);
            float currentCostBound = nextCostBound;
            solution = depthFirstSearch(start, currentCostBound);
            nextCostBound += 2;
        }
        return solution;
    }

    public State depthFirstSearch(State current, float currentCostBound) {
        statesExpanded++;
        if (Arrays.equals(current.getState(), GOAL)) {
            return current;
        }

        for (State next : findNeighbours(current)) {
            next.setG(current.getG() + 1);
            Manhattan m = new Manhattan();
            float li = l.linearConflict(next.getState());
            next.setH(li);
            float value = next.getG() + next.getH();

            if (value <= currentCostBound) {
                State result = depthFirstSearch(next, currentCostBound);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

 

    public ArrayList<State> findNeighbours(State currentState) {

        int[] state = currentState.getState();
        ArrayList<State> neighbours = new ArrayList<>();
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                if (i % 4 != 0 && !currentState.getDirection().equals("right")) {
                    int[] left = new int[16];
                    System.arraycopy(state, 0, left, 0, left.length);
                    int temp = left[i];
                    left[i] = left[i - 1];
                    left[i - 1] = temp;
                    State newState = new State(left, 0, 0, currentState, "left");
                    neighbours.add(newState);

                }
                if (i % 4 != 3 && !currentState.getDirection().equals("left")) {
                    int[] right = new int[16];
                    System.arraycopy(state, 0, right, 0, right.length);

                    int temp = right[i];
                    right[i] = right[i + 1];
                    right[i + 1] = temp;
                    State newState = new State(right, 0, 0, currentState, "right");
                    neighbours.add(newState);

                }
                if (i > 3 && !currentState.getDirection().equals("down")) {

                    int[] up = new int[16];
                    System.arraycopy(state, 0, up, 0, up.length);
                    int temp = up[i];
                    up[i] = up[i - 4];
                    up[i - 4] = temp;
                    State newState = new State(up, 0, 0, currentState, "up");
                    neighbours.add(newState);

                }
                if (i < 12 && !currentState.getDirection().equals("up")) {
                    int[] down = new int[16];
                    System.arraycopy(state, 0, down, 0, down.length);
                    int temp = down[i];
                    down[i] = down[i + 4];
                    down[i + 4] = temp;
                    State newState = new State(down, 0, 0, currentState, "down");
                    neighbours.add(newState);

                }

            }
        }
        return neighbours;
    }

}
