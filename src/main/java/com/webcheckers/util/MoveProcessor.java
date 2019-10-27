package com.webcheckers.util;

import com.webcheckers.model.*;

import java.util.Stack;

public class MoveProcessor {

    public static boolean validateMove (Move move, Board board) {
        Space[][] gameBoard = board.getBoard();
        if(board.getActiveColor() == Piece.pieceColor.WHITE) {
            gameBoard = reverseRows(board);
        }
        return gameBoard[move.getEndRow()][move.getEndCell()].isValid() &&
                move.getStartRow() + 1 == move.getEndRow() &&
                (move.getStartCell() + 1 == move.getEndCell() || move.getStartCell() - 1 == move.getEndCell());
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
     * @param board A board
     * @return True if there is an available jump move
     */
    public static boolean checkForJumpMove(Board board) {
        //we need to call the getactivecolor method in board
        Space[][] gameBoard = board.getBoard();
        for (int row = 0; row < gameBoard.length - 2; row ++) {
            for(int col = 0; col < gameBoard.length; col ++) {
                //check that this is the moving player's piece
                if(gameBoard[row][col].isValid()) {//&& check that it's the moving players piece
                    //check right
                    //check out of bounds, adjacent diagonal right piece is opponent and there is an empty space after
                    if (!(col + 2 > gameBoard.length) && gameBoard[row + 1][col + 1].isValid() &&
                            gameBoard[row + 2][col + 2].getPiece() == null) { //&& is opponents piece
                        return true;
                    }
                    //check left
                    //check out of bounds, adjacent diagonal left piece is opponent and there is an empty space after
                    if (!(col - 2 < 0) && gameBoard[row + 1][col - 1].isValid() &&
                            gameBoard[row + 2][col - 2].getPiece() == null) { //&& is opponents piece
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if a checkers piece reaches the end of the board
     * @param board The game board
     * @param move The move that the player made
     * @return True if the player has reached the end of the board
     */
    public static boolean reachedEnd(Space[][] board, Move move) {
        return move.getEndRow() == board.length;
    }

    public static Space[][] reverseRows(Board board) {
        Space[][] gameBoard = board.getBoard();
        for(int row = 0; row < gameBoard.length; row ++) {
            for (int col = 0; col < gameBoard.length; col ++) {
                gameBoard[gameBoard.length - row - 1][gameBoard.length - col - 1] = gameBoard[row][col];
            }
        }
        return gameBoard;
    }
}
