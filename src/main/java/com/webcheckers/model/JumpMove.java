package com.webcheckers.model;

public class JumpMove extends Rules{

    /**
     * Determines a jump move performed by the user is valid
     *
     * @param move  The jump move
     * @param board he board
     * @return True if the jump move is valid
     */
    public boolean validateJumpMove(Move move, Board board) {
        Space[][] gameBoard = board.getBoard();
        int one = 1;
        if(board.getActiveColor() == Piece.pieceColor.WHITE){
            one = -1;
            if (move.getEndRow() - move.getStartRow() != -2) {
                return false;
            }
        } else if (move.getEndRow() - move.getStartRow() != 2) {
            return false;
        }
        if (!(move.getEndCell() - 2 < 0) &&
                gameBoard[move.getEndRow() - one * 2][move.getEndCell() - 2] == gameBoard[move.getStartRow()][move.getStartCell()] &&
                gameBoard[move.getEndRow() - one][move.getEndCell() - 1].getPiece() != null &&
                gameBoard[move.getEndRow() - one][move.getEndCell() - 1].getPieceColor() != board.getActiveColor()) {
            return true;
        }
        if (!(move.getEndCell() + 2 > gameBoard.length - 1) &&
                gameBoard[move.getEndRow() - one * 2][move.getEndCell() + 2] == gameBoard[move.getStartRow()][move.getStartCell()] &&
                gameBoard[move.getEndRow() - one][move.getEndCell() + 1].getPiece() != null &&
                gameBoard[move.getEndRow() - one][move.getEndCell() + 1].getPieceColor() != board.getActiveColor()) {
            return true;
        }
        return false;
    }

    /**
     * Determines if the jump move made by the player is valid
     * @param move The move the player made
     * @param board The board
     * @return True if the move is valid
     */
    @Override
    public boolean checkMoves(Move move, Board board) {
        return validateJumpMove(move, board);
    }
}
