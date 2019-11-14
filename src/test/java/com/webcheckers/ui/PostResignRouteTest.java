package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostResignRouteTest {

    /** Friendly Objects */
    private PostResignRoute CuT;

    /** Mock objects */
    private PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private Response response;
    private GameLobby gameLobby;
    private Player playerRed;

    /**
     * Sets up all the mock and friendly objects
     */
    @BeforeEach
    public void setUp() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gameLobby = mock(GameLobby.class);
        playerRed = mock(Player.class);
        playerLobby = mock(PlayerLobby.class);
        CuT = new PostResignRoute(playerLobby);
    }

    /**
     * Tests that a game has ended properly after a resign
     */
//    @Test
//    public void testNewResignRoute() {
//        when(session.attribute("currentUser")).thenReturn(playerRed);
//        when(playerLobby.getGameLobby(playerRed)).thenReturn(gameLobby);
//        when(gameLobby.getIsGameOver()).thenReturn(false);
//        Message m = Message.info("Game has ended");
//        assertEquals(CuT.handle(request, response), new Gson().toJson(m));
//    }

    /**
     * Tests that a game improperly ended after a player resigns
     */
    @Test
    public void testInvalid() {
        when(session.attribute("currentUser")).thenReturn(playerRed);
        when(playerLobby.getGameLobby(playerRed)).thenReturn(gameLobby);
        when(gameLobby.getIsGameOver()).thenReturn(true);
        Message m = Message.error("Game has already ended");
        assertEquals(CuT.handle(request, response), new Gson().toJson(m));
    }
}
