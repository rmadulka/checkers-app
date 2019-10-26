package com.webcheckers.model;

public class MoveValidation {

    private Move move;
    private Board board;
    private Space[][] gameBoard;

    public MoveValidation (Move move, Board board) {
        this.move = move;
        this.board = board;
        this.gameBoard = board.getBoard();
    }

    public boolean validateMove () {
        return this.gameBoard[move.getEnd().getRow()][move.getEnd().getCell()] == null && move.getStart().getRow() ==
                move.getEnd().getRow() + 1 && (move.getStart().getCell() == move.getEnd().getCell() + 1 ||
                move.getStart().getCell() == move.getEnd().getCell() - 1);
    }
}
