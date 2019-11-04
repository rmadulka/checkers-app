package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckOneJumpMoveTest {

  /**
   * Friendly object
   */
  private CheckOneJumpMove CuT;

  /**
   * Mock player object
   */
  private static Player redPlayer;

  /**
   * Mock player object
   */
  private static Player whitePlayer;

  /**
   * Mock board object
   */
  private static Board board;

  @BeforeEach
  private void init() {
    CuT = new CheckOneJumpMove();
    redPlayer = new Player("Red");
    whitePlayer = new Player("White");
    board = new Board(redPlayer, whitePlayer);
  }

  @Test
  public void testRedHasNoJumpMove() {
    Position start = new Position(0, 0);
    Space jumping = board.getSpace(new Position(1, 1));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(2, 2);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Move move = new Move(start, end);
    assertFalse(CuT.checkMultiJump(move, board));
    assertFalse(CuT.checkMoves(move, board));
  }

  @Test
  public void testWhiteHasNoJumpMove() {
    board.switchTurn();
    Position start = new Position(5, 7);
    Space jumping = board.getSpace(new Position(4, 6));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Position end = new Position(3, 5);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Move move = new Move(start, end);
    assertFalse(CuT.checkMultiJump(move, board));
    assertFalse(CuT.checkMoves(move, board));
  }

  @Test
  public void testRedHasOneJumpMoveRight() {
    Position start = new Position(0, 0);
    Space jumping = board.getSpace(new Position(1, 1));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(2, 2);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(3,3));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    assertTrue(CuT.checkMultiJump(move, board));
    assertTrue(CuT.checkMoves(move, board));
  }

  @Test
  public void testRedHasOneMoveJumpLeft() {
    Position start = new Position(0, 0);
    Space jumping = board.getSpace(new Position(1, 1));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(2, 2);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(3, 1));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    assertTrue(CuT.checkMultiJump(move, board));
    assertTrue(CuT.checkMoves(move, board));
  }

  @Test
  public void testWhiteHasOneJumpMoveRight() {
    board.switchTurn();
    Position start = new Position(7, 7);
    Space jumping = board.getSpace(new Position(6, 6));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Position end = new Position(5, 5);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(4,4));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    assertTrue(CuT.checkMultiJump(move, board));
    assertTrue(CuT.checkMoves(move, board));
  }

  @Test
  public void testWhiteHasOneMoveJumpLeft() {
    board.switchTurn();
    Position start = new Position(7, 7);
    Space jumping = board.getSpace(new Position(6, 6));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Position end = new Position(5, 5);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(4, 6));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    assertTrue(CuT.checkMultiJump(move, board));
    assertTrue(CuT.checkMoves(move, board));
  }

  @Test
  public void testRedWrongColorNextJump() {
    Position start = new Position(0, 0);
    Space jumping = board.getSpace(new Position(1, 1));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(2, 2);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(3,3));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    assertFalse(CuT.checkMultiJump(move, board));
    assertFalse(CuT.checkMoves(move, board));
  }

  @Test
  public void testWhiteWrongColorNextJump() {
    board.switchTurn();
    Position start = new Position(7, 7);
    Space jumping = board.getSpace(new Position(6, 6));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Position end = new Position(5, 5);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(4,4));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    assertFalse(CuT.checkMultiJump(move, board));
    assertFalse(CuT.checkMoves(move, board));
  }

  @Test
  public void testRedBlockedNextJump() {
    Position start = new Position(0, 0);
    Space jumping = board.getSpace(new Position(1, 1));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(2, 2);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(3, 1));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Space blocking = board.getSpace(new Position(4, 0));
    blocking.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    assertFalse(CuT.checkMultiJump(move, board));
    assertFalse(CuT.checkMoves(move, board));
  }

  @Test
  public void testWhiteBlockedNextJump() {
    board.switchTurn();
    Position start = new Position(7, 7);
    Space jumping = board.getSpace(new Position(6, 6));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Position end = new Position(5, 5);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(4, 6));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Space blocking = board.getSpace(new Position(3, 7));
    blocking.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    assertFalse(CuT.checkMultiJump(move, board));
    assertFalse(CuT.checkMoves(move, board));
  }

  @Test
  public void testRedKingNextJumpRight() {
    Position start = new Position(7, 7);
    Space jumping = board.getSpace(new Position(6, 6));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(5, 5);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(4, 4));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    jumping.removePiece();
    assertTrue(CuT.checkMultiJump(move, board));
    assertTrue(CuT.checkMoves(move, board));
  }

  @Test
  public void testRedKingNextJumpLeft() {
    Position start = new Position(7, 7);
    Space jumping = board.getSpace(new Position(6, 6));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(5, 5);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(4, 6));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    jumping.removePiece();
    assertTrue(CuT.checkMultiJump(move, board));
    assertTrue(CuT.checkMoves(move, board));
  }

  @Test
  public void testWhiteKingNextJumpRight() {
    board.switchTurn();
    Position start = new Position(0, 0);
    Space jumping = board.getSpace(new Position(1, 1));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Position end = new Position(2, 2);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.KING, Piece.pieceColor.WHITE));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(3, 3));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    jumping.removePiece();
    assertTrue(CuT.checkMultiJump(move, board));
    assertTrue(CuT.checkMoves(move, board));
  }

  @Test
  public void testWhiteKingNextJumpLeft() {
    board.switchTurn();
    Position start = new Position(0, 0);
    Space jumping = board.getSpace(new Position(1, 1));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Position end = new Position(2, 2);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.KING, Piece.pieceColor.WHITE));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(3, 1));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    jumping.removePiece();
    assertTrue(CuT.checkMultiJump(move, board));
    assertTrue(CuT.checkMoves(move, board));
  }

}
