package com.webcheckers.util;

import com.webcheckers.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class MoveProcessor {

    /** An arraylist of all the types of moves a player can make */
    static final ArrayList<Rules> rules = new ArrayList<>(Arrays.asList(new SimpleMove(), new JumpMove(), new KingSimpleMove(), new KingJumpMove()));

    /** Rule for checking the entire board for a jump move */
    public static final Rules allJumpRule = new CheckAllJumpMove();

    /** Rule for checking a piece for continuing jump moves */
    static final Rules oneJumpRule = new CheckOneJumpMove();

    public static final Rules allSimpleMoveRule = new CheckSimpleMove();

    /** A temporary board that is made for a turn */
    static Board temporaryBoard;

    /**
     * Main method that determines if the players move is valid
     * Checks for simple move, single jumps, multijumps and king moves
     *
     * @param move  The players move that needs validation
     * @param board The game board
     * @return True if the player made a valid move
     */
    public static boolean validateMove(Move move, Board board, Player player) {
        //If there were no previous moves made, get a fresh temp board
        if(player.getTurnStack().empty()) {
            temporaryBoard = new Board(board);
        }

        if (!player.getTurnStack().empty()) {
            //If at any point the player makes a simple move, don't allow any more moves
            if(!player.getTurnStack().peek().isJumpMove()) {
                return false;
            }

            //If the player makes a single move after a jump move, disallow
            if(player.getTurnStack().peek().isJumpMove() && !move.isJumpMove()){
                return false;
            }
        }


        //Run through all move rules to make sure a legal move is made
        for (Rules rule : rules) {
            if(rule.checkMoves(move, temporaryBoard)){
                temporaryBoard.makeTempMove(move);
                return true;
            }
        }

        return false;
    }

    /**
     * Method to determine whether the turn stack is valid
     * Will fail if there are jump moves available that the user does not take
     *
     * @param turnStack - Stack of Moves
     * @param board - original board
     * @return - whether or not the move stack is valid
     */
    public static boolean validateTurn(Stack<Move> turnStack, Board board){

        //Start with fresh temp board
        Board tempBoard = new Board(board);

        //Runs through the turn stack and makes the moves on to a temp board
        for(Move move : turnStack){

            //If at any point a simple move is made when there are jump moves available, disallow the move set
            if(allJumpRule.checkMoves(null, tempBoard) && !move.isJumpMove()){
                return false;
            }
            tempBoard.makeTempMove(move);
        }

        //Debugging code I don't want to remove
//        System.out.println("jump move available: " + allJumpRule.checkMoves(null, tempBoard));
//        System.out.println("THIS PIECE JUMP MOVE AVAILABLE: " + oneJumpRule.checkMoves(turnStack.peek(), tempBoard));
//        System.out.println("was jump move: " + turnStack.peek().isJumpMove());
//        System.out.println("ACTIVE COLOR: " + tempBoard.getActiveColor().toString());

        //Checks if jump move is available after a jump move was made, If so disallow
        if(oneJumpRule.checkMoves(turnStack.peek(), tempBoard) && turnStack.peek().isJumpMove()){
            return false;
        }
        return true;
    }

    /**
     * Processes the real moves on the original board
     *
     * @param player - Player making the moves
     * @param board - original board
     */
    public static void processMoves(Player player, Board board){
        Stack<Move> turnStack = player.getTurnStack();
        while (!turnStack.empty()) {
            board.makeMove(turnStack.remove(0));
        }
        board.switchTurn();
    }


    /**
     * Refreshes the temporary board after a move has been undone
     * In order to 'go back' a move, the temporary board is reset, and
     * all moves minus one are redone to emulate an 'undone' move
     *
     * @param board - original board
     * @param player - Player making the move
     */
    public static void refreshTempBoard(Board board, Player player){

        //get the turn stack without the reverted move
        Stack<Move> turnStack = player.getTurnStack();

        //Reset the temporary board
        temporaryBoard = new Board(board);

        //Make all moves to get the 'undone' board state
        for (Move move : turnStack) {
            temporaryBoard.makeTempMove(move);
        }
    }
}
