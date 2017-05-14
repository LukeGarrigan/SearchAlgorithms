/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slidingtileproject.Experiments;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import slidingtileproject.Heuristics.HeuristicFunction;
import slidingtileproject.Heuristics.Manhattan;
import slidingtileproject.SearchAlgorithms.AStar;
import slidingtileproject.SearchAlgorithms.SearchAlgorithm;
import slidingtileproject.State;

/**
 *
 * @author Luke
 */
public class Testing {

    private SearchAlgorithm searchAlgorithm;

    public Testing(SearchAlgorithm searchAlgorithm) {
        this.searchAlgorithm = searchAlgorithm;
    }

    public Set<State> createTestsBFS(State state) {

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

    public void testing(ArrayList<State> states, State goalState, int move) throws FileNotFoundException, IOException {
        long totalExpanded = 0;
        ArrayList<Double> nodesExpanded = new ArrayList<>();
        ArrayList<Double> numberMoves = new ArrayList<>();
        ArrayList<Double> times = new ArrayList<>();
        double totalTime = 0;
        double totalMoves = 0;
        for (State s : states) {
            double initialTime = System.currentTimeMillis();
            State expanded = searchAlgorithm.resolve(s, goalState);
            double endTime = System.currentTimeMillis();
            double elapsedTime = endTime - initialTime;
            totalTime += elapsedTime;
            totalExpanded += expanded.getNodesExpanded();
            totalMoves += expanded.getG();
            nodesExpanded.add((double) expanded.getNodesExpanded());
            numberMoves.add((double) expanded.getG());
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
        /*
        FileWriter fw = new FileWriter("outputastar.csv", true);
        DecimalFormat df = new DecimalFormat("#.#####");
        df.format(0.912385);
        BufferedWriter bw = new BufferedWriter(fw);
        try (PrintWriter out = new PrintWriter(bw)) {
            StringBuilder s = new StringBuilder();
            s.append(move).append(",").append(df.format(totalTime)).append(",").append(df.format(averageTime)).append(",")
                    .append(df.format(stdTime)).append(",").append(df.format(totalExpanded))
                    .append(",").append(df.format(sampleMean)).append(",")
                    .append(df.format(std)).append("\n");
            out.println(s.toString());
        }
         */
    }
}
