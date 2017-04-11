/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdb15tile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import pdb15tile.PDB15Tile.State;

/**
 *
 * @author Luke
 */
public class Fringe {

    private byte[] storedNums = {1, 2, 3, 4, 5, 9, 13, 0};
    private String fileName = "fringe1";
    private byte[][][][][][][][] fringe;
    private State s;
    private byte[] startingState = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
        12, 13, 14, 15, 0};

    public Fringe() {
        System.out.println("hey");
        fringe = new byte[16][16][16][16][16][16][16][16];
        System.out.println("heyy");
        s = new State(startingState, "null", 15);
    }

    public void createFringe() throws IOException {
        int count = 0;
        System.out.println("Starting fringe");
        Queue<PDB15Tile.State> q = new LinkedList<>();
        Set<State> seen = new HashSet<>();

        seen.add(s);
        byte[] positions = new byte[storedNums.length];
        for (int i = 0; i < storedNums.length; i++) {
            positions[i] = getPatternPosition(storedNums[i], s.getState());
        }
        fringe[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]][positions[6]][positions[7]] = 0;
        q.add(s);
        PDB15Tile.State current;
        while (!q.isEmpty()) {
            current = q.poll();
            // System.out.println(Arrays.toString(current.getState()));
            for (PDB15Tile.State neighbour : current.findNeighbours()) {
                // System.out.println(Arrays.toString(neighbour.getState()));
                if (!seen.contains(neighbour)) {
                    for (int i = 0; i < storedNums.length; i++) {
                        positions[i] = getPatternPosition(storedNums[i], neighbour.getState());
                    }
                    if (fringe[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]][positions[6]][positions[7]] == 0) {
                        count++;
                        fringe[positions[0]][positions[1]][positions[2]][positions[3]][positions[4]][positions[5]][positions[6]][positions[7]] = neighbour.getH();
                        q.add(neighbour);
                    }
                }
                seen.add(neighbour);

            }
            if (count % 10000000 == 0) {
                System.out.println(count);
            }

        }
        serializeArrayToFile(fringe, fileName);
    }

    public void serializeArrayToFile(Object array, String filename) throws IOException {
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("Cannot serialize non-array object " + array.toString());
        }
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));
        outputStream.writeObject(array);
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

}
