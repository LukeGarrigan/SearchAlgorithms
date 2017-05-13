/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadthfirstsearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Luke
 */
public class State {

    private int[] state;
    private State previousState;
    private String direction;
    private int zeroPosition;
    private int moves;
    private int nodesExpanded;

    public State(int[] state, State previousState, String direction, int zeroPos, int moves) {
        this.state = state;
        this.previousState = previousState;
        this.direction = direction;
        this.zeroPosition = zeroPos;
        this.moves = moves;
    }

    public int getZeroPosition() {
        return zeroPosition;
    }

    public void resetFields() {
        this.nodesExpanded=0;
        this.zeroPosition=0;
        this.moves=0;
        this.previousState =null;
        this.direction="null";
    }

    public void setNodesExpanded(int nodesExpanded) {
        this.nodesExpanded = nodesExpanded;
    }

    public int getNodesExpanded() {
        return this.nodesExpanded;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public void setState(int[] state) {
        this.state = state;
    }

    public void setZeroPos() {
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                zeroPosition = i;
                break;
            }
        }
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int[] getState() {
        return state;
    }


    public State getPreviousState() {
        return previousState;
    }

    public String getDirection() {
        return direction;
    }

    public ArrayList<State> findNeighbours() {

        ArrayList<State> neighbours = new ArrayList<>();
        if (zeroPosition % 3 != 0 && !direction.equals("right")) {
            int[] left = new int[9];
            System.arraycopy(state, 0, left, 0, left.length);
            int temp = left[zeroPosition];
            left[zeroPosition] = left[zeroPosition - 1];
            left[zeroPosition - 1] = temp;

            State newState = new State(left, this, "left", zeroPosition - 1, this.moves + 1);
            neighbours.add(newState);

        }
        // going right
        if (zeroPosition % 3 != 2 && !direction.equals("left")) {
            int[] right = new int[9];
            System.arraycopy(state, 0, right, 0, right.length);
            int temp = right[zeroPosition];
            right[zeroPosition] = right[zeroPosition + 1];
            right[zeroPosition + 1] = temp;
            State newState = new State(right, this, "right", zeroPosition + 1, this.moves + 1);

            neighbours.add(newState);

        }
        // going up
        if (zeroPosition > 2 && !direction.equals("down")) {
            int[] up = new int[9];
            System.arraycopy(state, 0, up, 0, up.length);
            int temp = up[zeroPosition];
            up[zeroPosition] = up[zeroPosition - 3];
            up[zeroPosition - 3] = temp;
            State newState = new State(up, this, "up", zeroPosition - 3, this.moves + 1);
            neighbours.add(newState);

        }
        // GOING DOWN
        if (zeroPosition < 6 && !direction.equals("up")) {
            int[] down = new int[9];
            System.arraycopy(state, 0, down, 0, down.length);
            int temp = down[zeroPosition];
            down[zeroPosition] = down[zeroPosition + 3];
            down[zeroPosition + 3] = temp;
            State newState = new State(down, this, "down", zeroPosition + 3, this.moves + 1);
            neighbours.add(newState);
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

}
