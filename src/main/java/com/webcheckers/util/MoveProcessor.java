package com.webcheckers.util;

import com.webcheckers.model.*;

import java.util.Stack;

public class MoveProcessor {

    /**
     * Main method that determines if the players move is valid
     * Checks for simple move, single jumps, multijumps and king moves
     * @param move The players move that needs validation
     * @param board The game board
     * @return True if the player made a valid move
     */
    public static boolean validateMove (Move move, Board board) {
        if (validateSimpleMove(move, board) && !checkForJumpMove(board)) {
            return true;
        }
        else if (validateJumpMove(move, board)){
            return true;
        }
        return false;
    }

    /**
     * Checks if a simple move is valid
     * @param move The move that the player made
     * @param board The board
     * @return True if a player made a valid move
     */
    public static boolean validateSimpleMove (Move move, Board board) {
        //columns should be 8-col for white pieces but works without doing this so it may not be necessary to implement
        Space[][] gameBoard = board.getBoard();
        boolean kingException = false;
        int startRow = move.getStartRow();
        int endRow = move.getEndRow();
        if(board.getActiveColor() == Piece.pieceColor.WHITE) {
            startRow = adjustRow(move.getStartRow());
            endRow = adjustRow(move.getEndRow());
        }
        Piece checkPiece = gameBoard[startRow][move.getStartCell()].getPiece();
        if(checkPiece.getType() == Piece.pieceType.KING){
            if(startRow - 1 == endRow && (move.getStartCell() + 1 == move.getEndCell() || move.getStartCell() - 1 ==
                    move.getEndCell()) && gameBoard[move.getStartRow()][move.getStartCell()].getPiece() != null){
                kingException = true;
            }
        }
        return startRow + 1 == endRow &&
                ((move.getStartCell() + 1 == move.getEndCell() || move.getStartCell() - 1 == move.getEndCell()) &&
                gameBoard[move.getStartRow()][move.getStartCell()].getPiece() != null) || kingException;
    }

    /**
     * Determines a jump move performed by the user is valid
     * @param move The jump move
     * @param board he board
     * @return True if the jump move is valid
     */
    public static boolean validateJumpMove(Move move, Board board) {
        Space[][] gameBoard = board.getBoard();
        int startRow = move.getStartRow();
        int endRow = move.getEndRow();
        if(board.getActiveColor() == Piece.pieceColor.WHITE) {
            startRow = adjustRow(move.getStartRow());
            endRow = adjustRow(move.getEndRow());
        }
        if(!(endRow - 2 < 0)) {
            if (!(move.getEndCell() - 2 < 0) &&
                    gameBoard[endRow - 2][move.getEndCell() - 2] == gameBoard[startRow][move.getStartCell()] &&
                    gameBoard[endRow - 1][move.getEndCell() - 1].getPiece() != null &&
                    gameBoard[endRow - 1][move.getEndCell() - 1].getPiece().getColor() != board.getActiveColor()) {
                return true;
            }
            if (!(move.getEndCell() + 2 >= gameBoard.length) &&
                    gameBoard[endRow - 2][move.getEndCell() + 2] == gameBoard[startRow][move.getStartCell()] &&
                    gameBoard[endRow - 1][move.getEndCell() + 1].getPiece() != null &&
                    gameBoard[endRow - 1][move.getEndCell() + 1].getPiece().getColor() != board.getActiveColor()) {
                return true;
            }
        }
        return false;
    }

