package com.webcheckers.model;

public class Move {

    /** The start position of the move */
    private Position start;
    /** The end position of the move */
    private Position end;

    /**
     * Creates a new instance of a move
     * Made whenever the player makes a move
     * @param start The start position (before the move is made)
     * @param end The end position (after the move is made)
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Gets the start position
     * @return The start position
     */
    public Position getStart() {
        return start;
    }

    /**
     * Gets the end position
     * @return The end position
     */
    public Position getEnd() {
        return end;
    }

    /**
     * Gets the start row of the start position
     * @return Start row
     */
    public int getStartRow() {
        return start.getRow();
    }

    /**
     * Gets the start cell of the start position
     * @return The start cell
     */
    public int getStartCell() {
        return start.getCell();
    }

    /**
     * Gets the end row of the end position
     * @return The end row
     */
    public int getEndRow() {
        return end.getRow();
    }

    /**
     * Gets the end cell of the end position
     * @return The end cell
     */
    public int getEndCell () {
        return end.getCell();
    }


    public boolean isJumpMove(){
        return Math.abs(getStartRow() - getEndRow()) > 1
                && Math.abs(getStartCell() - getEndCell()) > 1;
    }
}
