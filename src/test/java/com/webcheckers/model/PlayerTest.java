package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    private Player CuT;
    public static final String name = "Joe Mama";
    public static final Player samplePlayer = new Player(name);

    /**
     * Initiates all of the mock objects
     */
    @BeforeEach
    public void setup() {
        CuT = new Player(name);
    }

    @Test
    /**
     * Tests whether the name instance can be obtained
     */
    public void test_get_name() {
        assertNotNull(CuT.getName());
        assertEquals(samplePlayer.getName(), CuT.getName());
    }

    @Test
    /**
     * Tests whether the inGame instance can be obtained
     */
    public void test_get_in_game() {
        assertEquals(samplePlayer.getInGame(), CuT.getInGame());
    }

    @Test
    /**
     * Tests whether the setInGame() method operates by changing the boolean value for the inGame instance
     */
    public void test_set_in_game_different_statuses() {
        Player newPlayer = new Player("j");
        newPlayer.addInGameStatus();
        assertNotEquals(newPlayer.getInGame(), CuT.getInGame());
    }

    @Test
    /**
     * Tests whether the setInGame() method operates by changing the boolean value to the original value for the inGame instance
     */
    public void test_set_in_game_same_statuses() {
        Player newPlayer = new Player("j");
        newPlayer.removeInGameStatus();
        assertEquals(newPlayer.getInGame(), CuT.getInGame());
    }

    @Test
    /**
     * Tests the equals method between two identical objects
     */
    public void test_equals_same(){
        assertEquals(samplePlayer.equals(samplePlayer), CuT.equals(CuT));
    }

    @Test
    /**
     * Tests the equals method between two different Player objects
     */
    public void test_equals_same_but_different_objects(){
        assertEquals(samplePlayer.equals(CuT), CuT.equals(samplePlayer));
    }

    @Test
    /**
     * Tests the equals method between two different objects
     */
    public void test_different_objects(){
        ArrayList<String> a = new ArrayList<>();
        assertEquals(a.equals(CuT), CuT.equals(a));
    }


    @Test
    /**
     * Tests that a turn stack can be accessed and a new stack is made when a game is created
     */
    public void testGetTurnStack() {
        assertNull(CuT.getTurnStack());
        CuT.addInGameStatus();
        assertNotNull(CuT.getTurnStack());
    }

    /**
     * Tests that we can set the turn stack
     */
    @Test
    public void testSetTurnStack() {
        assertNull(CuT.getTurnStack());
        CuT.setTurnStack(new Stack<>());
        assertNotNull(CuT.getTurnStack());
    }
}
