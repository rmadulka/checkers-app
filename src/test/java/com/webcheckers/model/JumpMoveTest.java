package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-Tier")
public class JumpMoveTest {
  /**
   * Friendly object
   */
  private JumpMove CuT;

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
  public void init() {
    CuT = new JumpMove();
    redPlayer = new Player("red");
    whitePlayer = new Player("white");
    board = new Board(redPlayer, whitePlayer);
  }

  /**
   * Tests a forward jump move
   */
  @Test
  public void jumpMoveTest() {
    Position start = new Position(0, 0);
    Space jumping = board.getSpace(new Position(1, 1));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(2, 2);
    Move move = new Move(start, end);
    assertTrue(CuT.validateJumpMove(move, board));
    assertTrue(CuT.checkMoves(move, board));
  }

  /**
   * Tests a jump move over the same color as the piece jumping
   */
  @Test
  public void jumpMoveWrongColor() {
    Position start = new Position(0, 0);
    Space jumping = board.getSpace(new Position(1, 1));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Position end = new Position(2, 2);
    Move move = new Move(start, end);
    assertFalse(CuT.validateJumpMove(move, board));
    assertFalse(CuT.checkMoves(move, board));
  }

  /**
   * Tests a jump move over an empty space
   */
  @Test
  public void jumpMoveNoCapture() {
    Position start = new Position(0, 0);
    Position end = new Position(2, 2);
    Move move = new Move(start, end);
    assertFalse(CuT.validateJumpMove(move, board));
    assertFalse(CuT.checkMoves(move, board));
  }

  /**
   * Tests a jump move in the wrong direction
   */
  @Test
  public void jumpMoveWrongDirection() {
    Position start = new Position(3,3);
    Space jumping = board.getSpace(new Position(2, 2));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(1,1);
    Move move = new Move(start, end);
    assertFalse(CuT.validateJumpMove(move, board));
    assertFalse(CuT.checkMoves(move, board));
  }

  @Test
  /**
   * Tests a white piece jump move
   */
  public void test_white_jump_move(){
    board.switchTurn();
    Position start = new Position(5, 5);
    Space jumping = board.getSpace(new Position(4, 4));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Position end = new Position(3, 3);
    Move move = new Move(start, end);
    assertTrue(CuT.validateJumpMove(move, board));
    assertTrue(CuT.checkMoves(move, board));

  }

  @Test
  /**
   * Tests an invalid jump that is too far
   */
  public void test_too_far_of_a_jump(){
    board.switchTurn();
    Position start = new Position(5, 5);
    Space jumping = board.getSpace(new Position(4, 4));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Position end = new Position(2, 2);
    Move move = new Move(start, end);
    assertFalse(CuT.validateJumpMove(move, board));

  }

}
