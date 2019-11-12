package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardState implements Iterable<Row>{

    /** Contains the piece placement for a completed turn */
    private Piece.pieceColor activeColor;

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
        for(int r = 0; r < 8; r++){
            rows.add(new Row(r, board.getRow(r)));
        }

    }

    public Piece.pieceColor getActiveColor(){
        return activeColor;
    }

    public Iterator<Row> iterator() {
        return rows.iterator();
    }


}
