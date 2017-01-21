/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarflattened;

/**
 *
 * @author Luke
 */
public class Direction {

    State moveLeft(State state, int pos) {
        int[] puzzle = state.getState();
        int[] left = new int[9];
        System.arraycopy(puzzle, 0, left, 0, left.length);
        if (pos % 3 != 1) {
            int temp = left[pos - 1];
            left[pos] = temp;
            left[pos - 1] = 0;
        }
        State newState = new State(left, 0, 0, state, "left");
        return newState;
    }

    State moveRight(State state, int pos) {
        int[] puzzle = state.getState();
        int[] right = new int[9];
        System.arraycopy(puzzle, 0, right, 0, right.length);
        if (pos % 3 != 0) {
            int temp = right[pos + 1];
            right[pos] = temp;
            right[pos + 1] = 0;
        }
        State newState = new State(right, 0, 0, state, "left");
        return newState;
    }

    State moveUp(State state, int pos) {
        int[] puzzle = state.getState();
        int[] up = new int[9];
        System.arraycopy(puzzle, 0, up, 0, up.length);
        if (pos > 3) {
            int temp = up[pos - 3];
            up[pos] = temp;
            up[pos - 3] = 0;
        }
        State newState = new State(up, 0, 0, state, "left");
        return newState;
    }

    State moveDown(State state, int pos) {
        int[] puzzle = state.getState();
        int[] down = new int[9];
        System.arraycopy(puzzle, 0, down, 0, down.length);
        if (pos < 6) {
            int temp = down[pos + 3];
            down[pos] = temp;
            down[pos + 3] = 0;
        }
        State newState = new State(down, 0, 0, state, "left");
        return newState;
    }

}
