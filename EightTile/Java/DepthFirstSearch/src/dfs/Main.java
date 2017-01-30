/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfs;

/**
 *
 * @author Luke
 */
public class Main {

    public static void main(String[] args) {
        // TODO code application logic here

        float startTime = System.nanoTime();
        int[] problemState = new int[]{8, 6, 7, 2, 5, 4, 3, 0, 1};
        int[] puzzles = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 8};
        int[] puzzle = new int[]{2, 3, 5, 1, 4, 6, 0, 7, 8};

        int[] tenMoves = new int[]{2, 3, 5, 1, 4, 6, 0, 7, 8};
        
        
        // Testing moves
        int[] fiveMoves = new int[]{2, 0, 3, 1, 5, 6, 4, 7, 8};
        int[] tenMovesTwo = new int[]{2, 3, 6, 1, 5, 8, 0, 4, 7};
        int[] fifteenMoves = new int[]{3, 6, 8, 2, 5, 0, 1, 4, 7};
        int[] twentyMoves = new int[]{0, 6, 8, 3, 5, 7, 2, 1, 4};
        State s = new State(twentyMoves, null, "null");
        
        DFS dfs = new DFS();
        dfs.dfsWithStack(s);

    }

  
}
