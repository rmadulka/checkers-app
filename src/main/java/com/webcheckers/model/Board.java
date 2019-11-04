package com.webcheckers.model;

//import apple.laf.JRSUIConstants;

import com.webcheckers.util.MoveProcessor;

/**
 * model of the board for the boardview ui
 */

 public class Board {
    /**
     * represents the dimensions of the board
     **/
    private static final int SIZE = 8;
    /**
     * represents the player who controls the white checkers piece (2nd player)
     **/
    private Player whitePlayer;
    /**
     * represents the player who controls the red checkers piece (1st player)
     **/
    private Player redPlayer;
    /**
     * represents the board itself as it stores space and row data
     **/
    private Space[][] board;
    /**
     * The color of the piece that is making a turn
     */
    private Piece.pieceColor activeColor;

    /**
     * Constructs a checkers game board and calls functions to initialize it
     *
     * @param whitePlayer player who controls white pieces
     * @param redPlayer   player who controls red pieces
     */
    public Board(Player whitePlayer, Player redPlayer) {
        this.whitePlayer = whitePlayer;
        this.redPlayer = redPlayer;
        activeColor = Piece.pieceColor.RED;
        init();
        //populate();
        //customPopulate();
        endGameTestOne();
        //endGameTestTwo();
    }

    /**
     * Creates a temporary copy of the board
     *
     * @param board The original board
     */
    public Board(Board board) {
        this.whitePlayer = board.getWhite();
        this.redPlayer = board.getRed();
        this.board = copyBoard(board);
        this.activeColor = board.getActiveColor();
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
                if (row < 3) { // these rows need to have red pieces on black spaces
                    if (board[row][col].isValid()) {
                        board[row][col].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
                    }
                } else if (row > 4) { // these rows need to have white pieces on black spaces
                    if (board[row][col].isValid()) {
                        board[row][col].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
                    }
                }
            }
        }
    }

    /**
     * A Testing board that is used to test moves without replaying an entire game of checkers
     */
    private void customPopulate() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (row == 4) { // these rows need to have red pieces on black spaces
                    if (board[row][col].isValid()) {
                        board[row][col].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
                    }
                } else if (row == 6) { // these rows need to have white pieces on black spaces
                    if (board[row][col].isValid()) {
                        board[row][col].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
                    }
                } else if (row == 0) { // these rows need to have red pieces on black spaces
                    if (board[row][col].isValid()) {
                        board[row][col].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
                    }
                } else if (row == 2) { // these rows need to have white pieces on black spaces
                    if (board[row][col].isValid()) {
                        board[row][col].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.WHITE));
                    }
                }
            }
        }
    }

    private void endGameTestOne() {
        board[0][1].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
        board[2][3].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.WHITE));

        board[3][0].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
        board[4][1].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
        board[5][2].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
    }

    private void endGameTestTwo() {
        board[0][1].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
        board[3][4].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.WHITE));
    }

    /**
     * gets current positions of the board
     *
     * @return board
     */
    public Space[][] getBoard() {
        return this.board;
    }

    /**
     * gets the player controlling the white pieces
     *
     * @return white piece player
     */
    public Player getWhite() {
        return this.whitePlayer;
    }

    /**
     * gets the player controlling the red pieces
     *
     * @return red piece player
     */
    public Player getRed() {
        return this.redPlayer;
    }

    /**
     * Gets a specific row listed on the board
     *
     * @param row int value that represents a whole row on board
     * @return position data for a whole row
     */
    public Space[] getRow(int row) {
        return board[row];
    }

    /**
     * Returns space data at a position
     *
     * @param pos position of space/piece
     * @return space data at pos
     */
    public Space getSpace(Position pos) {
        int row = pos.getRow();
        int cell = pos.getCell();
        return getRow(row)[cell];
    }

    /**
     * Swaps the positions on the board in order to accommodate each player's perspective as each player's pieces
     * are initially located on the top of the board, causing positions to be inversely related between both players.
     *
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
    public void switchTurn() {
        if (activeColor.equals(Piece.pieceColor.RED)) {
            activeColor = Piece.pieceColor.WHITE;
        } else if (activeColor.equals(Piece.pieceColor.WHITE)) {
            activeColor = Piece.pieceColor.RED;
        }
    }


    /**
     * returns the activeColor, which represents whose turn it currently is
     *
     * @return activeColor
     */
    public Piece.pieceColor getActiveColor() {
        return activeColor;
    }

    /**
     * Removes and places a piece from its old position to its new position, while also verifying that the piece has
     * not been moved past 2 rows. After the move has been finalized, the player's current turn is over
     *
     * @param move newly made move
     */
    public void makeMove(Move move) {
        Piece moving = board[move.getStartRow()][move.getStartCell()].getPiece();
        board[move.getStartRow()][move.getStartCell()].removePiece();
        board[move.getEndRow()][move.getEndCell()].place(moving);
        int diff = Math.abs(move.getStartRow() - move.getEndRow());
        if (reachedEnd(move)) {
            convertKingPiece(move);
        }
        if (diff == 2) {
            Position moveTo = new Position(((move.getStartRow() + move.getEndRow()) / 2), ((move.getStartCell() + move.getEndCell()) / 2));
            board[moveTo.getRow()][moveTo.getCell()].removePiece();
        }
    }

    /**
     * Method for making moves on a temporary board
     * Only difference is that pieces are never promoted to kings,
     * which disallows for further moves after a piece reaches the end
     *
     * @param move Move to be made
     */
    public void makeTempMove(Move move) {
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
     *
     * @param move: move in the final row, provides an EndCell location
     */
    public void convertKingPiece(Move move) {
        int column = move.getEndCell();
        int row = move.getEndRow();
        Piece currentPiece = board[row][column].getPiece();
        if (currentPiece.getType() == Piece.pieceType.SINGLE) {
            currentPiece.convertToKing(currentPiece);
        }
    }

    /**
     * Debugging code to align temp boards with the original boards
     *
     * @param board - board to be printed
     */
    public void printBoard(Board board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board.getBoard()[row][col].getPiece() != null) {
                    System.out.print("1");
                } else {
                    System.out.print("0");
                }
            }
            System.out.println("");
        }
    }

    /**
     * Determines if a checkers piece reaches the end of the board
     *
     * @param move The move that the player made
     * @return True if the player has reached the end of the board
     */
    public boolean reachedEnd(Move move) {
        return move.getEndRow() == board.length - 1 || move.getEndRow() == 0;
    }

    /**
     * Used to create a temporary board before moves are fully validated and finalized
     *
     * @param board board with not fully validated moves
     * @return temporary board
     */
    public Space[][] copyBoard(Board board) {
        Space[][] newBoard = new Space[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (row % 2 == 0) {
                    if (col % 2 == 0) newBoard[row][col] = new Space(col, false);
                    else newBoard[row][col] = new Space(col, true);
                } else {
                    if (col % 2 == 1) newBoard[row][col] = new Space(col, false);
                    else newBoard[row][col] = new Space(col, true);
                }
            }
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board.getBoard()[row][col].getPiece() != null) {
                    newBoard[row][col].place(board.getBoard()[row][col].copyPiece());
                }
            }
        }

        return newBoard;
    }

    /**
     * Iterates throughout the board counting the pieces of each player
     *
     * @return true if both pieces are present, false if only one piece is present indicating someone won
     */
    public boolean checkNoAvailiablePieces() {
        int redPiece = 0;
        int whitePiece = 0;
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board[r][c].getPiece() != null) {
                    Piece currentPiece = board[r][c].getPiece();
                    if (currentPiece.getColor() == Piece.pieceColor.RED) {
                        redPiece++;
                    } else {
                        whitePiece++;
                    }
                }
            }
        }
        if (whitePiece == 0 || redPiece == 0) {
            return true;
        }
        return false;
    }

    public boolean checkAvailableMove(){
        return MoveProcessor.allJumpRule.checkMoves(null, this) || MoveProcessor.allSimpleMoveRule.checkMoves(null, this);
    }
}
