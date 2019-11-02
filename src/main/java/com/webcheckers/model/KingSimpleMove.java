package com.webcheckers.model;

public class KingSimpleMove extends Rules {

    /**
     * Checks if a backwards move is valid assuming the moving piece is a king piece
     * @param move The move
     * @param board The board
     * @return True if the move is valid
     */
    public boolean validateKingSimpleMove(Move move, Board board) {
        Space[][] gameBoard = board.getBoard();
        int one = 1;
        if (board.getActiveColor() == Piece.pieceColor.WHITE) {
            one = -1;
        }
        Piece checkPiece = gameBoard[move.getStartRow()][move.getStartCell()].getPiece();
        if (checkPiece != null && checkPiece.getType() == Piece.pieceType.KING) {
            return (move.getStartRow() - one == move.getEndRow() &&
                    (move.getStartCell() + 1 == move.getEndCell() || move.getStartCell() - 1 == move.getEndCell()) &&
                    gameBoard[move.getStartRow()][move.getStartCell()].getPiece() != null);

        }
        return false;
    }

    /**
     * Determines if a checkers piece reaches the end of the board
     * @param board The game board
     * @param move The move that the player made
     * @return True if the player has reached the end of the board
     */
    public boolean reachedEnd(Board board, Move move) {
        Space[][] gameBoard = board.getBoard();
        return move.getEndRow() == gameBoard.length - 1 || move.getEndRow() == 0;
    }

    @Override
    public boolean checkMoves(Move move, Board board) {
        return validateKingSimpleMove(move, board);
    }
}
