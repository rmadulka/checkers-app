package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@Tag("UI-tier")
public class GetGameRouteTest {

    /** Names of the view model values */
    static final String RED_PLAYER = "redPlayer";
    static final String WHITE_PLAYER = "whitePlayer";
    static final String ACTIVE_COLOR = "activeColor";
    static final String VIEW_MODE = "viewMode";

    // mock objects
    private GetGameRoute CuT;
    private PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Response response;
    private Player player1;
    private Player player2;
    private GameLobby gameLobby;

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
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        CuT = new GetGameRoute(engine, playerLobby);
        gameLobby = mock(GameLobby.class);
    }

    /**
     * Tests how the view model is normally set up
     */
    @Test
    public void new_session(){
        Player p1 = new Player("Joe mama");
        Player p2 = new Player("Mike hawk");
        GameLobby gl = new GameLobby(p1,p2);
        final TemplateEngineTester testHelper = new TemplateEngineTester();

        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams("receiver")).thenReturn(p2.getName());
        when(session.attribute("currentUser")).thenReturn(p1);
        when(playerLobby.getGameLobby(p1)).thenReturn(gl);

        CuT.handle(request,response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelAttribute(GetHomeRoute.CURRENT_USER, p1);
        testHelper.assertViewModelAttribute(GetHomeRoute.HOME_TITLE, "Checkers Game");
        testHelper.assertViewModelAttribute(RED_PLAYER, gl.redPlayer);
        testHelper.assertViewModelAttribute(WHITE_PLAYER, gl.whitePlayer);
        testHelper.assertViewModelAttribute(VIEW_MODE, GetGameRoute.viewMode.PLAY);
        testHelper.assertViewModelAttribute(ACTIVE_COLOR, gl.getBoard().getActiveColor());

    }

    /**
     * Tests when a user is in a game and another user is redirected back to home
     */
    @Test
    public void user_in_game(){
        player1 = new Player("Joe mama");
        player2 = new Player("Mike hawk");
        player1.removeInGameStatus();
        GameLobby gl = new GameLobby(player1,player2);
        gl.removePlayer(player1);
        final TemplateEngineTester testHelper = new TemplateEngineTester();

        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams("receiver")).thenReturn(player2.getName());
        when(session.attribute("currentUser")).thenReturn(player1);
        when(playerLobby.getGameLobby(player1)).thenReturn(gl);
        when(playerLobby.getPlayer(player2.getName())).thenReturn(player2);

        try {
            CuT.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        } catch (HaltException e) {
            // expected
        }

        verify(response).redirect(WebServer.HOME_URL);
    }

    @Test
    /**
     * Tests when both users are not currently in a game
     */
    public void both_not_in_game(){
        Player p1 = new Player("Joe mama");
        Player p2 = new Player("Mike hawk");
        gameLobby = new GameLobby(p1, p2);
        gameLobby.removePlayer(p1);
        gameLobby.removePlayer(p2);
        final TemplateEngineTester testHelper = new TemplateEngineTester();

        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams("receiver")).thenReturn(p2.getName());
        when(session.attribute("currentUser")).thenReturn(p1);
        when(playerLobby.getPlayer(p2.getName())).thenReturn(p2);
        when(playerLobby.startGame(p1,p2)).thenReturn(gameLobby);

        CuT.handle(request,response);
        testHelper.assertViewModelAttribute(RED_PLAYER, p1);
        testHelper.assertViewModelAttribute(WHITE_PLAYER, p2);


    }

}
