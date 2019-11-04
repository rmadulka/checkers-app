package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import spark.*;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class PostSigninRouteTest {

    /** Friendly Object */
    private PostSigninRoute CuT;
    /** Mock objects */
    private  PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine templateEngine;
    private Player player;

    /**
     * Set up mock objects before each test
     */
    @BeforeEach
    public void setUp () {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        player = mock(Player.class);
        playerLobby = mock(PlayerLobby.class);
        CuT = new PostSigninRoute(templateEngine, playerLobby);
    }

    /**
     * Tests the Name exists switch
     */
    @Test
    public void testNameExists() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams(PostSigninRoute.USER_PARAM)).thenReturn("name");
        when(player.getName()).thenReturn("name");
        when(playerLobby.checkUsername(player.getName())).thenReturn(PlayerLobby.signinErrors.NAMEEXISTS);
        CuT.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetSigninRoute.TITLE, GetSigninRoute.TITLE_ATTR);
    }

    /**
     * Tests the Alpha switch statement
     */
    @Test
    public void testAlpha() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams(PostSigninRoute.USER_PARAM)).thenReturn("*&*^%");
        when(player.getName()).thenReturn("*&*^%");
        when(playerLobby.checkUsername(player.getName())).thenReturn(PlayerLobby.signinErrors.ALPHA);
        CuT.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
    }

    /**
     * Tests the Valid switch statement and the redirect
     */
    @Test
    public void testNameValid() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams(PostSigninRoute.USER_PARAM)).thenReturn("name");
        when(player.getName()).thenReturn("name");
        when(playerLobby.checkUsername(player.getName())).thenReturn(PlayerLobby.signinErrors.VALID);
        try {
            CuT.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        } catch (HaltException e) {

        }
        verify(response).redirect(WebServer.HOME_URL);
    }
}