    public static boolean processMoves(Player player, Board board){
        Stack<Move> turnStack = player.getTurnStack();
        while (!turnStack.empty()) {
            board.makeMove(turnStack.remove(0));
        }
        //TODO assert proper players turn
        return true;
    }

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
    public static boolean checkForJumpMove(Board board) {
        Space[][] gameBoard = board.getBoard();
        int negOne = 1;
        if(board.getActiveColor() == Piece.pieceColor.WHITE){
            negOne = -1;
        }
        for (int row = 0; row < gameBoard.length - 2; row ++) {
            for(int col = 0; col < gameBoard.length; col ++) {
                //check that this is the moving player's piece
                if(gameBoard[row][col].getPiece() != null &&
                        gameBoard[row][col].getPiece().getColor() == board.getActiveColor()) {
                    //check right
                    //check out of bounds, adjacent diagonal right piece is opponent and there is an empty space after
                    //TODO Fix law of demeter here
                    if (!(col + 2 > gameBoard.length) && gameBoard[row + negOne][col + 1].getPiece() != null &&
                            gameBoard[row + negOne][col + 1].getPiece().getColor() != board.getActiveColor() &&
                            gameBoard[row + negOne * 2][col + 2].getPiece() == null) {
                        return true;
                    }
                    //check left
                    //check out of bounds, adjacent diagonal left piece is opponent and there is an empty space after
                    //TODO Fix law of demeter here
                    if (!(col - 2 < 0) && gameBoard[row + negOne][col - 1].getPiece() != null &&
                            gameBoard[row + negOne][col - 1].getPiece().getColor() != board.getActiveColor() &&
                            gameBoard[row + negOne * 2][col - 2].getPiece() == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if there is a multijump move is available after a player places a piece down after performing a jump
     * @param board The board
     * @param move The first jump
     * @return True if there is a multijump move available
     */
    public static boolean checkMultiJump (Board board, Move move) {
        //TODO Likely need to adjust move for white piece to accommodate for the board flipping

        Space[][] gameBoard = board.getBoard();
        //checks that there is enough room for a multijump
        if(move.getEndRow() < gameBoard.length - 2) {
            //check right
            //checks out of bounds, if next piece is an enemy piece and the space after is empty
            if(!(move.getEndCell() + 2 > gameBoard.length) &&
                gameBoard[move.getEndRow() + 1][move.getEndCell() + 1].getPiece().getColor() != board.getActiveColor() &&
                    gameBoard[move.getEndRow() + 1][move.getEndCell() + 1].getPiece() != null &&
                    gameBoard[move.getEndRow() + 2][move.getEndCell() + 2].getPiece() == null) {
                return true;
            }
            //check left
            //checks out of bounds, if next piece is an enemy piece and the space after is empty
            return (!(move.getEndCell() - 2 < 0) &&
                gameBoard[move.getEndRow() - 1][move.getEndCell() - 1].getPiece().getColor() != board.getActiveColor() &&
                    gameBoard[move.getEndRow() + 1][move.getEndCell() - 1].getPiece() != null &&
                    gameBoard[move.getEndRow() - 2][move.getEndCell() - 2].getPiece() == null);
            }
        return false;
    }

    /**
     * Determines if a checkers piece reaches the end of the board
     * @param board The game board
     * @param move The move that the player made
     * @return True if the player has reached the end of the board
     */
    public static boolean reachedEnd(Board board, Move move) {
        Space[][] gameBoard = board.getBoard();
        int endRow = move.getEndRow();
        if (board.getActiveColor() == Piece.pieceColor.WHITE) {
            endRow = adjustRow(move.getEndRow());
        }
        return endRow == gameBoard.length;
    }

    /**
     * Adjusts the row when checking moves for the white player
     * @param row The row that is to be adjusted
     * @return The adjusted row
     */
    public static int adjustRow(int row) {
        return 8 - row;
    }

    //create a reverse move instead
    /*public static Space[][] reverseRows(Board board) {
        Space[][] gameBoard = board.getBoard();
        Space[][] game = new Space[gameBoard.length][gameBoard.length];
        for(int row = 0; row < gameBoard.length; row ++) {
            for (int col = 0; col < gameBoard.length; col ++) {
                //game[gameBoard.length - row - 1][gameBoard.length - col - 1] = gameBoard[row][col];
                game[row][col] = gameBoard[gameBoard.length - row - 1][gameBoard.length - col - 1];
            }
        }
        return game;
    }*/
}
