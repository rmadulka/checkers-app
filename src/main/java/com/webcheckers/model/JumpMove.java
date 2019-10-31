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
                gameBoard[move.getEndRow() - one][move.getEndCell() - 1].getPiece().getColor() != board.getActiveColor()) {
            return true;
        }
        if (!(move.getEndCell() + 2 > gameBoard.length - 1) &&
                gameBoard[move.getEndRow() - one * 2][move.getEndCell() + 2] == gameBoard[move.getStartRow()][move.getStartCell()] &&
                gameBoard[move.getEndRow() - one][move.getEndCell() + 1].getPiece() != null &&
                gameBoard[move.getEndRow() - one][move.getEndCell() + 1].getPiece().getColor() != board.getActiveColor()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if there is a multijump move is available after a player places a piece down after performing a jump
     * @param board The board
     * @param move The first jump
     * @return True if there is a multijump move available
     */
    //have no clue if this works. Not sure how we should implement a multijump move yet
    public boolean checkMultiJump (Move move, Board board) {
        Space[][] gameBoard = board.getBoard();
        int negOne = 1;
        if(board.getActiveColor() == Piece.pieceColor.WHITE) {
            negOne = -1;
        }
        //checks that there is enough room for a multijump
        if(move.getEndRow() < gameBoard.length - 2) {
            //check right
            //checks out of bounds, if next piece is an enemy piece and the space after is empty
            if(!(move.getEndCell() + 2 > gameBoard.length - 1) &&
                    gameBoard[move.getEndRow() + negOne][move.getEndCell() + 1].getPiece() != null &&
                    gameBoard[move.getEndRow() + negOne][move.getEndCell() + 1].getPiece().getColor() != board.getActiveColor() &&
                    gameBoard[move.getEndRow() + negOne][move.getEndCell() + 2].getPiece() == null) {
                return true;
            }
            //check left
            //checks out of bounds, if next piece is an enemy piece and the space after is empty
            return (move.getEndCell() - 2 >= 0 &&
                    gameBoard[move.getEndRow() + negOne][move.getEndCell() - 1].getPiece() != null &&
                    gameBoard[move.getEndRow() - negOne][move.getEndCell() - 1].getPiece().getColor() != board.getActiveColor() &&
                    gameBoard[move.getEndRow() - negOne][move.getEndCell() - 2].getPiece() == null);
        }
        return false;
    }

    @Override
    public boolean checkMoves(Move move, Board board) {
        return validateJumpMove(move, board);
    }
}
