package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayLobby;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-TIER")
public class PostNextTurnRouteTest {

    private PostNextTurnRoute CuT;
    private PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Response response;
    private Player player;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        player = mock(Player.class);
        CuT = new PostNextTurnRoute(playerLobby);
    }

    /**
     * Tests when the next button should be enabled
     */
     @Test
    public void valid_next_turn(){
        player = new Player("joe mama");
        Player player2 = new Player("Mike Hawk");
        ReplayLobby replayLobby = new ReplayLobby();
        Game game = new Game(player, player2);
        Message m = Message.info("true");
        Board board = new Board(player2, player);
        BoardState boardState = new BoardState(board.getActiveColor());
        boardState.constructState(board);
        game.addGameState(boardState);
        game.addGameState(boardState);
        replayLobby.addGame(game);


        when(session.attribute("currentUser")).thenReturn(player);
        when(request.queryParams("gameID")).thenReturn(String.valueOf(game.getId()));
        when(playerLobby.getReplayLobby()).thenReturn(replayLobby);
        assertEquals(CuT.handle(request, response), new Gson().toJson(m));

    }

    /**
     * Tests when the next button should be disabled
     */
    @Test
    public void invalid_next_turn(){
        player = new Player("joe mama");
        Player player2 = new Player("Mike Hawk");
        ReplayLobby replayLobby = new ReplayLobby();
        Game game = new Game(player, player2);
        Message m = Message.info("false");
        replayLobby.addGame(game);

        when(session.attribute("currentUser")).thenReturn(player);
        when(request.queryParams("gameID")).thenReturn(String.valueOf(game.getId()));
        when(playerLobby.getReplayLobby()).thenReturn(replayLobby);
        assertEquals(CuT.handle(request, response), new Gson().toJson(m));

    }


    }