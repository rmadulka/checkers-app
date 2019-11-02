package com.webcheckers.model;

public class SimpleMove extends Rules{

    /**
     * Checks if a simple move is valid
     *
     * @param move  The move that the player made
     * @param board The board
     * @return True if a player made a valid move
     */
    public boolean validateSimpleMove(Move move, Board board) {
        int one = 1;
        if (board.getActiveColor() == Piece.pieceColor.WHITE) {
            one = -1;
        }
        return move.getStartRow() + one == move.getEndRow() &&
                ((move.getStartCell() + 1 == move.getEndCell() || move.getStartCell() - 1 == move.getEndCell()));
    }

    @Override
    public boolean checkMoves(Move move, Board board){
        return validateSimpleMove(move, board);
    }
}
