/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towers;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Luke
 */
public class TowersTest {

    public TowersTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of bfs2 method, of class Towers.
     */
    @Test
    public void testBfs2() {

        // creating the intial state
        System.out.println("bfs2");
        int[][] towers = new int[3][3];
        for (int i = 0; i < 3; i++) {
            towers[i][0] = (i * 2) + 1;
        }
        State intialState = new State(towers);

        // creating the goal state
        int[][] goal = new int[3][3];
        for (int i = 0; i < 3; i++) {
            goal[i][2] = (i * 2) + 1;
        }
        State goalState = new State(goal);

        // running the test
        Towers.bfs2(intialState, goalState);
    }

    /**
     * Test of findLegalMoves method, of class Towers.
     */
    @Test
    public void testFindLegalMoves() {
        System.out.println("findLegalMoves");
        // initialising the game
        int[][] t = new int[3][3];
        for (int i = 0; i < 3; i++) {
            t[i][0] = (i * 2) + 1;
        }
        State tow = new State(t);

        // the possible moves from intial setup
        int[][] pos1 = {{0, 0, 0}, {3, 0, 0}, {5, 1, 0}};
        int[][] pos2 = {{0, 0, 0}, {3, 0, 0}, {5, 0, 1}};

        State posMove1 = new State(pos1);
        State posMove2 = new State(pos2);

        // adding the possible moves to the array list
        ArrayList<State> expResult = new ArrayList<>();
        expResult.add(posMove1);
        expResult.add(posMove2);

        System.out.println(Towers.findLegalMoves(tow));

        ArrayList<State> result = Towers.findLegalMoves(tow);
        int count = 0;
        if (expResult.size() == result.size()) {
            for (State x : expResult) {
                for (State y : result) {
                    if (Arrays.deepEquals(x.getState(), y.getState())) {
                        count++;
                    }
                }
            }
        }
        if (count != result.size()) {
            fail("Different number of possible moves found");
        }

    }

    /**
     * Test of move method, of class Towers.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        // initialising the game
        int[][] t = new int[3][3];
        for (int i = 0; i < 3; i++) {
            t[i][0] = (i * 2) + 1;
        }
        State tow = new State(t);

        int[][] expectedPosition = {{0, 0, 0}, {3, 0, 0}, {5, 1, 0}};
        State expResult = new State(expectedPosition);
        int fromPole = 0;
        int toPole = 1;
        State result = Towers.move(fromPole, toPole, tow);
        System.out.println(Arrays.deepToString(expResult.getState()));
        System.out.println(Arrays.deepToString(result.getState()));
        Assert.assertArrayEquals(expResult.getState(), result.getState());
    }

}
