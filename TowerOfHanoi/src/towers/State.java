/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towers;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Represents a given configuration of the Tower Of Hanoi puzzle.
 *
 * @version 1.0
 * @author Luke
 */
public class State implements Serializable {

    private int[][] state;
    private int g;
    private State previous;
    private int h;

    /**
     * A configuration of TOH
     *
     * @param state
     */
    public State(int[][] state) {
        this.state = state;
    }

    /**
     * Returns the previous configuration, so the State before the current, this
     * allows for counting the number of moves and also pretty printing and
     * visualizing process.
     *
     * @return
     */
    public State getPrevious() {
        return previous;
    }

    /**
     * When the new State is created it is important to assign the current State
     * to the previous - setPrevious(this) for example
     *
     * @param previous
     */
    public void setPrevious(State previous) {
        this.previous = previous;
    }

    /**
     *
     * @return the current configuration of the puzzle
     */
    public int[][] getState() {
        return state;
    }

    /**
     *
     * @param state Sets the current state of the puzzle
     */
    public void setState(int[][] state) {
        this.state = state;
    }

    /**
     *
     * @param h sets the estimate number of moves to the goal state
     */
    public void setH(int h) {
        this.h = h;
    }

    /**
     *
     * @param h
     * @return the estimate number of moves to the goal state
     */
    public int getH() {
        return this.h;
    }

    /**
     *
     * @return the number of moves taken to get to current configuration
     */
    public int getG() {
        return g;
    }

    /**
     *
     * @param g assigns the number of moves setG(currentState.getG()+1)
     */
    public void setG(int g) {
        this.g = g;
    }

    /**
     *
     * @param o to allow for comparing the two dimensional arrays
     * @return
     */
    @Override
    public boolean equals(Object o) {
        return (o instanceof State) && Arrays.deepEquals(((State) o).state, state);
    }

    /**
     *
     * @return to allow for comparing the two dimensional arrays
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }

}
