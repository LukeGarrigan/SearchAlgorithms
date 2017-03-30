/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towers;

import java.util.Arrays;

/**
 *
 * @author Luke
 */
public class State {

    private int[][] state;
    private int g;
    private State previous;

    public State(int[][] state) {
        this.state = state;
    }

    public State getPrevious() {
        return previous;
    }

    public void setPrevious(State previous) {
        this.previous = previous;
    }

    public int[][] getState() {
        return state;
    }

    public void setState(int[][] state) {
        this.state = state;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof State) && Arrays.deepEquals(((State) o).state, state);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }

}
