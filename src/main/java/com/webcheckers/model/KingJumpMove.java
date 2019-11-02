package com.webcheckers.model;

public class KingJumpMove extends Rules{

    public static boolean checkForJumpMove(Move move, Board board) {
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

    public boolean checkMoves(Move move, Board board){
        return checkForJumpMove(move, board);
    }

}
