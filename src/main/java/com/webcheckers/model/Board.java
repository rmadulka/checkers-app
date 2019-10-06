package com.webcheckers.model;

/**
 * model of the board for the boardview ui
 */

 public class Board {
     /** represents the dimensions of the board **/
     private static final int SIZE = 8;
     /** represents the player who controls the white checkers piece (2nd player) **/
     private Player white;
     /** represents the player who controls the red checkers piece (1st player) **/
     private Player red;
     /** represents the board itself as it stores space and row data **/
     private Space[][] board;

    /**
     * Constructs a checkers game board and calls functions to initialize it
     * @param white
     * @param red
     */
     public Board(Player white, Player red) {
         this.white = white;
         this.red = red;
         init();
         populate();
     }

    /**
     * initializes the board by adding dark and white spaces in each position within board
     */
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

    /**
     * Adds red and white pieces within the first two rows for each player's side on board
     */
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

    /**
     * gets current positions of the board
     * @return board
     */
     public Space[][] getBoard() {
         return this.board;
     }

    /**
     * gets the player controlling the red pieces
     * @return red piece player
     */
     public Player getRed() {
         return red;
     }

    /**
     * gets the player controlling the white pieces
     * @return white piece player
     */
     public Player getWhite() {
         return white;
     }

    /**
     * Gets a specific row listed on the board
     * @param row int value that represents a whole row on board
     * @return position data for a whole row
     */
     public Space[] getRow(int row) {
        return board[row];
    }

    /**
     * Swaps the positions on the board in order to accommodate each player's perspective as each player's pieces
     * are initially located on the top of the board, causing positions to be inversely located.
     * @param row int value that represents a row on board
     * @return board with inversely swapped positions
     */
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
