package com.webcheckers.model;

public class BoardState {

    /** Contains the piece placement for a completed turn */
    private Space[][] state;

    private Piece.pieceColor activeColor;

    /**
     * Intended to save a user's turn for replay uses
     */
    public BoardState(Piece.pieceColor activeColor){
        this.activeColor = activeColor;
    }

    /**
     * Sets the current state to the submitted board layout
     * @param board submitted board
     */
    public void constructState(Board board) {
        state = board.copyBoard(board);

    }

    /**
     * gets the board layout
     * @return state
     */
    public Space[][] getState() {
        return state;
    }

    public Piece.pieceColor getActiveColor(){
        return activeColor;
    }


}
