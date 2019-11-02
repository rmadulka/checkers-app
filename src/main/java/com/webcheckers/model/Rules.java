package com.webcheckers.model;

public abstract class Rules {

    /**
     * Determines if the made move is valid
     * @param move The move the player made
     * @param board The board
     * @return True if the move is valid
     */
    public abstract boolean checkMoves (Move move, Board board);
}
