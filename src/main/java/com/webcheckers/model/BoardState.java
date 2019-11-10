package com.webcheckers.model;

public class BoardState {

    /** Contains the piece placement for a completed turn */
    private Space[][] state;

    /**
     * Intended to save a user's turn for replay uses
     */
    public BoardState(){

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

}
