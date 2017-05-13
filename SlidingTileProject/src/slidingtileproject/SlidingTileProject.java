/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slidingtileproject;

import slidingtileproject.Experiments.RandomStates;
import slidingtileproject.Heuristics.SequenceAlign;
import slidingtileproject.Heuristics.HeuristicFunction;
import slidingtileproject.Heuristics.LinearConflict;
import slidingtileproject.Heuristics.Manhattan;
import slidingtileproject.SearchAlgorithms.IterativeDeepeningAStar;
import slidingtileproject.SearchAlgorithms.SearchAlgorithm;
import java.util.ArrayList;
import java.util.Arrays;
import slidingtileproject.SearchAlgorithms.AStar;
import slidingtileproject.SearchAlgorithms.BFS;

/**
 *
 * @author Luke
 */
public class SlidingTileProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        int[] twentyFiveMoves = new int[]{5, 1, 3, 4, 7, 2, 8, 12, 9, 6, 10, 0, 13, 14, 15, 11};
        int[] thirtyMoves = new int[]{5, 2, 1, 3, 7, 0, 8, 4, 9, 6, 10, 12, 13, 14, 15, 11};
        int[] thirtyFiveMoves = new int[]{5, 0, 2, 1, 7, 6, 8, 3, 9, 10, 15, 4, 13, 14, 11, 12};
        //int[] fourtyMoves = new int[]{6, 7, 0, 11, 1, 5, 10, 4, 14, 13, 15, 2, 9, 8, 3, 12};
        int[] fiftyMoves = new int[]{1, 3, 11, 14, 6, 2, 5, 0, 15, 4, 13, 9, 12, 10, 7, 8};
        int[] sixtyFiveMoves = new int[]{11, 14, 9, 15, 7, 2, 8, 13, 3, 0, 5, 6, 12, 1, 10, 4};
        int[] quickTest = new int[]{2, 9, 14, 0, 4, 7, 5, 6, 15, 1, 3, 13, 10, 11, 12, 8};

        int[] hundredSixtyOneAccordingToMan = {10, 13, 15, 11, 3, 7, 5, 1, 8, 0, 14, 4, 9, 2, 12, 6};
        HeuristicFunction h = new Manhattan();
        HeuristicFunction l = new LinearConflict();
        HeuristicFunction s = new SequenceAlign();
        int[] g = new int[]{1, 2, 3, 4, 5, 6, 7, 8,
            9, 10, 11, 12, 13, 14, 15, 0};
        State goal = new State(g, 0, 0, null, "null");
        // State stt = new State(hundredSixtyOneAccordingToMan, 0, 0, null, "null");
        // System.out.println(h.calculateHeuristic(stt));
        int[] fourtyMoves = new int[]{6, 7, 0, 11, 1, 5, 10, 4, 14, 13, 15, 2, 9, 8, 3, 12};
        RandomStates r = new RandomStates();
        ArrayList<int[]> testStates = r.RandomizeArray(fourtyMoves, 5);

        SearchAlgorithm ida = new IterativeDeepeningAStar(l);

        SearchAlgorithm a = new AStar(l);
        SearchAlgorithm b = new BFS();
        int[] goal8tile = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        int[] test8tile = {1, 4, 3, 5, 8, 7, 0, 6, 2};

        State gg = new State(goal8tile, 0, 0, null, "null");
        State t = new State(test8tile, 0, 0, null, "null");
        System.out.println(a.resolve(t, gg).getG());
        System.out.println(b.resolve(t, gg).getG());
        /*
        for (int[] testState : testStates) {
            System.out.println(Arrays.toString(testState));
            State st = new State(testState, 0, 0, null, "null");
            st.setG(0);
            long startTime = System.currentTimeMillis();
            st.setH(l.calculateHeuristic(st));
            State goalState = ida.resolve(st, goal);
            //float goalState = ida.resolve1(st);
            long endTime = System.currentTimeMillis();
            float elapsedTime = endTime - startTime;
            System.out.println("Time Taken(s): " + elapsedTime / 1000);
            System.out.println("GOAL : " + goalState);

            System.out.println("");

        }
         */

    }
}
