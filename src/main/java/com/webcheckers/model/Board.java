package com.webcheckers.model;

//import apple.laf.JRSUIConstants;
import com.webcheckers.util.MoveProcessor;
import spark.ModelAndView;

import java.util.Arrays;
import java.util.Stack;

/**
 * model of the board for the boardview ui
 */

 public class Board {
     /** represents the dimensions of the board **/
     private static final int SIZE = 8;
     /** represents the player who controls the white checkers piece (2nd player) **/
     private Player whitePlayer;
     /** represents the player who controls the red checkers piece (1st player) **/
     private Player redPlayer;
     /** represents the board itself as it stores space and row data **/
     private Space[][] board;

     private Piece.pieceColor activeColor;

    /**
     * Constructs a checkers game board and calls functions to initialize it
     * @param whitePlayer player who controls white pieces
     * @param redPlayer player who controls red pieces
     */
     public Board(Player whitePlayer, Player redPlayer) {
         this.whitePlayer = whitePlayer;
         this.redPlayer = redPlayer;
         activeColor = Piece.pieceColor.RED;
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
                        board[row][col].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
                    }
                }
                else if (row > 4) { // these rows need to have white pieces on black spaces
                    if (board[row][col].isValid()) {
                        board[row][col].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
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
     * gets the player controlling the white pieces
     * @return white piece player
     */
     public Player getWhite() {
         return this.whitePlayer;
    }

    /**
     * gets the player controlling the red pieces
     * @return red piece player
     */
     public Player getRed() {
         return this.redPlayer;
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
     * are initially located on the top of the board, causing positions to be inversely related between both players.
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

    /**
     * Alternates the activeColor in order to determine and update whose current turn it is
     */
    public void switchTurn(){
        if(activeColor.equals(Piece.pieceColor.RED)){
            activeColor = Piece.pieceColor.WHITE;
        } else if (activeColor.equals(Piece.pieceColor.WHITE)){
            activeColor = Piece.pieceColor.RED;
        }
    }


    /**
     * returns the activeColor, which represents whose turn it currently is
     * @return activeColor
     */
    public Piece.pieceColor getActiveColor(){
        return activeColor;
    }

    /**
     * Removes and places a piece from its old position to its new position, while also verifying that the piece has
     * not been moved past 2 rows. After the move has been finalized, the player's current turn is over
     * @param move newly made move
     */
    public void makeMove(Move move) {
        Piece moving = board[move.getStartRow()][move.getStartCell()].getPiece();
        board[move.getStartRow()][move.getStartCell()].removePiece();
        board[move.getEndRow()][move.getEndCell()].place(moving);
        int diff = Math.abs(move.getStartRow() - move.getEndRow());
        if (diff == 2) {
            Position moveTo = new Position(((move.getStartRow() + move.getEndRow()) / 2), ((move.getStartCell() + move.getEndCell()) / 2));
            board[moveTo.getRow()][moveTo.getCell()].removePiece();
        }
    }

    /**
     * Converts a SINGLE piece to a KING piece when a piece reaches the last row of the board
     * @param move: move in the final row, provides an EndCell location
     */
    public void convertKingPiece(Move move){
        if(MoveProcessor.reachedEnd(this, move)){
            int column = move.getEndCell();
            Piece currentPiece = board[SIZE][column].getPiece();
            if(currentPiece.getType() == Piece.pieceType.SINGLE){
                currentPiece.convertToKing(currentPiece);
            }
        }
    }

}
