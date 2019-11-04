package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class BoardTest {
  private Player whitePlayer;
  private Player redPlayer;
  private Board CuT;
  public static final int TEST_SIZE = 8;
  public static final int maxPlaceRow = 3;
  public static final int minPlaceRow = 4;

  private static Move move;
  private static Move move2;

  @BeforeAll
  public static void init () {
    move = new Move(new Position(6,5), new Position(7,6));
    move2 = new Move(new Position(5, 4), new Position(7, 6));
  }


  @BeforeEach
  /**
   * Initiates the the mock objects
   */
  public void setup() {
    whitePlayer = mock(Player.class);
    redPlayer = mock(Player.class);
    CuT = new Board(whitePlayer, redPlayer);
  }

  /**
   * Helper function that fills in the spaces in the board
   * @param testBoard
   */
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

  /**
   * Helper function used to populate the board with pieces
   * @param testBoard: sample board to fill
   */
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
  /**
   * Tests whether the board can be obtained after being populated
   */
  public void getBoard() {
    assertNotNull(CuT.getBoard());
    Space[][] testBoard = new Space[TEST_SIZE][TEST_SIZE];
    testBoardInit(testBoard);
    testBoardPopulate(testBoard);
    for (int row = 0; row < TEST_SIZE; row++) {
      for (int col = 0; col < TEST_SIZE; col++) {
        assertEquals(testBoard[row][col], CuT.getBoard()[row][col]);
      }
    }
  }

  @Test
  /**
   * Tests whether the red piece player can be obtained
   */
  public void getRed() {
    assertSame(redPlayer, CuT.getRed());
  }

  @Test
  /**
   * Tests whether the white piece player can be obtained
   */
  public void getWhite() {
    assertSame(whitePlayer, CuT.getWhite());
  }

  @Test
  /**
   * Tests whether a specified row can be obtained given the mock board
   */
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
  /**
   * Tests the board being flipped as the board must be positioned where the pieces are aligned on the bottom
   * for each player
   */
  public void testReverseRow() {
    Space[] testReverseWhite = new Space[TEST_SIZE];
    int space1 = 7;
    for (int i = 0; i < TEST_SIZE; i++) {
      if (space1 % 2 == 1) testReverseWhite[i] = new Space(space1, true,
      new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
      else if (space1 % 2 == 0) testReverseWhite[i] = new Space(space1, false);
      space1--;
    }
    for (int j = 0; j < TEST_SIZE; j++) {
      assertEquals(testReverseWhite[j], CuT.reverseRow(0)[j]);
    }
    Space[] testReverseRed = new Space[TEST_SIZE];
    int space2 = 7;
    for (int i = 0; i < TEST_SIZE; i++) {
      if (space2 % 2 == 0) testReverseRed[i] = new Space(space2, true,
      new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
      else if (space2 % 2 == 1) testReverseRed[i] = new Space(space2, false);
      space2--;
    }
    for (int j = 0; j < TEST_SIZE; j++) {
      assertEquals(testReverseRed[j], CuT.reverseRow(7)[j]);
    }
  }

  /**
   * Tests if we can make a move and if we can convert a piece to a king
   */
  @Test
  public void testMakeMoveAndConvertKing() {
    Space[][] game = new Space[TEST_SIZE][TEST_SIZE];
    testBoardInit(game);
    game[6][5].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    assertEquals(Piece.pieceType.SINGLE, game[6][5].getPiece().getType());
    CuT.makeMove(move);
    Space[][] updateGame = CuT.getBoard();
    assertEquals(Piece.pieceType.KING, updateGame[7][6].getPiece().getType());
  }

  /**
   * Tests a jump move in makemove method and makeTempMove method
   */
  @Test
  public void testMakeMoveAndTempMove() {
    Space[][] game = new Space[TEST_SIZE][TEST_SIZE];
    testBoardInit(game);
    game[5][4].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
    CuT.makeMove(move2);
    Space[][] updateGame = CuT.getBoard();
    assertNull(updateGame[6][5].getPiece());

    CuT.makeTempMove(move2);
    assertNull(updateGame[6][5].getPiece());
  }
}
