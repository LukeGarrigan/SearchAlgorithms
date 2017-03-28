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

    private final byte[] stored1;
    private final byte[] stored2;
    private final byte[] stored3;

    private byte[][][] three;
    private byte[][][][][][] six;
    private byte[][][][][][] six2;

    private int[] one;
    private int[][][][][][][] seven;
    private int[][][][][][][] seven2;
    private int[] storedNums1;
    private int[] storedNums2;
    private int[] storedNums3;
    public CalculatePDBHeuristic() {
        this.six2 = new byte[16][16][16][16][16][16];
        this.six = new byte[16][16][16][16][16][16];
        this.three = new byte[16][16][16];

        this.stored1 = new byte[]{1, 5, 6, 9, 10, 13};
        this.stored2 = new byte[]{7, 8, 11, 12, 14, 15};
        this.stored3 = new byte[]{2, 3, 4};
       
        this.storedNums1 = new int[]{5};
        this.storedNums2 = new int[]{1, 2, 3, 4, 6, 7, 8};
        this.storedNums3 = new int[]{9, 10, 11, 12, 13, 14, 15};

    }

    public void getStoredFiles() throws IOException, ClassNotFoundException {
        
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("PDB_3_1"));
        ObjectInputStream inputStream1 = new ObjectInputStream(new FileInputStream("PDB_6_1"));
        ObjectInputStream inputStream2 = new ObjectInputStream(new FileInputStream("PDB_6_2"));
        three = (byte[][][]) inputStream.readObject();
        six = (byte[][][][][][]) inputStream1.readObject();
        six2 = (byte[][][][][][]) inputStream2.readObject();
         /*
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("PDB_1_1"));
        ObjectInputStream inputStream1 = new ObjectInputStream(new FileInputStream("PDB_7_1"));
        ObjectInputStream inputStream2 = new ObjectInputStream(new FileInputStream("PDB_7_2"));
        one = (int[]) inputStream.readObject();
        seven = (int[][][][][][][]) inputStream1.readObject();
        seven2 = (int[][][][][][][]) inputStream2.readObject();
        */
    }

    public byte calculate663(byte[] currentState) {
        byte total = 0;
        byte[] temp = new byte[3];
        for (byte i = 0; i < stored3.length; i++) {
            byte value = getPatternPosition(stored3[i], currentState);
            temp[i] = value;
        }
        total += three[temp[0]][temp[1]][temp[2]];
        int[] temp2 = new int[6];
        for (int i = 0; i < stored2.length; i++) {
            int value = getPatternPosition(stored2[i], currentState);
            temp2[i] = value;
        }
        total += six2[temp2[0]][temp2[1]][temp2[2]][temp2[3]][temp2[4]][temp2[5]];

        int[] temp3 = new int[6];
        for (int i = 0; i < stored1.length; i++) {
            int value = getPatternPosition(stored1[i], currentState);
            temp3[i] = value;
        }
        total += six[temp3[0]][temp3[1]][temp3[2]][temp3[3]][temp3[4]][temp3[5]];
        return total;
    }

    /*
    public byte calculate771(byte[] currentState) {
        byte total = 0;
        int[] temp = new int[1];
        for (int i = 0; i < storedNums1.length; i++) {
            int value = getPatternPosition(storedNums1[i], currentState);
            temp[i] = value;
        }
        //System.out.println("Pos: " + temp[0] + " H: " + one[temp[0]]);
        total += one[temp[0]];
        int[] temp2 = new int[7];
        for (int i = 0; i < storedNums2.length; i++) {
            int value = getPatternPosition(storedNums2[i], currentState);
            temp2[i] = value;
        }
        // System.out.println(" H: " + seven[temp2[0]][temp2[1]][temp2[2]][temp2[3]][temp2[4]][temp2[5]][temp2[6]]);
        
        total += seven[temp2[0]][temp2[1]][temp2[2]][temp2[3]][temp2[4]][temp2[5]][temp2[6]];

        int[] temp3 = new int[7];
        for (int i = 0; i < storedNums3.length; i++) {
            int value = getPatternPosition(storedNums3[i], currentState);
            temp3[i] = value;
        }
        total += seven2[temp3[0]][temp3[1]][temp3[2]][temp3[3]][temp3[4]][temp3[5]][temp3[6]];
        return total;
    }
    */
    public byte getPatternPosition(byte value, byte[] tilesInPattern) {
        for (byte i = 0; i < 16; i++) {
            byte tile = tilesInPattern[i];
            if (tile == value) {
                return i;
            }
        }
        return -1; //not found
    }

    public int calculateManhattan(int[] puzz) {
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
