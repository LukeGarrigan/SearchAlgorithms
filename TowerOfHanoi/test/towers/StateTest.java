/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Luke
 */
public class StateTest {

    public StateTest() {
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
     * Test of getPrevious method, of class State.
     */
    @Test
    public void testGetPrevious() {
        int[][] towers = new int[3][3];
        for (int i = 0; i < 3; i++) {
            towers[i][0] = (i * 2) + 1;
        }
        State intialState = new State(towers);
        int[][] towers2 = new int[3][3];
        for (int i = 0; i < 3; i++) {
            towers2[i][0] = (i * 2) + 1;
        }
        State previousState= new State(towers2);

        intialState.setPrevious(previousState);
        System.out.println("getPrevious");
        State result = intialState.getPrevious();
        State expResult = previousState;
        assertEquals(expResult, result);
    }

    /**
     * Test of getState method, of class State.
     */
    @Test
    public void testGetState() {
        int[][] towers = new int[3][3];
        for (int i = 0; i < 3; i++) {
            towers[i][0] = (i * 2) + 1;
        }
        State intialState = new State(towers);
        int[][] expResult = towers;
        int[][] result = intialState.getState();
        assertArrayEquals(expResult, result);
    }
}
