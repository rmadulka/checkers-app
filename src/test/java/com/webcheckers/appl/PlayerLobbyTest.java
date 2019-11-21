package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Application-tier")
public class PlayerLobbyTest {

    /** Friendly Objects */
    private PlayerLobby CuT;

    /** Mock Objects */
    private Player playerRed;
    private Player playerWhite;
    private Player playerRed2;
    private Player playerWhite2;

    /**
     * Set up mock players and a new playerlobby object to test the PlayerLobby class
     */
    @BeforeEach
    public void setUp(){
        playerRed = mock(Player.class);
        playerWhite = mock(Player.class);
        playerRed2 = mock(Player.class);
        playerWhite2 = mock(Player.class);
        CuT = new PlayerLobby();
        CuT.addPlayer(playerRed);
        CuT.addPlayer(playerWhite);
    }

    /**
     * Tests that we can construct a PlayerLobby class
     */
    @Test
    public void testCreatePlayerLobby(){
        new PlayerLobby();
    }

    /**
     * Test that the initial players is the lobby is two given that we added two players in the setup
     */
    @Test
    public void testInitPlayerInLobby(){
        assertEquals(2, CuT.getNumPlayers());
    }

    /**
     * Tests that there is a hashset of players
     */
    @Test
    public void testGetPlayers(){
        assertNotNull(CuT.getPlayers());
    }

    /**
     * Tests that we can start a game between two players that are in the PlayerLobby hashset
     */
    @Test
    public void testStartGame(){
        assertNotNull(CuT.startGame(playerRed, playerWhite));
    }

    /**
     * Tests if certain players are currently in a game against each other
     */
    @Test
    public void testGetGameLobby(){
        CuT.addPlayer(playerRed2);
        CuT.addPlayer(playerWhite2);
        CuT.startGame(playerRed, playerWhite);
        assertSame(CuT.getGameLobby(playerRed), CuT.getGameLobby(playerWhite));
        CuT.startGame(playerRed2, playerWhite2);
        assertNotSame(CuT.getGameLobby(playerWhite2), CuT.getGameLobby(playerRed));
        assertNotSame(CuT.getGameLobby(playerRed2), CuT.getGameLobby(playerWhite));
        assertSame(CuT.getGameLobby(playerRed2), CuT.getGameLobby(playerWhite2));
    }

    /**
     * Tests if a player is not currently in a game
     */
    @Test
    public void testGameLobbyNull(){
        assertNull(CuT.getGameLobby(playerRed));
        assertNull(CuT.getGameLobby(playerWhite));
    }

    /**
     * Tests if we can add players to the player hash
     */
    @Test
    public void testAddingPlayers() {
        CuT.addPlayer(playerRed2);
        assertEquals(3, CuT.getNumPlayers());
        CuT.addPlayer(playerWhite2);
        assertEquals(4, CuT.getNumPlayers());
    }

    /**
     * Tests if we can get the correct player using the players name
     */
    @Test
    public void testGetPlayer () {
        when(playerRed.getName()).thenReturn("Name");
        assertSame(playerRed, CuT.getPlayer(playerRed.getName()));
        assertNotSame(playerWhite, CuT.getPlayer(playerRed.getName()));
        assertNotNull(CuT.getPlayer("Name"));
        assertNull(CuT.getPlayer("test"));
    }

    /**
     * Tests if the username the user enters is valid
     */
    @Test
    public void testCheckUsername() {
        when(playerRed.getName()).thenReturn("Name");
        when(playerWhite.getName()).thenReturn("*");
        assertSame(PlayerLobby.signinErrors.VALID, CuT.checkUsername(playerRed.getName()));
        assertSame(PlayerLobby.signinErrors.ALPHA, CuT.checkUsername(playerWhite.getName()));
    }

    /**
     * Tests that we can properly remove a player
     */
    @Test
    public void testRemovePlayer() {
        when(playerRed.getName()).thenReturn("Foo");
        assertSame(playerRed, CuT.getPlayer(playerRed.getName()));
        CuT.removePlayer(playerRed);
        assertNotSame(playerRed, CuT.getPlayer(playerRed.getName()));
    }

    /**
     * Tests that we can properly remove a GameLobby
     */
    @Test
    public void testRemoveGameLobby() {
        CuT.startGame(playerRed, playerWhite);
        assertNotNull(CuT.getGameLobby(playerRed));
        CuT.removeGameLobby(CuT.getGameLobby(playerRed));
        assertNull(CuT.getGameLobby(playerRed));
    }

    /**
     * Tests that we can get the replay lobby
     */
    @Test
    public void testGetReplayLobby () {
        assertNotNull(CuT.getReplayLobby());
    }
}
