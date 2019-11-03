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

  private static Player redPlayer;

  private static Player whitePlayer;

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

}
