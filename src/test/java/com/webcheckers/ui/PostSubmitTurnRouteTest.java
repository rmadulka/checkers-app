package com.webcheckers.ui;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-TIER")
public class PostSubmitTurnRouteTest {

    private PostSubmitTurnRoute CuT;
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
        CuT = new PostSubmitTurnRoute(playerLobby);

    }

    @Test
    public void test_valid_move_message(){
        player = new Player("Joe Mama");
        Player player2 = new Player("Mike Hawk");
        Stack<Move> validatedMoves = new Stack<>();
        Move simpleMove = new Move(new Position(0,1), new Position(1,2));
        validatedMoves.push(simpleMove);
        GameLobby gl = new GameLobby(player, player2);
        gl.getRedPlayer().setTurnStack(validatedMoves);
        Board b = new Board(player,player2);


        when(session.attribute("currentUser")).thenReturn(player);
        when(playerLobby.getGameLobby(player)).thenReturn(gl);
        assertEquals(CuT.handle(request, response), "{\"text\":\"Valid Turn\",\"type\":\"INFO\"}");

    }

    @Test
    public void test_invalid_move_message(){
        player = new Player("Joe Mama");
        Player player2 = new Player("Mike Hawk");
        Stack<Move> validatedMoves = new Stack<>();
        Move simpleMove = new Move(new Position(0,1), new Position(0,7));
        validatedMoves.push(simpleMove);
        GameLobby gl = new GameLobby(player, player2);
        gl.getRedPlayer().setTurnStack(validatedMoves);
        gl.setIsGameOver(true);


        when(session.attribute("currentUser")).thenReturn(player);
        when(playerLobby.getGameLobby(player)).thenReturn(gl);
        assertEquals(CuT.handle(request, response), "{\"text\":\"Invalid: There is a jump move available\",\"type\":\"ERROR\"}");

    }

}
