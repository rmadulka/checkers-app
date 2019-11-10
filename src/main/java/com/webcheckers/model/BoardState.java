package com.webcheckers.model;

public class BoardState {

    private Board board;

    private Space[][] newBoard;

    public BoardState(Board b) {
        board = b;
    }

    private void init() {
        this.newBoard = new Space[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row % 2 == 0) {
                    if (col % 2 == 0) newBoard[row][col] = new Space(col, false);
                    else newBoard[row][col] = new Space(col, true);
                } else {
                    if (col % 2 == 1) newBoard[row][col] = new Space(col, false);
                    else newBoard[row][col] = new Space(col, true);
                }
            }
        }
    }

    public void fillSpaces() {
        init();
        Space[][] tempBoard = board.getBoard();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece checkerPiece = tempBoard[r][c].getPiece();
                if (checkerPiece != null) {
                    if (checkerPiece.getColor() == Piece.pieceColor.RED && checkerPiece.getType() == Piece.pieceType.KING) {
                        newBoard[r][c].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
                    } else if (checkerPiece.getColor() == Piece.pieceColor.WHITE && checkerPiece.getType() == Piece.pieceType.KING) {
                        newBoard[r][c].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.WHITE));
                    } else if (checkerPiece.getColor() == Piece.pieceColor.RED && checkerPiece.getType() == Piece.pieceType.SINGLE) {
                        newBoard[r][c].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
                    } else if (checkerPiece.getColor() == Piece.pieceColor.WHITE && checkerPiece.getType() == Piece.pieceType.SINGLE) {
                        newBoard[r][c].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));

                    }
                }
            }
        }
    }

    public Board getBoardState(){
        fillSpaces();
        board.setBoard(newBoard);
        return board;
    }
}
