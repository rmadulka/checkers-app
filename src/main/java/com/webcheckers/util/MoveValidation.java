package com.webcheckers.util;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Space;

public class MoveValidation {

    public static boolean validateMove (Move move, Board board) {
        Space[][] gameBoard = board.getBoard();
        return gameBoard[move.getEndRow()][move.getEndCell()].isValid() &&
                move.getStartRow() + 1 == move.getEndRow() &&
                (move.getStartCell() + 1 == move.getEndCell() || move.getStartCell() - 1 == move.getEndCell());
    }
}
