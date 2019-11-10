package com.webcheckers.model;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.MoveProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class AIPlayer extends Player implements Runnable{

    /** Id to ensure each AIPlayer is Unique */
    private static int id = 0;

    /** Playerlobby to access gameLobbies */
    private PlayerLobby playerLobby;

    /** GameLobby that AIPlayer is a part of */
    private GameLobby gameLobby;

    /** AIPlayer Constructor */
    public AIPlayer(PlayerLobby playerLobby){
        super("Joseph 'Joe' Mama" + id);
        id++;
        this.playerLobby = playerLobby;
    }

    /**
     * Adds the AI Player to the gameLobby but sets its inGame status to false
     * This allows multiple players to click on an AIPlayer and when
     * the User leaves the game, the AI Player also leaves, thus closing the gameLobby
     * @return inGameLobby status
     */
    @Override
    public boolean addInGameStatus(){
        turnStack = new Stack<>();
        this.inGame = false;
        this.gameLobby = playerLobby.getGameLobby(this);
        new Thread(this).start();
        return false;
    }

    /**
     * Checks for a turn every 5 seconds and makes a turn
     */
    public void run(){
        while(!gameLobby.getIsGameOver()){
            if(checkMyTurn()){
                makeTurn();
            } else {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ie){
                    ie.printStackTrace();
                }
            }
        }
    }

    /**
     * Makes a turn on the board
     */
    private void makeTurn(){
        //TODO Fix valid turns
        System.out.println("AI Makes a Move");
        Move move = getRandomMove(gameLobby.getBoard());
        turnStack.push(move);
        MoveProcessor.processMoves(this, gameLobby.getBoard());
    }

    /**
     * Helper funciton for makeMove()
     */
    private Position findPiece(){
        boolean foundPiece = false;
        int startRow = 0;
        int startCell = 0;
        while (!foundPiece){
            startRow = (int)(Math.random()*8);
            startCell = (int)(Math.random()*8);
            foundPiece = gameLobby.getBoard().getBoard()[startRow][startCell].getPiece() != null &&
                    gameLobby.getBoard().getBoard()[startRow][startCell].getPiece().getColor() == Piece.pieceColor.WHITE;
        }
        return new Position(startRow, startCell);
    }

    /**
     * Checks to see if it is the AI Player's turn
     * @return - boolean to determine the players turn
     */
    private boolean checkMyTurn(){
        if ((gameLobby.getRedPlayer().equals(this) && gameLobby.getBoard().getActiveColor() == Piece.pieceColor.RED) ||
                (gameLobby.getWhitePlayer().equals(this) && gameLobby.getBoard().getActiveColor() == Piece.pieceColor.WHITE)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns an arraylist containing a move chosen randomly from a list of valid moves
     * @param board the board model
     * @return an arraylist of valid moves
     */
    private Move getRandomMove(Board board) {
//        ArrayList<Move> moves = new ArrayList<>();
//        for (int row = 0; row < 8; row++) {
//            for (int col =0; col < 8; col++) {
//                Position pos = new Position(row, col);
//                Space space = board.getSpace(pos);
//                Piece aiPiece = space.getPiece();
//                if (aiPiece != null && aiPiece.getColor() == Piece.pieceColor.WHITE) {
//                    moves.addAll(getValidSimpleMoves(board, pos));
//                    moves.addAll(getValidJumpMoves(board,pos));
//                }
//            }
//        }
//        int randMove = (int)(Math.random() * moves.size());
//        Move move = moves.get(randMove);
//        return move;
        ArrayList<Move> moves = new ArrayList<>();
        List<Move> jumps = getValidJumpMoves(board);
        if(jumps.size() == 0) {
            moves.addAll(getValidSimpleMoves(board));
        }
        moves.addAll(jumps);
        int randMove = (int)(Math.random() * moves.size());
        return moves.get(randMove);
    }

    /**
     * returns an arraylist of all valid simple moves
     * @param board the board model
     * @return an arraylist of valid simple moves
     */
    private List<Move> getValidSimpleMoves(Board board) {
        ArrayList<Move> validSimpleMoves = new ArrayList<>();
        Space[][] gameBoard = board.getBoard();
        int negOne = -1;
        for (int row = 0; row < gameBoard.length; row ++) {
            for (int col = 0; col < gameBoard.length; col++) {
                if (!(row + negOne > gameBoard.length - 1 || row + negOne < 0)) {
                    //check that this is the moving player's piece
                    if (gameBoard[row][col].getPiece() != null &&
                            gameBoard[row][col].getPiece().getColor() == board.getActiveColor()) {
                        //check out of bounds, adjacent diagonal right piece is opponent and there is an empty space after
                        //TODO Fix law of demeter here
                        if (!(col + 1 > gameBoard.length - 1) && gameBoard[row + negOne][col + 1].getPiece() == null) {
                            validSimpleMoves.add(new Move(new Position(row, col), new Position(row + negOne, col + 1)));
                        }
                        //check out of bounds, adjacent diagonal left piece is opponent and there is an empty space after
                        //TODO Fix law of demeter here
                        if (col - 1 >= 0 && gameBoard[row + negOne][col - 1].getPiece() == null) {
                            validSimpleMoves.add(new Move(new Position(row, col), new Position(row + negOne, col - 1)));
                        }
                    }
                }
                if (gameBoard[row][col].getPiece() != null && gameBoard[row][col].getPiece().getType() == Piece.pieceType.KING) {
                    if (!(row - negOne > gameBoard.length - 1 || row - negOne < 0)) {
                        //check that this is the moving player's piece
                        if (gameBoard[row][col].getPiece() != null &&
                                gameBoard[row][col].getPiece().getColor() == board.getActiveColor()) {
                            //check out of bounds, adjacent diagonal right piece is opponent and there is an empty space after
                            //TODO Fix law of demeter here
                            if (!(col + 1 > gameBoard.length - 1) && gameBoard[row - negOne][col + 1].getPiece() == null) {
                                validSimpleMoves.add(new Move(new Position(row, col), new Position(row - negOne, col + 1)));
                            }
                            //check out of bounds, adjacent diagonal left piece is opponent and there is an empty space after
                            //TODO Fix law of demeter here
                            if (col - 1 >= 0 && gameBoard[row - negOne][col - 1].getPiece() == null) {
                                validSimpleMoves.add(new Move(new Position(row, col), new Position(row - negOne, col - 1)));
                            }
                        }
                    }
                }
            }
        }
        return validSimpleMoves;
    }

    /**
     * Returns an arraylist of all valid jump moves.
     * @param board the board model
     * @return an arraylist of jump moves
     */
    private List<Move> getValidJumpMoves(Board board) {
        ArrayList<Move> validJumpMoves = new ArrayList<>();
        Space[][] gameBoard = board.getBoard();
        int negOne = -1;
        for (int row = 0; row < gameBoard.length; row ++) {
            for (int col = 0; col < gameBoard.length; col++) {
                if (!(row + negOne * 2 > gameBoard.length - 1 || row + negOne * 2 < 0)) {
                    //check that this is the moving player's piece
                    if (gameBoard[row][col].getPiece() != null &&
                            gameBoard[row][col].getPiece().getColor() == board.getActiveColor()) {
                        //check out of bounds, adjacent diagonal right piece is opponent and there is an empty space after
                        //TODO Fix law of demeter here
                        if (!(col + 2 > gameBoard.length - 1) && gameBoard[row + negOne][col + 1].getPiece() != null &&
                                gameBoard[row + negOne][col + 1].getPiece().getColor() != board.getActiveColor() &&
                                gameBoard[row + negOne * 2][col + 2].getPiece() == null) {
                            validJumpMoves.add(new Move(new Position(row, col), new Position(row + negOne*2, col + 2)));
                        }
                        //check out of bounds, adjacent diagonal left piece is opponent and there is an empty space after
                        //TODO Fix law of demeter here
                        if (col - 2 >= 0 && gameBoard[row + negOne][col - 1].getPiece() != null &&
                                gameBoard[row + negOne][col - 1].getPiece().getColor() != board.getActiveColor() &&
                                gameBoard[row + negOne * 2][col - 2].getPiece() == null) {
                            validJumpMoves.add(new Move(new Position(row, col), new Position(row + negOne*2, col - 2)));
                        }
                    }
                }
                if (gameBoard[row][col].getPiece() != null && gameBoard[row][col].getPiece().getType() == Piece.pieceType.KING) {
                    if (!(row - negOne * 2 > gameBoard.length - 1 || row - negOne * 2 < 0)) {
                        //check that this is the moving player's piece
                        if (gameBoard[row][col].getPiece() != null &&
                                gameBoard[row][col].getPiece().getColor() == board.getActiveColor()) {
                            //check out of bounds, adjacent diagonal right piece is opponent and there is an empty space after
                            //TODO Fix law of demeter here
                            if (!(col + 2 > gameBoard.length - 1) && gameBoard[row - negOne][col + 1].getPiece() != null &&
                                    gameBoard[row - negOne][col + 1].getPiece().getColor() != board.getActiveColor() &&
                                    gameBoard[row - negOne * 2][col + 2].getPiece() == null) {
                                validJumpMoves.add(new Move(new Position(row, col), new Position(row - negOne*2, col + 2)));
                            }
                            //check out of bounds, adjacent diagonal left piece is opponent and there is an empty space after
                            //TODO Fix law of demeter here
                            if (col - 2 >= 0 && gameBoard[row - negOne][col - 1].getPiece() != null &&
                                    gameBoard[row - negOne][col - 1].getPiece().getColor() != board.getActiveColor() &&
                                    gameBoard[row - negOne * 2][col - 2].getPiece() == null) {
                                validJumpMoves.add(new Move(new Position(row, col), new Position(row - negOne*2, col - 2)));
                            }
                        }
                    }
                }
            }
        }
        return validJumpMoves;
    }
}
