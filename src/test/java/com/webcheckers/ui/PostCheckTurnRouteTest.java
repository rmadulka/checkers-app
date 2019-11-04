package com.webcheckers.ui;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostCheckTurnRouteTest {


        /** friendly object */
        private PostCheckTurnRoute CuT;


        /** Mock Objects */
        private Request request;
        private Session session;
        private TemplateEngine engine;
        private Response response;
        private PlayerLobby playerLobby;
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
            CuT = new PostCheckTurnRoute(playerLobby);
            gameLobby = mock(GameLobby.class);

        }

        @Test
        /**
         * Tests whether the message output is correct when it is the user's turn
         */
        public void test_your_turn(){
            Player p1 = new Player("Joe Mama");
            Player p2 = new Player("Mike Hawk");
            GameLobby gl = new GameLobby(p1, p2);
            Board b = new Board(p1,p2);

            when(session.attribute("currentUser")).thenReturn(p1);
            when(playerLobby.getGameLobby(p1)).thenReturn(gl);
            assertEquals(CuT.handle(request,response), "{\"text\":\"true\",\"type\":\"INFO\"}");


        }

        @Test
        /**
         * Tests whether the message output is correct when it is the opponent's turn
         */
        public void test_opponent_turn(){
            Player p1 = new Player("Joe Mama");
            Player p2 = new Player("Mike Hawk");
            GameLobby gl = new GameLobby(p1, p2);
            Board b = new Board(p1,p2);
            b.switchTurn();
            gl.setBoard(b);

            when(session.attribute("currentUser")).thenReturn(p1);
            when(playerLobby.getGameLobby(p1)).thenReturn(gl);
            assertEquals(CuT.handle(request,response), "{\"text\":\"false\",\"type\":\"INFO\"}");

        }
}
