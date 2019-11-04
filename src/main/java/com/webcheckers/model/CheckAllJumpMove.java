package com.webcheckers.model;

public class CheckAllJumpMove extends Rules{
    /**
     * During a turn, a player is required to perform a jump move if there is one. Furthermore, a player is not required
     * to perform the jump move with the most jumps. A player can choose to do any jump move on the board, however, if
     * the piece they choose to perform the jump move is able to do multiple jump moves, then the player is required to
     * perform all the jump moves
     *
     * This method determines if there is an available jump move on the board
     * @param board A board
     * @return True if there is an available jump move
     */
    public boolean checkForJumpMove(Board board) {
        Space[][] gameBoard = board.getBoard();
        int negOne = 1;
        if(board.getActiveColor() == Piece.pieceColor.WHITE) {
            negOne = -1;
        }
        for (int row = 0; row < gameBoard.length; row ++) {
            for (int col = 0; col < gameBoard.length; col++) {
                if (!(row + negOne * 2 > gameBoard.length - 1 || row + negOne * 2 < 0)) {
                    //check that this is the moving player's piece
                    if (gameBoard[row][col].getPiece() != null &&
                            gameBoard[row][col].getPiece().getColor() == board.getActiveColor()) {
                        //check out of bounds, adjacent diagonal right piece is opponent and there is an empty space after
                        //TODO Fix law of demeter here
                        if (!(col + 2 > gameBoard.length - 1) && gameBoard[row + negOne][col + 1].getPiece() != null &&
                                gameBoard[row + negOne][col + 1].getPiece().getColor() != board.getActiveColor() &&
                                gameBoard[row + negOne * 2][col + 2].getPiece() == null) {
                            return true;
                        }
                        //check out of bounds, adjacent diagonal left piece is opponent and there is an empty space after
                        //TODO Fix law of demeter here
                        if (col - 2 >= 0 && gameBoard[row + negOne][col - 1].getPiece() != null &&
                                gameBoard[row + negOne][col - 1].getPiece().getColor() != board.getActiveColor() &&
                                gameBoard[row + negOne * 2][col - 2].getPiece() == null) {
                            return true;
                        }
                    }
                }
                if (gameBoard[row][col].getPiece() != null && gameBoard[row][col].getPiece().getType() == Piece.pieceType.KING) {
                    if (!(row - negOne * 2 > gameBoard.length - 1 || row - negOne * 2 < 0)) {
                        //check that this is the moving player's piece
                        if (gameBoard[row][col].getPiece() != null &&
                                gameBoard[row][col].getPiece().getColor() == board.getActiveColor()) {
                            //check out of bounds, adjacent diagonal right piece is opponent and there is an empty space after
                            //TODO Fix law of demeter here
                            if (!(col + 2 > gameBoard.length - 1) && gameBoard[row - negOne][col + 1].getPiece() != null &&
                                    gameBoard[row - negOne][col + 1].getPiece().getColor() != board.getActiveColor() &&
                                    gameBoard[row - negOne * 2][col + 2].getPiece() == null) {
                                return true;
                            }
                            //check out of bounds, adjacent diagonal left piece is opponent and there is an empty space after
                            //TODO Fix law of demeter here
                            if (col - 2 >= 0 && gameBoard[row - negOne][col - 1].getPiece() != null &&
                                    gameBoard[row - negOne][col - 1].getPiece().getColor() != board.getActiveColor() &&
                                    gameBoard[row - negOne * 2][col - 2].getPiece() == null) {
                                return true;
                            }
                        }
                    }

                }
            }
        }
        return false;
    }

    public boolean checkMoves(Move move, Board board){
        return checkForJumpMove(board);
    }
}
