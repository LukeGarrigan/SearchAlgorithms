/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdb15tile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Luke
 */
public class CreatePDB {

    public void bfs2(int[][][] three, PDB15Tile.State s, int[] storedNums) {
        Queue<PDB15Tile.State> q = new LinkedList<>();
        int[] positions = new int[storedNums.length];
        for (int i = 0; i < storedNums.length; i++) {
            positions[i] = getPatternPosition(storedNums[i], s.getState());
        }
        three[positions[0]][positions[1]][positions[2]] = 0;
        q.add(s);
        PDB15Tile.State current;
        while (!q.isEmpty()) {
            current = q.poll();
            for (PDB15Tile.State neighbour : current.findNeighbours2()) {
                for (int i = 0; i < storedNums.length; i++) {
                    positions[i] = getPatternPosition(storedNums[i], neighbour.getState());
                }
                if (three[positions[0]][positions[1]][positions[2]] == 0) {
                    three[positions[0]][positions[1]][positions[2]] = neighbour.getH();
                    q.add(neighbour);
                }
            }
        }

    }

    public void bfs2(int[][][][][][] six, PDB15Tile.State s, int[] storedNums) {
        Queue<PDB15Tile.State> q = new LinkedList<>();
        int[] positions = new int[storedNums.length];
        for (int i = 0; i < storedNums.length; i++) {
            positions[i] = getPatternPosition(storedNums[i], s.getState());
        }
        six[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]] = 0;
        q.add(s);
        PDB15Tile.State current;
        while (!q.isEmpty()) {
            current = q.poll();
            for (PDB15Tile.State neighbour : current.findNeighbours2()) {
                for (int i = 0; i < storedNums.length; i++) {
                    positions[i] = getPatternPosition(storedNums[i], neighbour.getState());
                }
                if (six[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]] == 0) {
                    six[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]] = neighbour.getH();
                    q.add(neighbour);
                }
            }
        }

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

    public void sendSixToFile(int[][][][][][] six, String filename) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));
        outputStream.writeObject(six);
    }

    public void sendThreeToFile(int[][][] three, String filename) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));
        outputStream.writeObject(three);
    }

}
