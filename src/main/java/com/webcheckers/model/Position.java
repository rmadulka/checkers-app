package com.webcheckers.model;

public class Position {

    /** The row position */
    private int row;
    /** The cell position */
    private int cell;

    /**
     * Creates a new position instance
     * @param row The row the piece is located
     * @param cell The cell the piece is located
     */
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * Gets the row position of the piece
     * @return The row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the cell position of the piece
     * @return The cell
     */
    public int getCell(){
        return cell;
    }
}
