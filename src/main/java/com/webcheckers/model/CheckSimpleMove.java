package com.webcheckers.model;

public class CheckSimpleMove extends Rules {

    private boolean checkForSimpleMove(Board board) {
        Space[][] gameBoard = board.getBoard();
        int negOne = 1;
        if(board.getActiveColor() == Piece.pieceColor.WHITE) {
            negOne = -1;
        }
        for (int row = 0; row < gameBoard.length; row ++) {
            for (int col = 0; col < gameBoard.length; col++) {
                if (!(row + negOne > gameBoard.length - 1 || row + negOne < 0)) {
                    //check that this is the moving player's piece
                    if (gameBoard[row][col].getPiece() != null &&
                            gameBoard[row][col].getPiece().getColor() == board.getActiveColor()) {
                        //check out of bounds, adjacent diagonal right piece is opponent and there is an empty space after
                        //TODO Fix law of demeter here
                        if (!(col + 1 > gameBoard.length - 1) && gameBoard[row + negOne][col + 1].getPiece() == null) {
                            return true;
                        }
                        //check out of bounds, adjacent diagonal left piece is opponent and there is an empty space after
                        //TODO Fix law of demeter here
                        if (col - 1 >= 0 && gameBoard[row + negOne][col - 1].getPiece() == null) {
                            return true;
                        }
                    }
                }
                if (gameBoard[row][col].getPiece() != null && gameBoard[row][col].getPiece().getType() == Piece.pieceType.KING) {
                    if (!(row - negOne > gameBoard.length - 1 || row - negOne < 0)) {
                        //check that this is the moving player's piece
                        if (gameBoard[row][col].getPiece() != null &&
                                gameBoard[row][col].getPiece().getColor() == board.getActiveColor()) {
                            //check out of bounds, adjacent diagonal right piece is opponent and there is an empty space after
                            //TODO Fix law of demeter here
                            if (!(col + 1 > gameBoard.length - 1) && gameBoard[row - negOne][col + 1].getPiece() == null) {
                                return true;
                            }
                            //check out of bounds, adjacent diagonal left piece is opponent and there is an empty space after
                            //TODO Fix law of demeter here
                            if (col - 1 >= 0 && gameBoard[row - negOne][col - 1].getPiece() == null) {
                                return true;
                            }
                        }
                    }

                }
            }
        }
        return false;
    }


    @Override
    public boolean checkMoves(Move move, Board board) {
        return checkForSimpleMove(board);
    }
}
