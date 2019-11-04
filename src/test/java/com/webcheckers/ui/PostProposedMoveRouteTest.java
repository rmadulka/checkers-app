package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import spark.*;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class PostProposedMoveRouteTest {
    /** Friendly Object */
    private PostProposedMoveRoute CuT;

    /** Mock Objects */
    private PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private Response response;
    private GameLobby gameLobby;

    /** static objects */
    private static Board board;
    private static Player playerRed;
    private static Player playerWhite;

    /**
     * Sets up all the static objects
     */
    @BeforeAll
    public static void init () {
        playerRed = new Player("red");
        playerWhite = new Player("white");
        board = new Board(playerRed, playerWhite);
    }

    /**
     * Sets up the mock objects
     */
    @BeforeEach
    public void setUp() {
        playerLobby = mock(PlayerLobby.class);
        gameLobby = mock(GameLobby.class);
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        this.CuT = new PostProposedMoveRoute(playerLobby);
    }

    /**
     * Tests that we can have a valid move
     */
    @Test
    public void testNewMoveRoute() {
        playerRed.setTurnStack(new Stack<>());
        when(session.attribute("currentUser")).thenReturn(playerRed);
        when(playerLobby.getGameLobby(playerRed)).thenReturn(gameLobby);
        when(gameLobby.getBoard()).thenReturn(board);
        when(request.queryParams("actionData")).thenReturn("{\"start\":{\"row\":0,\"cell\":1},\"end\":{\"row\":1,\"cell\":2}}");
        Message m = Message.info("Move is Valid");
        assertEquals(CuT.handle(request, response), new Gson().toJson(m));
    }

    /**
     * Tests that we can have an invalid move
     */
    @Test
    public void testInvalidMove() {
        playerRed.setTurnStack(new Stack<>());
        when(session.attribute("currentUser")).thenReturn(playerRed);
        when(playerLobby.getGameLobby(playerRed)).thenReturn(gameLobby);
        when(gameLobby.getBoard()).thenReturn(board);
        when(request.queryParams("actionData")).thenReturn("{\"start\":{\"row\":0,\"cell\":1},\"end\":{\"row\":1,\"cell\":6}}");
        Message m = Message.error("Invalid Move");
        assertEquals(CuT.handle(request, response), new Gson().toJson(m));
    }
}
