package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

@Tag("UI-tier")
public class PostSignoutRouteTest {

    // mock objects
    private PostSignoutRoute CuT;
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
        CuT = new PostSignoutRoute(playerLobby);
    }

    @Test
    /**
     * Tests when the user is not currently not in game and the signout is successfully done
     */
    public void check_successful_signout(){
        player = new Player("Joseph Mama");
        playerLobby.addPlayer(player);
        Player p1 = new Player("lol");
        p1.removeInGameStatus();
        when(request.queryParams("currentUser")).thenReturn(p1.getName());
        when(playerLobby.getPlayer(p1.getName())).thenReturn(p1);

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
     * Tests when the player is in game and they cannot sign out
     */
    public void invalid_in_game_signout(){
        player = new Player("Joseph Mama");
        Player p1 = new Player("lol");
        p1.addInGameStatus();
        when(request.queryParams("currentUser")).thenReturn(p1.getName());
        when(playerLobby.getPlayer(p1.getName())).thenReturn(p1);
        when(session.attribute(GetHomeRoute.MESSAGE_ATR)).thenReturn(Message.error("Cannot sign-out mid game"));

        try {
            CuT.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        } catch (HaltException e) {
            // expected
        }
        verify(response).redirect(WebServer.GAME_URL);
    }


}
