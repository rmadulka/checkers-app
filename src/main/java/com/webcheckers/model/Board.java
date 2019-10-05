package com.webcheckers.model;

/**
 * model of the board for the boardview ui
 */

 public class Board {
     private static final int SIZE = 8;
     private Player white;
     private Player red;
     private Space[][] board;

     public Board(Player white, Player red) {
         this.white = white;
         this.red = red;
         init();
         populate();
     }

     private void init() {
         this.board = new Space[SIZE][SIZE];
         for (int row = 0; row < SIZE; row++) {
             for (int col = 0; col < SIZE; col++) {
                 if (row % 2 == 0) {
                     if (col % 2 == 0) board[row][col] = new Space(col, false);
                     else board[row][col] = new Space(col, true);
                 } else {
                     if (col % 2 == 1) board[row][col] = new Space(col, false);
                     else board[row][col] = new Space(col, true);
                 }
             }
         }
     }

     private void populate() {
         for (int row = 0; row < SIZE; row++) {
             for (int col = 0; col < SIZE; col++) {
                if(row < 3) { // these rows need to have red pieces on black spaces
                    if (board[row][col].isValid()) {
                        board[row][col].place(new Piece(Piece.pieceType.PAWN, Piece.pieceColor.RED));
                    }
                }
                else if (row > 4) { // these rows need to have white pieces on black spaces
                    if (board[row][col].isValid()) {
                        board[row][col].place(new Piece(Piece.pieceType.PAWN, Piece.pieceColor.WHITE));
                    }
                }
             }
         }
     }

     public Space[][] getBoard() {
         return this.board;
     }

     public Player getRed() {
         return red;
     }

     public Player getWhite() {
         return white;
     }

     public Space[] getRow(int row) {
        return board[row];
    }

    public Space[] reverseRow(int row) {
        Space[] toReverse = getRow(row);
        Space[] reversed = new Space[SIZE];
        int last = SIZE;
        for (int space = 0; space < SIZE; space++) {
            last--;
            reversed[space] = toReverse[last];
        }
        return reversed;
    }
 }
