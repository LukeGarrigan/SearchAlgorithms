/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breadthfirstsearch;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 *
 * @author Luke
 */
public class BFS {

    static int MOVE = 19;

    public static void main(String[] args) throws IOException {

        int totalTesting =0;
        for (int i = 0; i < 32; i++) {
            MOVE = i;

            int[] g = {1, 2, 3, 4, 5, 6, 7, 8, 0};
            State goalState = new State(g, null, "null", 8, 0);

            int[] t = {8, 0, 3, 7, 4, 5, 1, 2, 6};

            State test = new State(t, null, "null", 1, 0);
            /*
        ArrayList<State> tests = RandomizeArray(goalState.getState(), 10000);
        testing(tests, goalState);
             */
 /*
            THE CODE FOR TESTING EACH STATE IN THE PUZZLE
             */

            Set<State> allPossibleStates = createTestsBFS(goalState);

            ArrayList<State> one = new ArrayList<>();
            ArrayList<State> fiveNine = new ArrayList<>();
            ArrayList<State> tenFourteen = new ArrayList<>();
            ArrayList<State> fifteenNineteen = new ArrayList<>();
            ArrayList<State> twentyTwentyfour = new ArrayList<>();
            ArrayList<State> twentyFiveTwentyNine = new ArrayList<>();
            ArrayList<State> thirtyPlus = new ArrayList<>();

            for (State s : allPossibleStates) {
                int moves = s.getMoves();
                if (moves == MOVE) {
                    //s.resetFields();
                    s.setDirection("null");
                    s.setMoves(0);
                    s.setPreviousState(null);
                    one.add(s);
                }
            }
            totalTesting += one.size();
            System.out.println("Testing size: " + one.size());
           // testing(one, goalState);
        }
        System.out.println("TOTAL "+ totalTesting);

    }

    public static void testing(ArrayList<State> states, State goalState) throws FileNotFoundException, IOException {
        long totalExpanded = 0;
        ArrayList<Double> nodesExpanded = new ArrayList<>();
        ArrayList<Double> numberMoves = new ArrayList<>();
        ArrayList<Double> times = new ArrayList<>();
        double totalTime = 0;
        double totalMoves = 0;
        for (State s : states) {
            double initialTime = System.currentTimeMillis();
            State expanded = bfs(s, goalState);
            double endTime = System.currentTimeMillis();
            double elapsedTime = endTime - initialTime;
            totalTime += elapsedTime;
            totalExpanded += expanded.getNodesExpanded();
            totalMoves += expanded.getMoves();
            nodesExpanded.add((double) expanded.getNodesExpanded());
            numberMoves.add((double) expanded.getMoves());
            times.add((double) elapsedTime);
        }
        double averageTime = totalTime / states.size();

        System.out.println("\nTotal Time: " + totalTime);
        System.out.println("Average Time: " + averageTime);
        double sumOfTimesSquared = 0;
        for (Double i : times) {
            i = Math.pow(i - averageTime, 2);
            sumOfTimesSquared += i;
        }
        double varianceTime = sumOfTimesSquared / states.size() - 1;
        double stdTime = Math.sqrt(varianceTime);
        System.out.println("Standard Deviation Time: " + stdTime);

        System.out.println("\nTotal Nodes: " + totalExpanded);
        double sampleMean = totalExpanded / (double) states.size();
        System.out.println("Mean Nodes: " + sampleMean);
        double sumOfSquares = 0;
        for (Double i : nodesExpanded) {
            i = Math.pow(i - sampleMean, 2);
            sumOfSquares += i;
        }
        double variance = sumOfSquares / states.size() - 1;
        double std = Math.sqrt(variance);
        System.out.println("Standard Deviation Nodes: " + std);

        double sumOfMovesSquared = 0;
        System.out.println("\nTotal Moves " + totalMoves);
        double meanMoves = totalMoves / (double) states.size();
        System.out.println("Average Moves " + totalMoves / (double) states.size());
        for (Double i : numberMoves) {
            i = Math.pow(i - meanMoves, 2);
            sumOfMovesSquared += i;
        }
        double varianceMoves = sumOfMovesSquared / states.size() - 1;
        double stdMoves = Math.sqrt(varianceMoves);
        System.out.println("Standard Deviation Moves: " + stdMoves);

        // writing to a csv file
        FileWriter fw = new FileWriter("output.csv", true);
        DecimalFormat df = new DecimalFormat("#.#####");
        df.format(0.912385);
        BufferedWriter bw = new BufferedWriter(fw);
        try (PrintWriter out = new PrintWriter(bw)) {
            StringBuilder s = new StringBuilder();
            s.append(MOVE).append(",").append(df.format(totalTime)).append(",").append(df.format(averageTime)).append(",")
                    .append(df.format(stdTime)).append(",").append(df.format(totalExpanded))
                    .append(",").append(df.format(sampleMean)).append(",")
                    .append(df.format(std)).append("\n");
            out.println(s.toString());
        }
    }

    public static Set<State> createTestsBFS(State state) {

        Set<State> s = new HashSet<>();
        LinkedList<State> q = new LinkedList<>();

        s.add(state);
        q.add(state);

        while (q.size() > 0) {
            State currentState = q.removeFirst();
            for (State neighbour : currentState.findNeighbours()) {
                if (!s.contains(neighbour)) {
                    s.add(neighbour);
                    q.add(neighbour);
                }
            }
        }
        return s;
    }

    public static State bfs(State state, State goal) {
        Set<State> s = new HashSet<>();
        LinkedList<State> q = new LinkedList<>();
        s.add(state);
        q.add(state);
        int nodesExpanded = 0;
        while (q.size() > 0) {
            nodesExpanded++;
            State currentState = q.removeFirst();
            if (currentState.equals(goal)) {
                currentState.setNodesExpanded(nodesExpanded);
                return currentState;
            }
            for (State neighbour : currentState.findNeighbours()) {
                if (!s.contains(neighbour)) {
                    s.add(neighbour);
                    q.add(neighbour);
                }
            }
        }
        return null;
    }

    public static ArrayList<State> RandomizeArray(int[] array, int amount) {
        // Random number generator			
        ArrayList<State> testValues = new ArrayList<>();
        while (testValues.size() < amount) {
            for (int i = 0; i < array.length; i++) {
                Random rgen = new Random();
                int randomPosition = rgen.nextInt(array.length);

                int temp = array[i];
                array[i] = array[randomPosition];
                array[randomPosition] = temp;
            }
            if (isSolvable2(array)) {
                int[] t = array.clone();
                int zeroPos = 0;
                for (int i = 0; i < t.length; i++) {
                    if (t[i] == 0) {
                        zeroPos = i;
                    }
                }

                State s = new State(t, null, "null", zeroPos, 0);

                testValues.add(s);
            }
        }
        return testValues;
    }

    public static boolean isSolvable2(int[] puzzle) {
        boolean parity = true;
        int gridWidth = (int) Math.sqrt(puzzle.length);
        boolean blankRowEven = true; // the row with the blank tile

        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] == 0) { // the blank tile
                blankRowEven = (i / gridWidth) % 2 == 0;
                continue;
            }
            for (int j = i + 1; j < puzzle.length; j++) {
                if (puzzle[i] > puzzle[j] && puzzle[j] != 0) {
                    parity = !parity;
                }
            }
        }

        // even grid with blank on even row; counting from top
        if (gridWidth % 2 == 0 && blankRowEven) {
            return !parity;
        }
        return parity;
    }
}
