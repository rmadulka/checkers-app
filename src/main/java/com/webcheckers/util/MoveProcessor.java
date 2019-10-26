package com.webcheckers.util;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Space;

import java.util.Stack;

public class MoveProcessor {

    public static boolean validateMove (Move move, Board board) {
        Space[][] gameBoard = board.getBoard();
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
}
