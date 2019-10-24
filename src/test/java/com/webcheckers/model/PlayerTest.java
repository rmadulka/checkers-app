package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class PlayerTest {

    private Player CuT;
    public static final boolean status = false;
    public static final String name = "Joe Mama";
    public static final Player samplePlayer = new Player(name);


    @BeforeEach
    public void setup(){
        CuT = new Player(name);
    }

    @Test
    public void test_get_name(){
        assertNotNull(CuT.getName());
        assertEquals(samplePlayer.getName(), CuT.getName());

    }
    @Test
    public void test_get_in_game(){
        assertEquals(samplePlayer.getInGame(), CuT.getInGame());
    }

    @Test
    public void test_set_in_game(){
        samplePlayer.setInGame(true);
        assertNotEquals(samplePlayer.getInGame(), CuT.getInGame());
    }
    
}
