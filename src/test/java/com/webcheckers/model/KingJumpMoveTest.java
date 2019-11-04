package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KingJumpMoveTest {

  /**
   * Friendly object
   */
  private KingJumpMove CuT;

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

  /**
   * Initialize objects before each test
   */
  @BeforeEach
  public void init() {
    CuT = new KingJumpMove();
    redPlayer = new Player("red");
    whitePlayer = new Player("white");
    board = new Board(redPlayer, whitePlayer);
  }

  /**
   * Tests the backward king jump move
   */
  @Test
  public void kingJumpMoveBackwardsTest() {
    Position start = new Position(7, 1);
    Space king = board.getSpace(start);
    king.place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
    Space jumping = board.getSpace(new Position(6, 2));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(5, 3);
    Move move = new Move(start, end);
    assertTrue(CuT.validateKingJumpMove(move, board));
    assertTrue(CuT.checkMoves(move, board));
  }

  /**
   * Tests a backwards jump over a piece of the same color
   */
  @Test
  public void kingJumpMoveWrongColor() {
    Position start = new Position(5, 3);
    Space king = board.getSpace(start);
    king.place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
    Space jumping = board.getSpace(new Position(4, 6));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Position end = new Position(3, 7);
    Move move = new Move(start, end);
    assertFalse(CuT.validateKingJumpMove(move, board));
    assertFalse(CuT.checkMoves(move, board));
  }

  /**
   * Tests a backwards jump over no piece
   */
  @Test
  public void kingJumpMoveNoCapture() {
    Position start = new Position(5,1);
    Space king = board.getSpace(start);
    king.place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
    Position end = new Position(3, 3);
    Move move = new Move(start, end);
    assertFalse(CuT.validateKingJumpMove(move, board));
    assertFalse(CuT.checkMoves(move, board));
  }

  @Test
  /**
   * tests a normal king jump by a white piece
   */
  public void test_white_king_jump(){
    board.switchTurn();
    Position start = new Position(3,3);
    Space king = board.getSpace(start);
    king.place(new Piece(Piece.pieceType.KING, Piece.pieceColor.WHITE));
    Space jumping = board.getSpace(new Position(4, 2));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Position end = new Position(5, 1);
    Move move = new Move(start, end);
    assertTrue(CuT.validateKingJumpMove(move, board));
    assertTrue(CuT.checkMoves(move, board));
  }

  @Test
  /**
   * tests a jump that is too far and is invalid
   */
  public void test_too_far_of_a_jump(){
    board.switchTurn();
    Position start = new Position(3,3);
    Space king = board.getSpace(start);
    Space jumping = board.getSpace(new Position(4, 2));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    king.place(new Piece(Piece.pieceType.KING, Piece.pieceColor.WHITE));
    Position end = new Position(6, 1);
    Move move = new Move(start, end);
    assertFalse(CuT.validateKingJumpMove(move, board));
  }

  @Test
  /**
   * tests a left diagonal king jump
   */
  public void test_left_diagonal_king_jump(){
    Position start = new Position(3,3);
    Space king = board.getSpace(start);
    king.place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
    Space jumping = board.getSpace(new Position(2, 2));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(1, 1);
    Move move = new Move(start, end);
    assertTrue(CuT.validateKingJumpMove(move, board));
    assertTrue(CuT.checkMoves(move, board));




  }


}
