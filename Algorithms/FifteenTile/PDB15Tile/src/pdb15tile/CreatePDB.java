/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdb15tile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Luke
 */
public class CreatePDB {

    public void bfs2(byte[] one, PDB15Tile.State s, byte[] storedNums, String fileName) throws IOException {
        Queue<PDB15Tile.State> q = new LinkedList<>();
        byte[] positions = new byte[storedNums.length];
        for (int i = 0; i < storedNums.length; i++) {
            positions[i] = getPatternPosition(storedNums[i], s.getState());
        }
        one[positions[0]] = 0;
        q.add(s);
        PDB15Tile.State current;
        while (!q.isEmpty()) {
            current = q.poll();
            // System.out.println(Arrays.toString(current.getState()));
            for (PDB15Tile.State neighbour : current.findNeighbours2()) {
                // System.out.println(Arrays.toString(neighbour.getState()));
                for (int i = 0; i < storedNums.length; i++) {
                    positions[i] = getPatternPosition(storedNums[i], neighbour.getState());
                }
                if (one[positions[0]] == 0) {
                    one[positions[0]] = neighbour.getH();
                    q.add(neighbour);
                }

            }

        }
        serializeArrayToFile(one, fileName);
    }

    public void bfs2(byte[][][] three, PDB15Tile.State s, byte[] storedNums, String fileName) throws IOException {
        Queue<PDB15Tile.State> q = new LinkedList<>();
        byte[] positions = new byte[storedNums.length];
        for (int i = 0; i < storedNums.length; i++) {
            positions[i] = getPatternPosition(storedNums[i], s.getState());
        }
        three[positions[0]][positions[1]][positions[2]] = 0;
        q.add(s);
        PDB15Tile.State current;
        while (!q.isEmpty()) {
            current = q.poll();
            // System.out.println(Arrays.toString(current.getState()));
            for (PDB15Tile.State neighbour : current.findNeighbours2()) {
                // System.out.println(Arrays.toString(neighbour.getState()));
                for (byte i = 0; i < storedNums.length; i++) {
                    positions[i] = getPatternPosition(storedNums[i], neighbour.getState());
                }
                if (three[positions[0]][positions[1]][positions[2]] == 0) {
                    three[positions[0]][positions[1]][positions[2]] = neighbour.getH();
                    q.add(neighbour);
                }

            }

        }
        serializeArrayToFile(three, fileName);
    }

    public void bfs2(byte[][][][][][] six, PDB15Tile.State s, byte[] storedNums, String fileName) throws IOException {
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
            // System.out.println(Arrays.toString(current.getState()));
            for (PDB15Tile.State neighbour : current.findNeighbours2()) {
                // System.out.println(Arrays.toString(neighbour.getState()));
                for (int i = 0; i < storedNums.length; i++) {
                    positions[i] = getPatternPosition(storedNums[i], neighbour.getState());
                }
                //System.out.println(six[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]]);
                if (six[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]] == 0) {
                    six[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]] = neighbour.getH();
                    q.add(neighbour);
                }

            }

        }
        serializeArrayToFile(six, fileName);
    }

    public void bfs2(byte[][][][][][][] seven, PDB15Tile.State s, byte[] storedNums, String fileName) throws IOException {
        Queue<PDB15Tile.State> q = new LinkedList<>();
        byte[] positions = new byte[storedNums.length];
        for (int i = 0; i < storedNums.length; i++) {
            positions[i] = getPatternPosition(storedNums[i], s.getState());
        }
        seven[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]][positions[6]] = 0;
        q.add(s);
        PDB15Tile.State current;
        while (!q.isEmpty()) {
            current = q.poll();
            // System.out.println(Arrays.toString(current.getState()));
            for (PDB15Tile.State neighbour : current.findNeighbours2()) {
                // System.out.println(Arrays.toString(neighbour.getState()));
                for (int i = 0; i < storedNums.length; i++) {
                    positions[i] = getPatternPosition(storedNums[i], neighbour.getState());
                }
                //System.out.println(six[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]]);
                if (seven[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]][positions[6]] == 0) {
                    seven[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]][positions[6]] = neighbour.getH();
                    q.add(neighbour);
                }

            }

        }
        serializeArrayToFile(seven, fileName);
    }

    public void bfs2(byte[][][][][][][][] eight, PDB15Tile.State s, byte[] storedNums, String fileName) throws IOException {
        int count = 0;
        Queue<PDB15Tile.State> q = new LinkedList<>();
        byte[] positions = new byte[storedNums.length];
        for (byte i = 0; i < storedNums.length; i++) {
            positions[i] = getPatternPosition(storedNums[i], s.getState());
        }
        eight[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]][positions[6]][positions[7]] = 0;
        q.add(s);
        PDB15Tile.State current;
        while (!q.isEmpty()) {
            current = q.poll();
            // System.out.println(Arrays.toString(current.getState()));
            for (PDB15Tile.State neighbour : current.findNeighbours2()) {
                // System.out.println(Arrays.toString(neighbour.getState()));
                for (int i = 0; i < storedNums.length; i++) {
                    positions[i] = getPatternPosition(storedNums[i], neighbour.getState());
                }
                //System.out.println(six[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]]);
                if (eight[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]][positions[6]][positions[7]] == 0) {
                    eight[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]][positions[6]][positions[7]] = neighbour.getH();
                    q.add(neighbour);
                    count++;
                }

            }
            if (count % 100000 == 0) {
                System.out.println(count);
            }
        }
        serializeArrayToFile(eight, fileName);
    }

    public byte getPatternPosition(byte value, byte[] tilesInPattern) {
        for (byte i = 0; i < 16; i++) {
            byte tile = tilesInPattern[i];
            if (tile == value) {
                return i;
            }
        }
        return -1; //not found
    }

    public void serializeArrayToFile(Object array, String filename) throws IOException {
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("Cannot serialize non-array object " + array.toString());
        }
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));
        outputStream.writeObject(array);
    }

}
