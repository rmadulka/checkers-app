package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
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
    private Space[][] board;

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
    /**
     * Tests when the user has made valid moves and that the correct message has been outputted
     */
    public void test_valid_move_message(){
        Message m =  Message.info("Valid Turn");
        player = new Player("Joe Mama");
        Player player2 = new Player("Mike Hawk");
        Stack<Move> validatedMoves = new Stack<>();
        Move simpleMove = new Move(new Position(0,1), new Position(1,2));
        validatedMoves.push(simpleMove);
        GameLobby gl = new GameLobby(player, player2);
        gl.getRedPlayer().setTurnStack(validatedMoves);

        when(session.attribute("currentUser")).thenReturn(player);
        when(playerLobby.getGameLobby(player)).thenReturn(gl);
        assertEquals(CuT.handle(request, response), new Gson().toJson(m));

    }

    @Test
    /**
     * Tests when there is an invalid move and determines whether the right message is outputted
     */
    public void test_invalid_move_message(){
        Message m = Message.error("Invalid: There is a jump move available");
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
        assertEquals(CuT.handle(request, response), new Gson().toJson(m));

    }

    /**
     * Helper function that constructs and organizes the board spaces
     */
    private void init() {
        board = new Space[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row % 2 == 0) {
                    if (col % 2 == 0) {
                        board[row][col] = new Space(col, false);
                    }else {
                        board[row][col] = new Space(col, true);
                    }
                } else {
                    if (col % 2 == 1) board[row][col] = new Space(col, false);
                    else board[row][col] = new Space(col, true);
                }
            }
        }
    }

    /**
     * Helper function that constructs the board with only red pieces
     */
    public void populateNoWhitePieces(){
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                    if (board[row][col].isValid()) {
                        board[row][col].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
                    }
            }
        }
    }

    @Test
    /**
     * Tests the board when there is no possible moves or pieces remaining
     */
    public void test_no_available_pieces(){
        Message message = Message.info("Valid Turn");
        player = new Player("Joe Mama");
        Player player2 = new Player("Mike Hawk");
        Stack<Move> validatedMoves = new Stack<>();
        Move simpleMove = new Move(new Position(0,1), new Position(0,7));
        validatedMoves.push(simpleMove);
        GameLobby gl = new GameLobby(player, player2);
        gl.getRedPlayer().setTurnStack(validatedMoves);
        Board b = new Board(player, player2);
        init();
        populateNoWhitePieces();
        b.setBoard(board);
        gl.setBoard(b);

        when(session.attribute("currentUser")).thenReturn(player);
        when(playerLobby.getGameLobby(player)).thenReturn(gl);
        assertEquals(CuT.handle(request, response), new Gson().toJson(message));
    }

}
