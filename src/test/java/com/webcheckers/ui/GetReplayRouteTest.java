package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayLobby;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.HaltException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import java.util.HashSet;
import org.junit.jupiter.api.Tag;

@Tag("UI-tier")

public class GetReplayRouteTest {
    public static final String TITLE = "title";
    public static final String TITLE_ATTR = "Replay";
    public static final String VIEW_NAME = "replay.ftl";
    public static final String CURRENT_USER = "currentUser";
    public static final String GAMES = "games";

    private GetReplayRoute CuT;
    private PlayerLobby playerLobby;
    // mock objects
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

        CuT = new GetReplayRoute(engine, playerLobby);
    }

    /**
     * Tests the layout of the ReplayRoute page when there are no games available to replay
     */
    @Test
    public void no_games_played(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        player = new Player("Joe mama");
        ReplayLobby replayLobby = new ReplayLobby();

        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute("currentUser")).thenReturn(player);
        when(playerLobby.getReplayLobby()).thenReturn(replayLobby);

        CuT.handle(request, response);
        testHelper.assertViewModelAttribute(TITLE, TITLE_ATTR);
        testHelper.assertViewModelAttribute(CURRENT_USER, player);

     }

    /**
     * Tests the layout of the ReplayRoute page when there are games available to replay
     */
     @Test
    public void games_played(){
         final TemplateEngineTester testHelper = new TemplateEngineTester();
         player = new Player("Joe mama");
         Player player2 = new Player("Mike Hawk");
         ReplayLobby replayLobby = new ReplayLobby();
         Game game = new Game(player, player2);
         replayLobby.addGame(game);

         when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
         when(session.attribute("currentUser")).thenReturn(player);
         when(playerLobby.getReplayLobby()).thenReturn(replayLobby);

         CuT.handle(request, response);
         testHelper.assertViewModelAttribute(TITLE, TITLE_ATTR);
         testHelper.assertViewModelAttribute(CURRENT_USER, player);
         testHelper.assertViewModelAttribute(GAMES, replayLobby.getGames());

     }


    }