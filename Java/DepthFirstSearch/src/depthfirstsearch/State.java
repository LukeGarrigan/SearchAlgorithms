/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package depthfirstsearch;

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
    private ArrayList<State> neighbours;


    public State(int[] state, State previousState, String direction) {
        this.state = state;
        //this.visisted = false;
        this.previousState = previousState;
        this.direction = direction;
        this.neighbours = new ArrayList<>();

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

    public void setNeighbours(ArrayList<State> neighbours) {
        this.neighbours = neighbours;
    }

    public int[] getState() {
        return state;
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
        return direction;
    }

    public ArrayList<State> getNeighbours() {
        return this.neighbours;
    }

    // public State(int[] state, int g, int h, State previousState, String direction) {
    public void findNeighbours() {
        //  this.neighbours = new ArrayList<>();

        //int[] movement = new int[9];
        //System.arraycopy(state, 0, movement, 0, movement.length);
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                if (i % 3 != 0) {
                    int[] left = new int[9];
                    System.arraycopy(state, 0, left, 0, left.length);
                    // System.out.println(Arrays.toString(puzzle));
                    int temp = left[i];
                    left[i] = left[i - 1];
                    left[i - 1] = temp;

                    State newState = new State(left,this, "left");

                    this.neighbours.add(newState);
                    //System.out.println("GONE LEFT" + "\n");
                    //System.out.println(Arrays.toString(newState.getState()));
                    //movement = state;

                }
                if (i % 3 != 2) {
                    int[] right = new int[9];
                    System.arraycopy(state, 0, right, 0, right.length);
                    //  System.out.println(Arrays.toString(puzzle));
                    int temp = right[i];
                    right[i] = right[i + 1];
                    right[i + 1] = temp;
                    State newState = new State(right, this, "right");
                    this.neighbours.add(newState);

                    // System.out.println("GONE RIGHT" + "\n");
                    //System.out.println(Arrays.toString(newState.getState()));
                    // movement = state;
                }
                if (i > 3) {
                    // System.out.println(Arrays.toString(puzzle));
                    int[] up = new int[9];
                    System.arraycopy(state, 0, up, 0, up.length);

                    int temp = up[i];
                    up[i] = up[i - 3];
                    up[i - 3] = temp;
                    State newState = new State(up, this, "up");
                    this.neighbours.add(newState);
                    // System.out.println("GONE UP" + "\n ");
                    // System.out.println(Arrays.toString(newState.getState()));
                    //movement = state;
                }
                if (i < 6) {
                    int[] down = new int[9];
                    System.arraycopy(state, 0, down, 0, down.length);
                    //  System.out.println(Arrays.toString(puzzle));
                    int temp = down[i];
                    down[i] = down[i + 3];
                    down[i + 3] = temp;
                    State newState = new State(down,  this, "down");
                    this.neighbours.add(newState);
                    // System.out.println(Arrays.toString(newState.getState()));
                    // System.out.println("GONE DOWN" + " \n");
                    // movement = state;
                }
                break;
            }
        }
       
//        System.out.println("CHECK");
//        for (State neighb : neighbours) {
//            System.out.println(Arrays.toString(neighb.getState()));
//        }
//        System.out.println("CHECKED");
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
