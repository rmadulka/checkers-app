package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReplayLobbyTest {
    /** Friendly object */
    private ReplayLobby CuT;

    /** Mock Objects */
    private Player red;
    private Player white;

    /**
     * Sets up mock and friendly objects for each test
     */
    @BeforeEach
    public void setUp() {
        this.red = mock(Player.class);
        this.white = mock(Player.class);
        this.CuT = new ReplayLobby();
    }

    /**
     * Tests that we can get all the games in the list
     */
    @Test
    public void testGetGames() {
        assertEquals(0, CuT.getGames().size());
    }

    /**
     * Tests that we can add games to the list
     */
    @Test
    public void testAddGame() {
        assertEquals(0, CuT.getGames().size());
        Game game = new Game(red, white);
        CuT.addGame(game);
        assertEquals(CuT.getGames().get(0), game);
    }

    /**
     * Tests that we can get a game in the list based on id
     */
    @Test
    public void testGetGame() {
        Game game = new Game(red, white);
        assertNull(CuT.getGame(3));
        CuT.addGame(game);
        assertEquals(game, CuT.getGame(game.getId()));
    }

}
