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
        spaces = new Space[8][8];
        constructSpaces(board);
        for(int r = 0 ; r < 8; r++){
            Space spaceArray[] = new Space[8];
            for(int c = 0 ; c < 8 ; c++){
                spaceArray[c] = spaces[r][c];
            }
            for(int row = 0 ; row < 8 ; row++){
                rows.add(new Row(row, spaceArray));
            }
        }

    }

    public void constructSpaces(Board board) {
        Space[][] boardLayout = board.getBoard();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (r % 2 == 0 && c % 2 == 0) {
                    spaces[r][c] = new Space(c, false);
                } else if (r % 2 == 0 && c % 2 == 1) {
                    spaces[r][c] = new Space(c, true);
                } else if (r % 2 == 1 && c % 2 == 0) {
                    spaces[r][c] = new Space(c, true);
                } else if (r % 2 == 1 && c % 2 == 1) {
                    spaces[r][c] = new Space(c, false);
                }
                if(boardLayout[r][c].getPiece() != null) {
                    spaces[r][c].place(boardLayout[r][c].getPiece());
                }
            }
        }
    }

    public Piece.pieceColor getActiveColor(){
        return activeColor;
    }

    public Iterator<Row> iterator() {
        return rows.iterator();
    }


}
