package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardState implements Iterable<Row>{

    /** Contains the piece placement for a completed turn */
    private Piece.pieceColor activeColor;

    /** holds all of the row data for a specific board */
    private ArrayList<Row> rows;


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

    /**
     * Helper function that fills out a single row of spaces and pieces
     * @param rowInd specified row index
     * @param board current board
     * @return a singular row of board
     */
    public Space[] constructSpaces(int rowInd, Board board) {
        Space[] row = new Space[8];
        for (int c = 0; c < 8; c++) {
            Piece piece = null;
            if(board.getBoard()[rowInd][c].getPiece() != null) {
                piece = new Piece(board.getBoard()[rowInd][c].getPiece().getType(), board.getBoard()[rowInd][c].getPiece().getColor());
            }
            if(rowInd + c % 2 == 0) {
                row[c] = new Space(c, true, piece);
            } else {
                row[c] = new Space(c, false, piece);
            }
        }
        return row;
    }

    /**
     * retrieves the active color for a given board
     * @return activeColor
     */
    public Piece.pieceColor getActiveColor(){
        return activeColor;
    }

    /**
     * Gets the given board positions organized by row
     * @return rows
     */
    public Iterator<Row> iterator() {
        return rows.iterator();
    }


}
