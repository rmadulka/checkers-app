package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class SimpleMoveTest {
    private SimpleMove CuT;
    private static Move move;
    private static Move move2;
    private static Board board;
    private static Player red;
    private static Player white;
    private static Space[][] game;

    @BeforeAll
    public static void init() {
        move = new Move(new Position(2, 0), new Position(3, 1));
        move2 = new Move(new Position(7, 7), new Position(6, 6));
        red = new Player("foo");
        white = new Player("bar");
        board = new Board(red, white);
        game = board.getBoard();
        game[2][0].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
        game[7][7].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    }

    @BeforeEach
    public void setUp() {
        this.CuT = new SimpleMove();
    }

    @Test
    public void testValidateSimpleMove() {
        assertTrue(CuT.validateSimpleMove(move, board));
        //assertTrue(CuT.validateSimpleMove(move2, board));
    }
}
