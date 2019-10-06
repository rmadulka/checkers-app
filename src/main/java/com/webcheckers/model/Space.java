package com.webcheckers.model;

/**
 * represents a space on a checkerboard. returns a cell index, if the space is valid and the piece
 * on the space.
 */
public class Space {
    private int cellIdx;        /* cell index */
    private boolean isDark;     /* is the space dark? */
    private Piece piece;     /* the piece in the spot */

    /**
     * constructor for a checkerboard space.
     * @param cellIdx cell index
     * @param isDark is the space dark?
     */
    public Space(int cellIdx, boolean isDark) {
        this.cellIdx = cellIdx;
        this.isDark = isDark;
        this.piece = null;
    }

    /**
     * space cell position
     * @return cellIdx: 0 to 63
     */
    public int getCellIdx() {
        return this.cellIdx;
    }

    /**
     * is this space dark and is there a piece already here?
     * @return true or false
     */
    public boolean isValid() {
        return this.isDark && this.piece == null;
    }

    /**
     * place piece in a valid space
     * @param piece
     */
    public boolean place(Piece piece) {
        if (piece != null) {
            this.piece = piece;
            return true;
        } else {
            return false;
        }
    }

    /**
     * is a piece here?
     * @return a piece
     */
    public Piece getPiece() {
        return this.piece;
    }
}
