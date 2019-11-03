package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class SimpleMoveTest {
    /** Friendly object */
    private SimpleMove CuT;

    /** Needed objects */
    private static Move move;
    private static Move move2;
    private static Move move3;
    private static Move move4;
    private static Board board;
    private static Player red;
    private static Player white;
    private static Space[][] game;

    /**
     * Initializes the objects we need to use to test the simpleMove class
     */
    @BeforeAll
    public static void init() {
        move = new Move(new Position(2, 0), new Position(3, 1));
        move2 = new Move(new Position(7, 7), new Position(6, 6));
        move3 = new Move(new Position(5, 5), new Position(3,7)); //impossible move
        move4 = new Move(new Position(6, 5), new Position(7,7)); //impossible move
        red = new Player("foo");
        white = new Player("bar");
        board = new Board(red, white);
        game = board.getBoard();
        game[2][0].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
        game[7][7].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
        game[5][5].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    }

    /**
     * Sets up the friendly object
     */
    @BeforeEach
    public void setUp() {
        this.CuT = new SimpleMove();
    }

    /**
     * Tests each section of the validateSimpleMove method
     */
    @Test
    public void testValidateSimpleMove() {
        assertTrue(CuT.validateSimpleMove(move, board));
        board.switchTurn();
        assertTrue(CuT.validateSimpleMove(move2, board));
        board.switchTurn();
        assertFalse(CuT.validateSimpleMove(move3, board));
        assertFalse(CuT.validateSimpleMove(move4, board));
        game[6][5].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
        assertFalse(CuT.validateSimpleMove(move4, board));
    }

    /**
     * Tests the checkMoves method
     */
    @Test
    public void testCheckMoves() {
        assertTrue(CuT.checkMoves(move, board));
        assertFalse(CuT.checkMoves(move3, board));
    }
}
