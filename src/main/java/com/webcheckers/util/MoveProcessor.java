package com.webcheckers.util;

import com.webcheckers.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class MoveProcessor {

    static final ArrayList<Rules> rules = new ArrayList<>(Arrays.asList(new SimpleMove(), new JumpMove(), new KingSimpleMove(), new KingJumpMove()));

    /**
     * Main method that determines if the players move is valid
     * Checks for simple move, single jumps, multijumps and king moves
     *
     * @param move  The players move that needs validation
     * @param board The game board
     * @return True if the player made a valid move
     */
    public static boolean validateMove(Move move, Board board, Player player) {
        if (!player.getTurnStack().empty() && !player.getTurnStack().peek().isJumpMove()){
            return false;
        }
        if(!player.getTurnStack().empty()){
            System.err.println(board.checkConvertedKingPiece(board, player.getTurnStack().get(0)));
        }

        if (!player.getTurnStack().empty() && board.checkConvertedKingPiece(board, player.getTurnStack().get(0))){
            return false;
        }
        for (Rules rule : rules) {
            if(rule.checkMoves(move, board)){
                return true;
            }
        }
        return false;
    }

    public static boolean validateTurn(Stack<Move> turnStack, Board board){
        Board tempBoard = new Board(board);

        //Runs through the turn stack and makes the moves on to a temp board
        for(Move move : turnStack){
            System.err.println("was converted piece: " + board.checkConvertedKingPiece(tempBoard, move));
            if(checkForJumpMove(tempBoard) && !move.isJumpMove()){
                return false;
            } else if (board.checkConvertedKingPiece(tempBoard, move)){
                return true;
            }
            tempBoard.makeMove(move);
        }

        System.out.println("jump move available: " + checkForJumpMove(tempBoard));
        System.out.println("was jump move: " + turnStack.peek().isJumpMove());
        //Checks if jump move is available after a jump move was made
        if(checkForJumpMove(tempBoard) && turnStack.peek().isJumpMove()){

            return false;
        }
        return true;
    }

    public static boolean processMoves(Player player, Board board){
        Stack<Move> turnStack = player.getTurnStack();
        while (!turnStack.empty()) {
            board.makeMove(turnStack.remove(0));
        }
        //TODO assert proper players turn
        board.switchTurn();
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
                    if (!(row - negOne * 2> gameBoard.length - 1 || row - negOne * 2 < 0)) {
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
}
