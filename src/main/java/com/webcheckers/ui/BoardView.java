package com.webcheckers.ui;

import com.webcheckers.model.*;
import java.util.ArrayList;
import java.util.Iterator;

public class BoardView implements Iterable<Row> {
  private ArrayList<Row> rows = new ArrayList();
  public static final int SIZE = 8;

  public BoardView(Player player, Board board) {
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

  public Piece.pieceColor getColor(Player player, Board board) {
    if (board.getRed().equals(player)) {
      return Piece.pieceColor.RED;
    } else if (board.getWhite().equals(player)) {
      return Piece.pieceColor.WHITE;
    }
    return null;
    }

  public Iterator<Row> iterator() {
      return rows.iterator();
  }

}
