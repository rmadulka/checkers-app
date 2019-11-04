package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckAllJumpMoveTest {

  /**
   * Friendly object
   */
  private CheckAllJumpMove CuT;

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
    CuT = new CheckAllJumpMove();
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
    assertFalse(CuT.checkMoves(move, board));
  }

  @Test
  public void testRedHasAtleastOneJumpMoveRight() {
    Position start = new Position(0, 0);
    Space jumping = board.getSpace(new Position(1, 1));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(2, 2);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(3,3));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    assertTrue(CuT.checkMoves(move, board));
  }

  @Test
  public void testRedHasAtleastOneMoveJumpLeft() {
    Position start = new Position(0, 0);
    Space jumping = board.getSpace(new Position(1, 1));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(2, 2);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(3, 1));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    assertTrue(CuT.checkMoves(move, board));
  }

  @Test
  public void testWhiteHasAtleastOneJumpMoveRight() {
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
    assertTrue(CuT.checkMoves(move, board));
  }

  @Test
  public void testWhiteHasAtleastOneMoveJumpLeft() {
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
    assertTrue(CuT.checkMoves(move, board));
  }

  @Test
  public void testRedWrongColorNoNextJump() {
    Position start = new Position(0, 0);
    Space jumping = board.getSpace(new Position(1, 1));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(2, 2);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(3,3));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    assertFalse(CuT.checkMoves(move, board));
  }

  @Test
  public void testWhiteWrongColorNoNextJump() {
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
    assertFalse(CuT.checkMoves(move, board));
  }

  @Test
  public void testRedBlockedNoNextJump() {
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
    blocking.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    assertFalse(CuT.checkMoves(move, board));
  }

  @Test
  public void testWhiteBlockedNoNextJump() {
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
    assertFalse(CuT.checkMoves(move, board));
  }

  @Test
  public void testRedKingHasNextJump() {
    Position start = new Position(7, 7);
    Space jumping = board.getSpace(new Position(6, 6));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(5, 5);
    Space ended = board.getSpace(end);
    ended.place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
    Move move = new Move(start, end);
    Space ahead = board.getSpace(new Position(4, 6));
    ahead.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    assertTrue(CuT.checkMoves(move, board));
  }

  @Test
  public void testWhiteKingHasNextJump() {
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
    assertTrue(CuT.checkMoves(move, board));
  }

}
