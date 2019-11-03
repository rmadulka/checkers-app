package com.webcheckers.ui;

import com.webcheckers.model.*;
import java.util.ArrayList;
import java.util.Iterator;

public class BoardView implements Iterable<Row> {

  /** An array list of row objects */
  private ArrayList<Row> rows;
  /** Sets the size of the board to always be 8 spaces */
  private static final int SIZE = 8;
  private Player whitePlayer;
  private Player redPlayer;
  /**
   * Creates a new instance of a BoardView
   * @param player A player
   * @param board The board
   */
  public BoardView(Player player, Board board) {
    whitePlayer = board.getWhite();
    redPlayer = board.getRed();
    this.rows = new ArrayList<>();
    Piece.pieceColor playerColor = getColor(player, board);
    if (playerColor == Piece.pieceColor.WHITE) {
      for (int row = 0; row < SIZE; row++) {
          rows.add(new Row(row, board.getRow(row)));
      }
    } if (playerColor == Piece.pieceColor.RED) {
        for (int row = 7; row >= 0; row--) {
            rows.add(new Row(row, board.reverseRow(row)));
        }
    }
  }

  /**
   * Gets the color of the piece the player is playing as
   * @param player The player
   * @param board The board that the player is playing on
   * @return The color type based on the player
   */
  public Piece.pieceColor getColor(Player player, Board board) {
    if (board.getRed().equals(player)) {
      return Piece.pieceColor.RED;
    } else if (board.getWhite().equals(player)) {
      return Piece.pieceColor.WHITE;
    }
    return null;
  }

  /**
   * An iterator that goes through the rows in the board
   * @return An iterator object
   */
  public Iterator<Row> iterator() {
        return rows.iterator();
  }

  /**
   * Obtains the white piece player
   * @return whitePlayer
   */
  public Player getWhite() {
    return whitePlayer;
  }

  /**
   * Obtains the red piece player
   * @return redPlayer
   */
  public Player getRed() {
    return redPlayer;
  }
}
