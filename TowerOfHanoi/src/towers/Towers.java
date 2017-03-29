/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Luke
 */
public class Towers {

    public static int[][] towers;
    public static int poles = 3;
    public static int discs;
    public static int moves = 0;
    public static int[][] goal;

    public static void main(String[] args) {
        System.out.print("Enter number of discs: ");
        Scanner scanner = new Scanner(System.in);

        discs = scanner.nextInt();
        towers = new int[discs][poles];
        goal = new int[discs][poles];
        // place the discs on the left-most pole (column)
        for (int disc = 0; disc < discs; disc++) {
            towers[disc][0] = (disc * 2) + 1;
        }
        printTowers(towers);
        //solveTowers(discs, 0, 2);
        for (int disc = 0; disc < discs; disc++) {
            goal[disc][2] = (disc * 2) + 1;
        }

        //moveDisc(0, 2);
        // moveDisc(0, 2);
        bfs2();
        scanner.close();
        System.out.println("Number of moves: " + moves);
    }

    /**
     * Complete this recursive method to solve the Towers of Hanoi
     *
     * @param numDiscs - number of discs in the problem
     * @param startPole - pole the discs start on (zero based)
     * @param endPole - pole the discs end on (zero based)
     */
    public static void solveTowers(int numDiscs, int startPole, int endPole) {

    }

    public static void bfs2() {
        Queue<int[][]> q = new LinkedList<>();
        Set<int[][]> seen = new HashSet<>();
        q.add(towers);
        seen.add(towers);
        while (!q.isEmpty()) {
            int[][] current = q.poll();
            if (Arrays.equals(current, goal)) {
                System.out.println("WE MADE IT!");
                break;
            }

            for (int[][] x : findLegalMoves(current)) {
                if (!seen.contains(x)) {
                    q.add(x);
                    seen.add(x);
                }
            }
        }

    }

    public static ArrayList<int[][]> findLegalMoves(int[][] towers) {
        // loop through all the poles 
        // and moves the top discs 
        ArrayList<int[][]> legalMoves = new ArrayList<>();
        for (int pole = 0; pole < poles; pole++) {
            for (int disc = 0; disc < discs; disc++) {
                if (towers[disc][pole] != 0) {
                    System.out.println("Top disc " + towers[disc][pole]);
                    for (int i = 0; i < poles; i++) {
                        if (i != pole) {
                            int[][] blar = move(pole, i, towers);
                            legalMoves.add(blar);
                        }
                    }
                    break;
                }
            }

        }
        return legalMoves;
    }

    public static int[][] move(int fromPole, int toPole, int[][] towerss) {
        int length = towerss.length;
        int[][] pole = new int[length][towerss[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(towerss[i], 0, pole[i], 0, towerss[i].length);
        }

        int disc = 0;
        // finds the top disc of the current pole
        while (disc < discs && pole[disc][fromPole] == 0) {
            disc++;
        }

        int temp = pole[disc][fromPole];
        pole[disc][fromPole] = 0;

        int newDisc = 0;
        // finds the top position of the new pole
        while (newDisc < discs && pole[newDisc][toPole] == 0) {
            newDisc++;
        }
        // we need to make sure a bigger disc is not placed on a smaller one
        if (newDisc < discs) {
            if (pole[newDisc][toPole] > temp) {
                pole[--newDisc][toPole] = temp;
                moves++;
                printTowers(pole);
                return pole;
            } else {
                pole[disc][fromPole] = temp;
                return pole;
            }
        } else {
            pole[--newDisc][toPole] = temp;
            moves++;
            printTowers(pole);
            return pole;
        }

    }

    /**
     * Prints the towers
     */
    public static void printTowers(int[][] towers) {
        System.out.println();
        for (int disc = 0; disc < discs; disc++) {
            for (int pole = 0; pole < poles; pole++) {
                System.out.print(pad(towers[disc][pole]));
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Pads the width of the disc for printing
     *
     * @param disc - the disc number (1 to 4 if there are 4 discs)
     * @return - padded string for pretty printing
     */
    public static String pad(int disc) {
        // pad string with spaces
        int columnWidth = (discs * 2) + 2;

        String output = "";
        if (disc == 0) {
            output = "|";
        } else {
            for (int i = 0; i < disc; i++) {
                output += "*";
            }
        }

        // if length is odd pad end to even length
        if (output.length() % 2 == 1) {
            output += " ";
        }
        // justify pad to center in cell
        while (output.length() < columnWidth) {
            output = " " + output + " ";
        }
        return output;
    }
}
