package com.webcheckers.util;

import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.BaseStubbing;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


public class MoveProcessorTest {

    private Board board;

    private Move move;

    private Player player;

    @BeforeEach
    public void init() {
        board = mock(Board.class);
        move = mock(Move.class);
        player = mock(Player.class);
    }

    /**
     * Test that no move can be made after a simple move
     */
    @Test
    public void invalidMoveCappedSimpleMove(){
        when(player.getTurnStack()).thenReturn(new Stack<>());
        Stack<Move> turnStack = player.getTurnStack();

        Move prevMove = mock(Move.class);
        turnStack.push(prevMove);

        when(prevMove.isJumpMove()).thenReturn(false);

        assertFalse(MoveProcessor.validateMove(move, board, player));
    }

    /**
     * Test that a simple move can not be made after a jump move
     */
    @Test
    public void invalidMoveJumpThenSimple(){
        when(player.getTurnStack()).thenReturn(new Stack<>());
        Stack<Move> turnStack = player.getTurnStack();

        Move prevMove = mock(Move.class);
        turnStack.push(prevMove);

        when(prevMove.isJumpMove()).thenReturn(true);
        when(move.isJumpMove()).thenReturn(false);

        assertFalse(MoveProcessor.validateMove(move, board, player));
    }

    /**
     * Tests that a simple move is valid
     */
    @Test
    public void validMoveSimple(){

        Stack turnStack = mock(Stack.class);

        when(player.getTurnStack()).thenReturn(turnStack);

        when(turnStack.empty()).thenReturn(true);

        move = new Move(new Position(2,1),new Position(3,2));

        board = new Board(new Player("a"), new Player("b"));

        assertTrue(MoveProcessor.validateMove(move, board, player));
    }

    /**
     * Test that a turn stack with a simple move is valid
     * given that no jump moves are available
     */
    @Test
    public void validSimpleTurn(){

        Stack<Move> turnStack = new Stack<>();

        when(player.getTurnStack()).thenReturn(turnStack);

        move = new Move(new Position(2,1),new Position(3,2));

        turnStack.push(move);

        board = new Board(new Player("a"), new Player("b"));

        assertTrue(MoveProcessor.validateTurn(turnStack, board));
    }

    /**
     * Test that a turn stack with a simple move is invalid
     * given that a jump move is available
     */
    @Test
    public void invalidSimpleTurn(){

        Stack<Move> turnStack = new Stack<>();

        when(player.getTurnStack()).thenReturn(turnStack);

        move = new Move(new Position(2,3),new Position(3,4));

        turnStack.push(move);

        board = new Board(new Player("a"), new Player("b"));

        board.getBoard()[3][2].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));

        assertFalse(MoveProcessor.validateTurn(turnStack, board));
    }

    /**
     * Tests that a jump move is valid in the turn stack
     */
    @Test
    public void validJumpTurn(){

        Stack<Move> turnStack = new Stack<>();

        when(player.getTurnStack()).thenReturn(turnStack);

        move = new Move(new Position(2,3),new Position(4,1));

        turnStack.push(move);

        board = new Board(new Player("a"), new Player("b"));

        board.getBoard()[3][2].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));

        assertTrue(MoveProcessor.validateTurn(turnStack, board));
    }

    /**
     * Test that a jump move is invalid given that another jump move is available after
     */
    @Test
    public void invalidJumpTurn(){

        Stack<Move> turnStack = new Stack<>();

        when(player.getTurnStack()).thenReturn(turnStack);

        move = new Move(new Position(2,3),new Position(4,1));

        turnStack.push(move);

        board = new Board(new Player("a"), new Player("b"));

        board.getBoard()[3][2].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));

        board.getBoard()[6][3].removePiece();

        assertFalse(MoveProcessor.validateTurn(turnStack, board));
    }

    @Test
    public void processMoves(){

        Stack<Move> turnStack = new Stack<>();

        when(player.getTurnStack()).thenReturn(turnStack);

        move = new Move(new Position(2,3),new Position(3,2));

        board = new Board(new Player("a"), new Player("b"));

        turnStack.push(move);

        assertNull(board.getBoard()[3][2].getPiece());
        assertFalse(turnStack.empty());

        MoveProcessor.processMoves(player, board);

        assertNotNull(board.getBoard()[3][2].getPiece());
        assertTrue(turnStack.empty());
    }

    /**
     * Test that the temporary board will refresh when a move is undone
     */
    @Test
    public void tempBoardRefresh(){

        Stack<Move> turnStack = new Stack<>();

        when(player.getTurnStack()).thenReturn(turnStack);

        move = new Move(new Position(2,3),new Position(3,2));

        board = new Board(new Player("a"), new Player("b"));

        MoveProcessor.temporaryBoard = new Board(board);

        MoveProcessor.temporaryBoard.makeTempMove(move);

        assertNotNull(MoveProcessor.temporaryBoard.getBoard()[3][2].getPiece());

        MoveProcessor.refreshTempBoard(board, player);

        assertNull(MoveProcessor.temporaryBoard.getBoard()[3][2].getPiece());
    }
}
