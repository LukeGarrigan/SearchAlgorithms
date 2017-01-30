/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iterativedeepeningdfs;

/**
 *
 * @author Luke
 */
public class IterativeDeepeningDFS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        IDDFS id = new IDDFS();
         // Testing moves
        int[] fiveMoves = new int[]{2, 0, 3, 1, 5, 6, 4, 7, 8};
        int[] tenMovesTwo = new int[]{2, 3, 6, 1, 5, 8, 0, 4, 7};
        int[] fifteenMoves = new int[]{3, 6, 8, 2, 5, 0, 1, 4, 7};
        int[] twentyMoves = new int[]{0, 6, 8, 3, 5, 7, 2, 1, 4};
        State initialState = new State(fiveMoves, null, "null");
        id.IDDFS(initialState);

    }

}
