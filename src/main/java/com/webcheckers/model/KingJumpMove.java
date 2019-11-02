package com.webcheckers.model;

public class KingJumpMove extends Rules{

    /**
     * Determines if the players king jump move was valid
     * @param move The move performed
     * @param board The board
     * @return True if the move was valid
     */
    public boolean ValidateKingJumpMove(Move move, Board board) {
        Space[][] gameBoard = board.getBoard();
        Piece checkPiece = gameBoard[move.getStartRow()][move.getStartCell()].getPiece();
        if(checkPiece != null && checkPiece.getType() == Piece.pieceType.KING) {
            int one = 1;
            if (board.getActiveColor() == Piece.pieceColor.WHITE) {
                one = -1;
            }
            if (Math.abs(move.getEndRow() - move.getStartRow()) != 2) {
                return false;
            }
            //checks backwards right diagonal jump moves
            if(!(move.getEndCell() - 2 < 0) &&
                    gameBoard[move.getEndRow() + one * 2][move.getEndCell() - 2] == gameBoard[move.getStartRow()][move.getStartCell()] &&
                    gameBoard[move.getEndRow() + one][move.getEndCell() - 1].getPiece() != null &&
                    gameBoard[move.getEndRow() + one][move.getEndCell() - 1].getPiece().getColor() != board.getActiveColor()){
                return true;
            }
            //checks backwards left diagonal jump moves
            if (!(move.getEndCell() + 2 > gameBoard.length - 1) &&
                    gameBoard[move.getEndRow() + one * 2][move.getEndCell() + 2] == gameBoard[move.getStartRow()][move.getStartCell()] &&
                    gameBoard[move.getEndRow() + one][move.getEndCell() + 1].getPiece() != null &&
                    gameBoard[move.getEndRow() + one][move.getEndCell() + 1].getPiece().getColor() != board.getActiveColor()) {
                return true;
            }

        }
        return false;
    }

    /**
     * Checks if the move made was a valid king jump
     * @param move The move the player made
     * @param board The board
     * @return True if the move is valid
     */
    @Override
    public boolean checkMoves(Move move, Board board){
        return ValidateKingJumpMove(move, board);
    }

}
