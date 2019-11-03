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
import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-TIER")
public class BackupMoveRouteTest {

    // mock objects
    private BackupMoveRoute CuT;
    private PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Response response;
    private Player player;
    private Player player2;
    private Board board;
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
        player = mock(Player.class);
        player2 = mock(Player.class);
        board = mock(Board.class);
        CuT = new BackupMoveRoute(playerLobby);
        gameLobby = mock(GameLobby.class);
    }

    @Test
    /**
     * Tests whether the correct message is outputted when a simple move has been undone
     */
    public void test_simple_move_completed(){
        Stack<Move> validatedMoves = new Stack<>();
        Move simpleMove = new Move(new Position(0,1), new Position(1,2));
        validatedMoves.push(simpleMove);
        player = new Player("Joseph Mama");
        player2 = new Player("Mike Hawk");
        GameLobby gl = new GameLobby(player, player2);
        gl.getRedPlayer().setTurnStack(validatedMoves);
        Board b = new Board(player,player2);

        when(session.attribute("currentUser")).thenReturn(player);
        when(playerLobby.getGameLobby(player)).thenReturn(gl);
        when(gameLobby.getBoard()).thenReturn(b);
        assertEquals(CuT.handle(request, response), "{\"text\":\"A Simple Move has been undone\",\"type\":\"INFO\"}");
    }

    @Test
    /**
     * Tests whether the correct message is outputted when a jump move has been undone
     */
    public void test_jump_move_completed(){
        Stack<Move> validatedMoves = new Stack<>();
        Move simpleMove = new Move(new Position(0,1), new Position(2,3));
        validatedMoves.push(simpleMove);
        player = new Player("Joseph Mama");
        player2 = new Player("Mike Hawk");
        GameLobby gl = new GameLobby(player, player2);
        gl.getRedPlayer().setTurnStack(validatedMoves);
        Board b = new Board(player,player2);


        when(session.attribute("currentUser")).thenReturn(player);
        when(playerLobby.getGameLobby(player)).thenReturn(gl);
        when(gameLobby.getBoard()).thenReturn(b);
        assertEquals(CuT.handle(request, response), "{\"text\":\"A Jump Move has been undone\",\"type\":\"INFO\"}");
    }

    @Test
    /**
     * Tests whether the correct message is outputted when the TurnStack is empty
     */
    public void test_empty_stack(){
        Stack<Move> validatedMoves = new Stack<>();
        player = new Player("Joseph Mama");
        player2 = new Player("Mike Hawk");
        GameLobby gl = new GameLobby(player, player2);
        gl.getRedPlayer().setTurnStack(validatedMoves);
        Board b = new Board(player,player2);

        when(session.attribute("currentUser")).thenReturn(player);
        when(playerLobby.getGameLobby(player)).thenReturn(gl);
        when(gameLobby.getBoard()).thenReturn(b);
        assertEquals(CuT.handle(request, response), "{\"text\":\"No moves have been made to undo\",\"type\":\"ERROR\"}");

    }
}
