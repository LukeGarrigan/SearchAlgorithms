/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slidingtileproject;

/**
 *
 * @author Luke
 */
public class Manhattan implements HeuristicFunction {
    @Override
    public int calculateHeuristic(int[] puzz) {
        int total = 0;
        for (int j = 0; j < puzz.length; j++) {
            int i = puzz[j];
            if (i != 0) {
                int expectedRow = (i - 1) / 3;
                int expectedCol = (i - 1) % 3;
                int numRow = j / 3;
                int numCol = j % 3;
                total += Math.abs(expectedRow - numRow)
                        + Math.abs(expectedCol - numCol);
            }
        }
        return total;
    }
}
