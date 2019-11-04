package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
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

@Tag("UI-tier")
public class GetHomeRouteTest {

    private GetHomeRoute CuT;

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

        CuT = new GetHomeRoute(engine, playerLobby);
    }

    /**
     * Test that CuT shows the Home view when the session is brand new.
     */
    @Test
    public void new_session() {

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetHomeRoute.HOME_TITLE, GetHomeRoute.HOME_TITLE_TEXT);
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

    /**
     * Test the players listed
     */
    @Test
    public void PlayerListDisplayed(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        HashSet<Player> players = new HashSet<>();

        players.add(mock(Player.class));
        players.add(mock(Player.class));
        players.add(mock(Player.class));

        when(playerLobby.getPlayers()).thenReturn(players);

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYERS_ONLINE, players);
    }

    /**
     * Test that the number of players is properly accessed
     */
    @Test
    public void numPlayersDisplayed() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(playerLobby.getNumPlayers()).thenReturn(3);

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(GetHomeRoute.NUM_PLAYERS, 3);
    }

    /**
     * Test user exists
     */
    @Test
    public void currentUser(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player player = new Player("name");
        when(session.attribute(GetHomeRoute.CURRENT_USER)).thenReturn(player);

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(GetHomeRoute.CURRENT_USER, player);
    }

    /**
     * Test that CuT shows welcome message
     */
    @Test
    public void initialMessage(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(GetHomeRoute.MESSAGE, GetHomeRoute.WELCOME_MSG);
    }

    /**
     * Test that CuT shows custom messages
     */
    @Test
    public void customMessage(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Message message = Message.info("error");
        when(session.attribute(GetHomeRoute.MESSAGE)).thenReturn(message);

        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(GetHomeRoute.MESSAGE, message);
    }

    /**
     * Test game redirect if a user is in a game
     */
    @Test
    public void gameRedirect(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute(GetHomeRoute.CURRENT_USER)).thenReturn(player);
        when(player.getInGame()).thenReturn(true);

        try {
            CuT.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        } catch (HaltException e) {

        }

        verify(response).redirect(WebServer.GAME_URL);
    }
}
