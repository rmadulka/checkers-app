package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class KingSimpleMoveTest {

    /** Friendly Object */
    private KingSimpleMove CuT;

    /** Move, Board and player Objects */
    private static Move moveRed;
    private static Move moveWhite;
    private static Move single;
    private static Move invalid;
    private static Player red;
    private static Player white;
    private static Board board;
    private static Space [][] game;

    /**
     * Sets up the moves and board
     */
    @BeforeAll
    public static void init() {
        moveRed = new Move(new Position(1, 1), new Position(0,0));
        moveWhite = new Move(new Position(2,2), new Position(3, 3));
        single = new Move (new Position(5,5), new Position(3, 3));
        invalid = new Move (new Position(4,4), new Position(7, 7));
        red = new Player("foo");
        white = new Player("bar");
        board = new Board(red, white);
        game = board.getBoard();
        game[1][1].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
        game[2][2].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.WHITE));
        game[5][5].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
        game[4][4].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
    }

    /**
     * Sets up the CuT object
     */
    @BeforeEach
    public void setUp() {
        this.CuT = new KingSimpleMove();
    }

    /**
     * Tests valid move with a white and red piece
     */
    @Test
    public void testSimpleMove() {
        assertTrue(CuT.validateKingSimpleMove(moveRed, board));
        board.switchTurn();
        assertTrue(CuT.validateKingSimpleMove(moveWhite, board));
    }

    /**
     * Tests when a piece is a single piece
     */
    @Test
    public void testFailingSimpleMoveSingle() {
        assertFalse(CuT.validateKingSimpleMove(single, board));
    }

    /**
     * Tests when a piece makes an invalid simple move
     */
    @Test
    public void testInvalidSimpleMove() {
        assertFalse(CuT.validateKingSimpleMove(invalid, board));
    }

    /**
     * Tests when a piece at the starting position is null
     */
    @Test
    public void testPieceNull() {
        game[1][1].removePiece();
        assertFalse(CuT.validateKingSimpleMove(moveRed, board));
    }

    /**
     * Tests the check move method
     */
    @Test
    public void testCheckMoves() {
        game[1][1].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
        board.switchTurn();
        assertTrue(CuT.checkMoves(moveRed, board));
        assertFalse(CuT.checkMoves(single, board));
    }
}
