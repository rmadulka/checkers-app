package com.webcheckers.model;

public class CheckOneJumpMove extends Rules {

    /**
     * Checks if there is a multijump move is available after a player places a piece down after performing a jump
     * @param board The board
     * @param move The first jump
     * @return True if there is a multijump move available
     */
    public boolean checkMultiJump (Move move, Board board) {
        Space[][] gameBoard = board.getBoard();
        int negOne = 1;
        if (board.getActiveColor() == Piece.pieceColor.WHITE) {
            negOne = -1;
        }
        if (((move.getEndRow() >= gameBoard.length - 2 && gameBoard[move.getEndRow()][move.getEndCell()].getPiece().getColor() == Piece.pieceColor.RED) ||
                (move.getEndRow() - 2 < 0 && gameBoard[move.getEndRow()][move.getEndCell()].getPiece().getColor() == Piece.pieceColor.WHITE)) &&
                gameBoard[move.getEndRow()][move.getEndCell()].getPiece().getType() == Piece.pieceType.SINGLE) {
            return false;
        }
        if((move.getEndRow() <= gameBoard.length - 2 && gameBoard[move.getEndRow()][move.getEndCell()].getPiece().getColor() == Piece.pieceColor.RED) ||
                (move.getEndRow() - 2 >= 0 && gameBoard[move.getEndRow()][move.getEndCell()].getPiece().getColor() == Piece.pieceColor.WHITE)) {
            //checks out of bounds, if next piece is an enemy piece and the space after is empty
            if (!(move.getEndCell() - 2 < 0) &&
                    gameBoard[move.getEndRow() + negOne][move.getEndCell() - 1].getPiece() != null &&
                    gameBoard[move.getEndRow() + negOne][move.getEndCell() - 1].getPiece().getColor() != board.getActiveColor() &&
                    gameBoard[move.getEndRow() + negOne * 2][move.getEndCell() - 2].getPiece() == null) {
                return true;
            }
            //checks out of bounds, if next piece is an enemy piece and the space after is empty
            if (!(move.getEndCell() + 2 > gameBoard.length - 1) &&
                    gameBoard[move.getEndRow() + negOne][move.getEndCell() + 1].getPiece() != null &&
                    gameBoard[move.getEndRow() + negOne][move.getEndCell() + 1].getPiece().getColor() != board.getActiveColor() &&
                    gameBoard[move.getEndRow() + negOne * 2][move.getEndCell() + 2].getPiece() == null) {
                return true;
            }
        }
        if (gameBoard[move.getEndRow()][move.getEndCell()].getPiece().getType().equals(Piece.pieceType.KING) &&
                ((move.getEndRow() <= gameBoard.length - 2 &&
                gameBoard[move.getEndRow()][move.getEndCell()].getPiece().getColor() == Piece.pieceColor.WHITE) ||
                (move.getEndRow() - 2 >= 0 && gameBoard[move.getEndRow()][move.getEndCell()].getPiece().getColor() == Piece.pieceColor.RED))) {
            if (!(move.getEndCell() - 2 < 0) &&
                    gameBoard[move.getEndRow() - negOne][move.getEndCell() - 1].getPiece() != null &&
                    gameBoard[move.getEndRow() - negOne][move.getEndCell() - 1].getPiece().getColor() != board.getActiveColor() &&
                    gameBoard[move.getEndRow() - negOne * 2][move.getEndCell() - 2].getPiece() == null) {
                return true;
            }
            if (!(move.getEndCell() + 2 > gameBoard.length - 1) &&
                    gameBoard[move.getEndRow() - negOne][move.getEndCell() + 1].getPiece() != null &&
                    gameBoard[move.getEndRow() - negOne][move.getEndCell() + 1].getPiece().getColor() != board.getActiveColor() &&
                    gameBoard[move.getEndRow() - negOne * 2][move.getEndCell() + 2].getPiece() == null) {
                return true;
            }
        }
        return false;
    }

    public boolean checkMoves(Move move, Board board){
        return checkMultiJump(move, board);
    }
}
