/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarflattened;

import java.util.Timer;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.binarySearch;
import java.util.TimerTask;
import javafx.scene.chart.Axis;

/**
 *
 * @author Luke
 */
public class AStarFlattened {

    /**
     * @param args the command line arguments
     */
    static int[] GOAL = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};

    static ArrayList<State> OPENSET = new ArrayList<>();
    static ArrayList<State> CLOSEDSET = new ArrayList<>();

    public static void main(String[] args) {

        // TODO code application logic here
        Timer t = new Timer();
        // worlds most difficult sliding tile isntance ( 31 moves )
        int[] problemState = new int[]{8, 6, 7, 2, 5, 4, 3, 0, 1};
        int[] puzzle = new int[]{2, 3, 5, 1, 4, 6, 0, 7, 8};
        int[] puzzles = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 8};

        int[] fiveMoves = new int[]{2, 0, 3, 1, 5, 6, 4, 7, 8};
        int[] tenMovesTwo = new int[]{2, 3, 6, 1, 5, 8, 0, 4, 7};
        int[] fifteenMoves = new int[]{3, 6, 8, 2, 5, 0, 1, 4, 7};
        int[] twentyMoves = new int[]{0, 6, 8, 3, 5, 7, 2, 1, 4};
        int[] twentyFive = new int[]{6, 8, 7, 3, 5, 4, 2, 0, 1};
        int[] thirtyMoves = new int[]{8, 6, 7, 2, 5, 4, 0, 3, 1};

        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        int numberStates =0;
        long startTime = System.nanoTime();
       // int dist = manhattan(thirtyMoves);
        LinearConflicts l = new LinearConflicts();
        
        int lin = l.linearConflict(thirtyMoves);
        State initialState = new State(thirtyMoves, 0, lin, null, "null");
        
        
        ArrayList<Float> averageMemory = new ArrayList<>();
        float largestMemory = 0;
        OPENSET.add(initialState);
        while (OPENSET.isEmpty() == false) {
            numberStates = numberStates +1;
          

            //System.out.println("Total memory " +  total + " Free memory " + free + " Used Memory " + used/1000000);
            if (OPENSET.size() > 0) {
                int winner = 0;
                for (int i = 0; i < OPENSET.size(); i++) {
                    if (OPENSET.get(i).getF() < OPENSET.get(winner).getF()) {
                        winner = i;
                    }
                }
                State currentState = OPENSET.get(winner);

                if (Arrays.equals(currentState.getState(), GOAL)) {
                    printPath(currentState, startTime, beforeUsedMem);
                    break;
                }
                OPENSET.remove(currentState);
                CLOSEDSET.add(currentState);
                currentState.findNeighbours();
                ArrayList<State> neighbours = currentState.getNeighbours();
                for (int i = 0; i < neighbours.size(); i++) {
                    State neighbour = neighbours.get(i);
                    if (!CLOSEDSET.contains(neighbour)) {
                        float tempG = currentState.getG() + 1;
                        if (OPENSET.contains(neighbour)) {
                            if (tempG < neighbour.getG()) {
                                neighbour.setG(tempG);
                            }
                        } else {
                            neighbour.setG(tempG);
                            OPENSET.add(neighbour);
                        }
                        int liii = l.linearConflict(neighbour.getState());
                       // int man = manhattan(neighbour.getState());
                        neighbour.setH(liii);
                        neighbour.setF(neighbour.getG() + neighbour.getH());
                    }
                }
            }
        }
        float quickTot = 0;
        for (Float x : averageMemory) {
            quickTot = x + quickTot;
        }
        float average = quickTot / averageMemory.size();
        System.out.println("Average Memory " + average);
        System.out.println("Largest Memory " + largestMemory / 1000000);
        System.out.println("Number States "+ numberStates);
        System.exit(0);
    }

  

    public static void printPath(State currentState, long startTime, long beforeUsedMem) {
       // System.out.println(startTime);
       // System.out.println(System.currentTimeMillis());
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        System.out.println(" ACT USED + "+ actualMemUsed);
        int count = 0;
        while (currentState.getPrevious() != null) {
            count++;
            System.out.print(currentState.getDirection() + " ");
            currentState = currentState.getPrevious();
        }
        System.out.println("\n" + count);
    }
}
