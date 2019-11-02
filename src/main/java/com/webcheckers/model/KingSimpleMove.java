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

    @Override
    public boolean checkMoves(Move move, Board board) {
        return validateKingSimpleMove(move, board);
    }
}
