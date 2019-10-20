package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.*;

public class GameLobbyTest {

    private GameLobby CuT;
    private Player redPlayer;
    private Player whitePlayer;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);

        CuT = new GameLobby(redPlayer, whitePlayer);
        when(redPlayer.getInGame()).thenReturn(true);
        when(whitePlayer.getInGame()).thenReturn(true);
    }

    /**
     * Test that the players in the GameLobby exist
     */
    @Test
    public void playersExist() {
        assertNotNull(CuT.redPlayer);
        assertNotNull(CuT.whitePlayer);
    }

    /**
     * Tests that the players in the GameLobby are properly set
     */
    @Test
    public void playersProperlySet(){
        assertEquals(CuT.redPlayer, redPlayer);
        assertEquals(CuT.whitePlayer, whitePlayer);
    }


    /**
     * Tests that a Board is created
     */
    @Test
    public void BoardExists(){
        assertNotNull(CuT.getBoard());
    }

    /**
     * Tests that the Board has the proper players
     */
    @Test
    public void BoardHasPlayers(){
        assertEquals(whitePlayer, CuT.getBoard().getWhite());
        assertEquals(redPlayer, CuT.getBoard().getRed());
    }

    /**
     * Tests that the GameLobby tracks a player's opponent
     */
    @Test
    public void opponents(){
        assertEquals(redPlayer,CuT.getOpponent(whitePlayer));
        assertEquals(whitePlayer,CuT.getOpponent(redPlayer));

        assertNotEquals(whitePlayer, CuT.getOpponent(mock(Player.class)));
    }

    /**
     * Tests that a Player is properly put into a game
     */
    @Test
    public void playersInGame(){
        assertTrue(CuT.redPlayer.getInGame());
        assertTrue(CuT.whitePlayer.getInGame());

        assertFalse(mock(Player.class).getInGame());
    }
}
