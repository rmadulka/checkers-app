package com.webcheckers.util;

import com.webcheckers.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class MoveProcessor {

    /** An arraylist of all the types of moves a player can make */
    static final ArrayList<Rules> rules = new ArrayList<>(Arrays.asList(new SimpleMove(), new JumpMove(), new KingSimpleMove(), new KingJumpMove()));

    static final Rules allJumpRule = new CheckAllJumpMove();

    static final Rules oneJumpRule = new CheckOneJumpMove();
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
        if(player.getTurnStack().empty()) {
            temporaryBoard = new Board(board);
        }
        if (!player.getTurnStack().empty() && !player.getTurnStack().peek().isJumpMove()) {
            return false;
        }

        temporaryBoard.printBoard(temporaryBoard);


        for (Rules rule : rules) {
            if(rule.checkMoves(move, temporaryBoard)){
                temporaryBoard.makeTempMove(move);
                return true;
            }
        }
        System.err.println("Made it thorugh all rules");
        return false;
    }

    public static boolean validateTurn(Stack<Move> turnStack, Board board){
        Board tempBoard = new Board(board);

        //Runs through the turn stack and makes the moves on to a temp board
        for(Move move : turnStack){
            if(allJumpRule.checkMoves(null, tempBoard) && !move.isJumpMove()){
                return false;
            }
            tempBoard.makeTempMove(move);
        }

        System.out.println("jump move available: " + allJumpRule.checkMoves(null, tempBoard));
        System.out.println("THIS PIECE JUMP MOVE AVAILABLE: " + oneJumpRule.checkMoves(turnStack.peek(), tempBoard));
        System.out.println("was jump move: " + turnStack.peek().isJumpMove());
        System.out.println("ACTIVE COLOR: " + tempBoard.getActiveColor().toString());

        //Checks if jump move is available after a jump move was made
        if(oneJumpRule.checkMoves(turnStack.peek(), tempBoard) && turnStack.peek().isJumpMove()){
            return false;
        }
        return true;
    }

    public static void processMoves(Player player, Board board){
        Stack<Move> turnStack = player.getTurnStack();
        while (!turnStack.empty()) {
            board.makeMove(turnStack.remove(0));
        }
        board.switchTurn();
    }



    public static void refreshTempBoard(Board board, Player player){
        Stack<Move> turnStack = player.getTurnStack();

        temporaryBoard = new Board(board);

        for (Move move : turnStack) {
            temporaryBoard.makeMove(move);
        }

    }
}
