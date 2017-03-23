/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdb15tile;

/**
 *
 * @author Luke
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CalculatePDBHeuristic {

    private final int[] stored1;
    private final int[] stored2;
    private final int[] stored3;

    private int[][][] three;
    private int[][][][][][] six;
    private int[][][][][][] six2;

    public CalculatePDBHeuristic() {
        this.six2 = new int[16][16][16][16][16][16];
        this.six = new int[16][16][16][16][16][16];
        this.three = new int[16][16][16];

        this.stored1 = new int[]{1, 5, 6, 9, 10, 13};
        this.stored2 = new int[]{7, 8, 11, 12, 14, 15};
        this.stored3 = new int[]{2, 3, 4};
    }

    public void getStoredFiles() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("PDB_3_1"));
        ObjectInputStream inputStream1 = new ObjectInputStream(new FileInputStream("PDB_6_1"));
        ObjectInputStream inputStream2 = new ObjectInputStream(new FileInputStream("PDB_6_2"));
        three = (int[][][]) inputStream.readObject();
        six = (int[][][][][][]) inputStream1.readObject();
        six2 = (int[][][][][][]) inputStream2.readObject();
        System.out.println("Files stored");
    }

    public int calculate(int[] currentState) {
        int total = 0;
        int[] temp = new int[3];
        for (int i = 0; i < stored3.length; i++) {
            int value = getPatternPosition(stored3[i], currentState);
            temp[i] = value;
        }
        total += three[temp[0]][temp[1]][temp[2]];

        int[] temp2 = new int[6];
        for (int i = 0; i < stored1.length; i++) {
            int value = getPatternPosition(stored1[i], currentState);
            temp2[i] = value;
        }
        total += six[temp2[0]][temp2[1]][temp2[2]][temp2[3]][temp2[4]][temp2[5]];

        int[] temp3 = new int[6];
        for (int i = 0; i < stored2.length; i++) {
            int value = getPatternPosition(stored2[i], currentState);
            temp3[i] = value;
        }
        total += six2[temp3[0]][temp3[1]][temp3[2]][temp3[3]][temp3[4]][temp3[5]];
        return total;
    }

    public int getPatternPosition(int value, int[] tilesInPattern) {
        for (int i = 0; i < 16; i++) {
            int tile = tilesInPattern[i];
            if (tile == value) {
                return i;
            }
        }
        return -1; //not found
    }
}
