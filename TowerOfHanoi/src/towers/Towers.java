/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    public static int discs = 6;

    /**
     * Represents the goal state
     */
    public static int[][] goal;
    
    private static HashSet<State> one;
    
    public static State goalState;
    public static State initialState;
    
    public static int nextCostBound;

    /**
     * Initializes the starting and goal states and runs the breadth-first
     * -search algorithm.
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        // assigns the disc and pole parameters
        towers = new int[discs][poles];
        goal = new int[discs][poles];
        // place the discs on the left-most pole (column)
        for (int disc = 0; disc < discs; disc++) {
            towers[disc][0] = (disc * 2) + 1;
        }
        initialState = new State(towers);

        // places the discs on the right-most pole (column) 
        for (int disc = 0; disc < discs; disc++) {
            goal[disc][3] = (disc * 2) + 1;
        }
        goalState = new State(goal);

        // runs the search algorithm
        createPDB(goalState);
        
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("pdb7"));
            one = (HashSet<State>) inputStream.readObject();
        } catch (Exception ex) {
        }
        System.out.println("Solving");
        System.out.println("Initial State:");
        System.out.println(Arrays.deepToString(initialState.getState()));
        printTowers(initialState.getState());
        
        System.out.println("Goal State:");
        System.out.println(Arrays.deepToString(goalState.getState()));
        printTowers(goalState.getState());
        bfs(initialState, goalState);
        //idastar(initialState);

    }
    
    public static int getHeuristic(State s) {
        for (State x : one) {
            if (x.equals(s)) {
                s.setH(x.getH());
                // System.out.println("H: " + s.getH() + " G: " + s.getG());
                return x.getH();
            }
        }
        return 0;
    }
    
    public static State idastar(State start) {
        int h = getHeuristic(start);
        start.setH(h);
        start.setG(0);
        nextCostBound = start.getH();
        State solution = null;
        
        while (solution == null) {
            int currentCostBound = nextCostBound;
            solution = depthFirstSearch(start, currentCostBound);
            nextCostBound += 2;
        }
        return solution;
    }
    
    public static State depthFirstSearch(State current, int currentCostBound) {
        if (current.equals(goalState)) {
            System.out.println("Found the goal state");
            State previous = current.getPrevious();
            //  while (previous != null) {
            //     printTowers(previous.getState());
            //    previous = previous.getPrevious();
            //}
            System.out.println("Moves: " + current.getG());
            return current;
        }
        for (State next : findLegalMoves(current)) {
            int h = getHeuristic(next);
            next.setH(h);
            System.out.println("Current G : " + current.getG() + " Current H : " + current.getH());
            System.out.println("Next G : " + next.getG() + " Next H : " + next.getH());
            System.out.println("");
            int value = next.getG() + next.getH();
            if (value <= currentCostBound) {
                State result = depthFirstSearch(next, currentCostBound);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
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
    
    public static void createPDB(State goalState) throws IOException {
        Queue<State> q = new LinkedList<>();
        Set<State> seen = new HashSet<>();
        q.add(goalState);
        seen.add(goalState);
        goalState.setH(0);
        while (!q.isEmpty()) {
            State current = q.poll();
            // finds all the legal moves from current position and adds the 
            // ones not already seen to the queue
            for (State x : findLegalMoves(current)) {
                x.setHForPDB();
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
        
        try {
            FileOutputStream fos = new FileOutputStream("pdb7");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(seen); // write MenuArray to ObjectOutputStream
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //serializeArrayToFile(seen, "pdb7");
    }
    
    public static void serializeArrayToFile(Object array, String filename) throws IOException {
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("Cannot serialize non-array object " + array.toString());
        }
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));
        outputStream.writeObject(array);
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
                            // makes sure it doesn't create a new object
                            // which is just going back on itself
                            if (!blar.equals(towers.getPrevious())) {
                                legalMoves.add(blar);
                            }
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
