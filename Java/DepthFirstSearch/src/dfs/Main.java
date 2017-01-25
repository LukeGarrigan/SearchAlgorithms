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
        State s = new State(puzzle, null, "null");
        
        DFS dfs = new DFS();
        dfs.dfsWithStack(s);
        
        
    }
    
}
