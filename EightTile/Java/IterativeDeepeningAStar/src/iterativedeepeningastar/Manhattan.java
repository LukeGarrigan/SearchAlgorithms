/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iterativedeepeningastar;

/**
 *
 * @author Luke
 */
public class Manhattan {
    
    public int manhattan(int[] puzz) {
        int total = 0;
        for (int i = 1; i < puzz.length; i++) {
            int expectedRow = (i - 1) / 3;
            int expectedCol = (i - 1) % 3;
            int num = 0;
            for (int j = 0; j < puzz.length; j++) {
                if (puzz[j] == i) {
                    num = j + 1;
                    break;
                }
            }
            int numRow = (num - 1) / 3;
            int numCol = (num - 1) % 3;
            total += Math.abs(expectedRow - numRow)
                    + Math.abs(expectedCol - numCol);
        }
        return total;
    }
}
