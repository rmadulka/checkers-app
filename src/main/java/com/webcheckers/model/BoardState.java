package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardState implements Iterable<Row>{

    /** Contains the piece placement for a completed turn */
    private Piece.pieceColor activeColor;

    private ArrayList<Row> rows;

    private Space[][] spaces;

    /**
     * Intended to save a user's turn for replay uses
     */
    public BoardState(Piece.pieceColor activeColor){
        this.activeColor = activeColor;
        this.rows = new ArrayList<>();

    }

    /**
     * Sets the current state to the submitted board layout
     * @param board submitted board
     */
    public void constructState(Board board) {
        for (int row = 0; row < 8; row++){
            rows.add(new Row(row, constructSpaces(row, board)));
        }
    }

    public Space[] constructSpaces(int rowInd, Board board) {
        Space[] row = new Space[8];
        for (int c = 0; c < 8; c++) {
            if(rowInd + c % 2 == 0) {
                row[c] = new Space(c, true, board.getBoard()[rowInd][c].getPiece());
            } else {
                row[c] = new Space(c, false, board.getBoard()[rowInd][c].getPiece());
            }
        }
        return row;
    }

    public Piece.pieceColor getActiveColor(){
        return activeColor;
    }

    public Iterator<Row> iterator() {
        return rows.iterator();
    }


}
