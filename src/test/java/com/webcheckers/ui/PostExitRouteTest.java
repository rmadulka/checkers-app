package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.*;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
public class PostExitRouteTest {
    // mock objects
        private PostExitGameRoute CuT;
        private PlayerLobby playerLobby;
        private Request request;
        private Session session;
        private Response response;

        /**
         * Setup new mock objects for each test.
         */
        @BeforeEach
        public void setup() {
            request = mock(Request.class);
            session = mock(Session.class);
            when(request.session()).thenReturn(session);
            response = mock(Response.class);
            playerLobby = mock(PlayerLobby.class);

            CuT = new PostExitGameRoute(playerLobby);
        }

    /**
     * Tests whether the correct Gson() message is returned when users are exiting the game
     */
    @Test
        public void test_gson_message(){
            Player p1 = new Player("p1");
            Player p2 = new Player("p2");
            when(session.attribute("currentUser")).thenReturn(p1);
            GameLobby gameLobby = new GameLobby(p1, p2);
            when(playerLobby.getGameLobby(p1)).thenReturn(gameLobby);
            Message m = Message.info("Exiting Game");
            assertEquals(CuT.handle(request, response), new Gson().toJson(m));
        }

    /**
     * Tests how an empty gameLobby is implemented and whether the correct message is returned
     */
    @Test
        public void test_empty_lobby(){
            Player p1 = new Player("p1");
            Player p2 = new Player("p2");
            when(session.attribute("currentUser")).thenReturn(p1);
            GameLobby gameLobby = new GameLobby(p1, p2);
            gameLobby.removePlayer(p2);
            when(playerLobby.getGameLobby(p1)).thenReturn(gameLobby);
            Message m = Message.info("Exiting Game");
            assertEquals(CuT.handle(request, response), new Gson().toJson(m));
        }



}
