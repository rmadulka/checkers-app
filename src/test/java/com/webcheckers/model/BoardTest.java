package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {
  private Player whitePlayer;
  private Player redPlayer;
  private Board CuT;
  public static final int TEST_SIZE = 8;
  public static final int maxPlaceRow = 3;
  public static final int minPlaceRow = 4;


  @BeforeEach
  public void setup() {
    whitePlayer = mock(Player.class);
    redPlayer = mock(Player.class);
    CuT = new Board(whitePlayer, redPlayer);
  }

  private void testBoardInit(Space[][] testBoard) {
    for (int row = 0; row < TEST_SIZE; row++) {
      for (int col = 0; col < TEST_SIZE; col++) {
        if (row % 2 == 0) {
          if (col % 2 == 0) testBoard[row][col] = new Space(col, false);
          else if (col % 2 == 1) testBoard[row][col] = new Space(col, true);
        } else if (row % 2 == 1) {
          if (col % 2 == 0) testBoard[row][col] = new Space(col, true);
          else if (col % 2 == 1) testBoard[row][col] = new Space(col, false);
        }
      }
    }
  }

  private void testBoardPopulate(Space[][] testBoard) {
    for (int row = 0; row < TEST_SIZE; row++) {
      for (int col = 0; col < TEST_SIZE; col++) {
        if(row < maxPlaceRow) {
          if (testBoard[row][col].isValid()) {
            testBoard[row][col].place(
              new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
          }
        }
        else if (row > minPlaceRow) {
          if (testBoard[row][col].isValid()) {
            testBoard[row][col].place(
              new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
          }
        }
      }
    }
  }

  @Test
  public void getBoard() {
    assertNotNull(CuT.getBoard());
    Space[][] testBoard = new Space[TEST_SIZE][TEST_SIZE];
    testBoardInit(testBoard);
    testBoardPopulate(testBoard);
    for (int row = 0; row < TEST_SIZE; row++) {
      for (int col = 0; col < TEST_SIZE; col++) {
        assertEquals(testBoard[row][col], CuT.getBoard()[row][col], "Not equal at " + row + ", " + col);
      }
    }
  }

  @Test
  public void getRed() {
    assertSame(redPlayer, CuT.getRed());
  }

  @Test
  public void getWhite() {
    assertSame(whitePlayer, CuT.getWhite());
  }

  @Test
  public void getRow() {
    Space[] testRowRed = new Space[TEST_SIZE];
    for (int space = 0; space < TEST_SIZE; space++) {
      if (space % 2 == 0) testRowRed[space] = new Space(space, false);
      else if (space % 2 == 1)
        testRowRed[space] = new Space(space, true,
        new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    }
    for (int space = 0; space < TEST_SIZE; space++) {
      assertEquals(testRowRed[space], CuT.getRow(0)[space]);
    }
    Space[] testRowWhite = new Space[TEST_SIZE];
    for (int space = 0; space < TEST_SIZE; space++) {
      if (space % 2 == 1) testRowWhite[space] = new Space(space, false);
      else if (space % 2 == 0) testRowWhite[space] = new Space(space, true,
      new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    }
    for (int space = 0; space < TEST_SIZE; space++) {
      assertEquals(testRowWhite[space], CuT.getRow(7)[space]);
    }
  }

  @Test
  public void testReverseRow() {
    Space[] testReverseWhite = new Space[TEST_SIZE];
    int space = 7;
    for (int i = 0; i < TEST_SIZE; i++) {
      if (space % 2 == 1) testReverseWhite[i] = new Space(space, true,
      new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
      else if (space % 2 == 0) testReverseWhite[i] = new Space(space, false);
      space--;
    }
  }
}
