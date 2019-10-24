package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    private Player CuT;
    public static final String name = "Joe Mama";
    public static final Player samplePlayer = new Player(name);

    @BeforeEach
    public void setup() {
        CuT = new Player(name);
    }

    @Test
    public void test_get_name() {
        assertNotNull(CuT.getName());
        assertEquals(samplePlayer.getName(), CuT.getName());
    }

    @Test
    public void test_get_in_game() {
        assertEquals(samplePlayer.getInGame(), CuT.getInGame());
    }

    @Test
    public void test_set_in_game_different_statuses() {
        Player newPlayer = new Player("j");
        newPlayer.setInGame(true);
        assertNotEquals(newPlayer.getInGame(), CuT.getInGame());
    }

    @Test
    public void test_set_in_game_same_statuses() {
        Player newPlayer = new Player("j");
        newPlayer.setInGame(false);
        assertNotEquals(newPlayer.getInGame(), CuT.getInGame());
    }

    @Test
    public void test_equals_same(){
        assertEquals(samplePlayer.equals(samplePlayer), CuT.equals(CuT));
    }

    @Test
    public void test_equals_same_but_different_objects(){
        assertEquals(samplePlayer.equals(CuT), CuT.equals(samplePlayer));
    }

    @Test
    public void test_different_objects(){
        ArrayList<String> a = new ArrayList<>();
        assertEquals(a.equals(CuT), CuT.equals(a));
    }
}
