package com.webcheckers.util;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Space;

public class MoveValidation {

    public static boolean validateMove (Move move, Board board) {
        Space[][] gameBoard = board.getBoard();
        return gameBoard[move.getEndRow()][move.getEnd().getRow()].isValid() &&
                move.getStartRow() == move.getEndRow() + 1 &&
                (move.getStartCell() == move.getEndCell() + 1 ||
                move.getStartCell() == move.getEndCell() - 1);
    }
}
