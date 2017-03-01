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
public class State {

    int[] state;
    float g, h, f;
    State previousState;
    String direction;

    public State(int[] state, float g, float h, State previousState, String direction) {
        this.state = state;
        this.g = g;
        this.h = h;
        this.previousState = previousState;
        this.direction = direction;
       
    }

    public float getG() {
        return g;
    }

    public float getH() {
        return h;
    }

    public float getF() {
        return f;
    }

    public State getPreviousState() {
        return previousState;
    }

    public String getDirection() {
        return this.direction;
    }

    public void updateDirection(String direction) {
        this.direction = direction;
    }

    public State getPrevious() {
        return this.previousState;
    }

    public int[] getState() {
        return this.state;
    }

    public float getFScore() {
        return this.f;
    }

    public void setState(int[] state) {
        this.state = state;
    }

    public void setG(float g) {
        this.g = g;
    }

    public void setH(float h) {
        this.h = h;
    }

    public void setF(float f) {
        this.f = f;
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public float getGScore() {
        return this.g;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof State) && Arrays.equals(((State) o).state, state);
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }
}
