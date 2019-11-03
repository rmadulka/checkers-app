package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webcheckers.model.Piece.pieceType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KingJumpMoveTest {

  private KingJumpMove CuT;

  private static Player redPlayer;

  private static Player whitePlayer;

  private static Board board;

  @BeforeEach
  public void init() {
    CuT = new KingJumpMove();
    redPlayer = new Player("red");
    whitePlayer = new Player("white");
    board = new Board(redPlayer, whitePlayer);
  }

  @Test
  public void kingJumpMoveTest() {
    Position start = new Position(7, 1);
    Space king = board.getSpace(start);
    king.place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
    Space jumping = board.getSpace(new Position(6, 2));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    Position end = new Position(5, 3);
    Move move = new Move(start, end);
    assertTrue(CuT.validateKingJumpMove(move, board));
  }

  @Test
  public void kingJumpMoveWrongColor() {
    Position start = new Position(5,3);
    Space king = board.getSpace(start);
    king.place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
    Space jumping = board.getSpace(new Position(6, 4));
    jumping.place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    Position end = new Position(5, 5);
    Move move = new Move(start, end);
    assertFalse(CuT.validateKingJumpMove(move, board));
  }

  @Test
  public void kingJumpMoveNoCapture() {
    Position start = new Position(5,1);
    Space king = board.getSpace(start);
    king.place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
    Position end = new Position(3, 3);
    Move move = new Move(start, end);
    assertFalse(CuT.validateKingJumpMove(move, board));
  }
}
