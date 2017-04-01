/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * Solves the TOH problem in the minimum number of moves.
 *
 * @author Luke
 * @version 1.0
 */
public class Towers {

    /**
     * Represents the starting state of the puzzle
     */
    public static int[][] towers;

    /**
     * Number of poles used
     */
    public static int poles = 4;

    /**
     * Number of discs used
     */
    public static int discs = 7;

    /**
     * Represents the goal state
     */
    public static int[][] goal;

    /**
     * Initializes the starting and goal states and runs the breadth-first
     * -search algorithm.
     *
     * @param args
     */
    public static void main(String[] args) {
        // assigns the disc and pole parameters
        towers = new int[discs][poles];
        goal = new int[discs][poles];
        // place the discs on the left-most pole (column)
        for (int disc = 0; disc < discs; disc++) {
            towers[disc][0] = (disc * 2) + 1;
        }
        State intialState = new State(towers);

        // places the discs on the right-most pole (column) 
        for (int disc = 0; disc < discs; disc++) {
            goal[disc][2] = (disc * 2) + 1;
        }
        State goalState = new State(goal);

        // runs the search algorithm
        bfs(intialState, goalState);
    }

    /**
     * Performs the breadth-first search algorithm for finding to goal state
     * from the initial state in the minimum number of moves. This will also be
     * used to create the pattern databases, performed on the goal state.
     *
     * @param intialState
     * @param goalState
     */
    public static void bfs(State intialState, State goalState) {
        Queue<State> q = new LinkedList<>();
        Set<State> seen = new HashSet<>();
        q.add(intialState);
        seen.add(intialState);
        while (!q.isEmpty()) {
            State current = q.poll();
            if (current.equals(goalState)) {
                System.out.println("Found the goal state");
                State previous = current.getPrevious();
                while (previous != null) {
                    printTowers(previous.getState());
                    previous = previous.getPrevious();
                }
                System.out.println("Moves: " + current.getG());
                System.out.println("Number of states: " + seen.size());

            }
            // finds all the legal moves from current position and adds the 
            // ones not already seen to the queue
            for (State x : findLegalMoves(current)) {
                boolean beenSeen = false;
                for (State val : seen) {
                    if (x.equals(val)) {
                        beenSeen = true;
                    }
                }
                if (beenSeen == false) {
                    q.add(x);
                    seen.add(x);
                }
            }
        }
    }

    /**
     * Given a current state, finds all legal moves from that given state and
     * returns them in an ArrayList.
     *
     * @param towers represents the current State
     * @return
     */
    public static ArrayList<State> findLegalMoves(State towers) {
        // loop through all the poles 
        // and moves the top discs 
        ArrayList<State> legalMoves = new ArrayList<>();
        for (int pole = 0; pole < poles; pole++) {
            for (int disc = 0; disc < discs; disc++) {
                if (towers.getState()[disc][pole] != 0) {
                    for (int i = 0; i < poles; i++) {
                        if (i != pole) {
                            State blar = move(pole, i, towers);
                            legalMoves.add(blar);
                        }
                    }
                    break;
                }
            }

        }
        return legalMoves;
    }

    /**
     * Moves the top disc from one pole to another and updates the config.
     *
     * @param fromPole the initial pole with the disc to be moved
     * @param toPole the pole which will contain the disc
     * @param towerss the object which will be updated with the new config
     * @return the updated State
     */
    public static State move(int fromPole, int toPole, State towerss) {
        int length = towerss.getState().length;
        int[][] pole = new int[length][towerss.getState()[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(towerss.getState()[i], 0, pole[i], 0, towerss.getState()[i].length);
        }
        int disc = 0;
        // finds the top disc of the current pole
        while (disc < discs && pole[disc][fromPole] == 0) {
            disc++;
        }

        int temp = pole[disc][fromPole];
        pole[disc][fromPole] = 0;

        int newDisc = 0;
        // finds the top position of the new pole
        while (newDisc < discs && pole[newDisc][toPole] == 0) {
            newDisc++;
        }
        // we need to make sure a bigger disc is not placed on a smaller one
        if (newDisc < discs) {
            if (pole[newDisc][toPole] > temp) {
                pole[--newDisc][toPole] = temp;
                // printTowers(pole);
                State s = new State(pole);
                s.setG(towerss.getG() + 1);
                s.setPrevious(towerss);
                return s;
            } else {
                pole[disc][fromPole] = temp;
                return towerss;
            }
        } else {
            pole[--newDisc][toPole] = temp;
            State s = new State(pole);
            s.setG(towerss.getG() + 1);
            s.setPrevious(towerss);
            return s;
        }

    }

    /**
     * Prints the towers in a pretty format.
     *
     * @param towers
     */
    public static void printTowers(int[][] towers) {
        System.out.println();
        for (int disc = 0; disc < discs; disc++) {
            for (int pole = 0; pole < poles; pole++) {
                System.out.print(pad(towers[disc][pole]));
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Pads the width of the disc for printing.
     *
     * @param disc - the disc number (1 to 4 if there are 4 discs)
     * @return - padded string for pretty printing
     */
    public static String pad(int disc) {
        // pad string with spaces
        int columnWidth = (discs * 2) + 2;

        String output = "";
        if (disc == 0) {
            output = "|";
        } else {
            for (int i = 0; i < disc; i++) {
                output += "*";
            }
        }

        // if length is odd pad end to even length
        if (output.length() % 2 == 1) {
            output += " ";
        }
        // justify pad to center in cell
        while (output.length() < columnWidth) {
            output = " " + output + " ";
        }
        return output;
    }
}
