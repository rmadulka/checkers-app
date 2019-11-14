package com.webcheckers.model;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayLobby;
import com.webcheckers.util.Message;
import com.webcheckers.util.MoveProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class AIPlayer extends Player implements Runnable {

    /** Id to ensure each AIPlayer is Unique */
    private static int id = 0;

    /** Playerlobby to access gameLobbies */
    private PlayerLobby playerLobby;

    /** GameLobby that AIPlayer is a part of */
    private GameLobby gameLobby;

    /** AIPlayer Constructor */
    public AIPlayer(PlayerLobby playerLobby){
        super("Joseph 'Joe' Mama " + id);
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
     * AI makes a turn on the board
     * Also determines if the ai has won or lost at the end of the game
     */
    public void makeTurn(){
        Move move = getRandomMove(gameLobby.getBoard());
        if (move != null) {
            turnStack.push(move);
        }
        while (!turnStack.empty() && !(MoveProcessor.validateTurn(turnStack, gameLobby.getBoard()))) {
            gameLobby.getBoard().makeMove(move);
            move = getMultijump(move, gameLobby.getBoard());
            turnStack.push(move);
        }
        MoveProcessor.processMoves(this, gameLobby.getBoard());

        Game game = gameLobby.getGame();
        Board board = gameLobby.getBoard();
        BoardState boardState = new BoardState(board.getActiveColor());
        boardState.constructState(board);
        game.addGameState(boardState);
        if(board.checkNoAvailiablePieces()){
            ReplayLobby replayLobby = playerLobby.getReplayLobby();
            replayLobby.addGame(game);
            gameLobby.endGame(Message.info(String.format("%s has no remaining pieces",gameLobby.getBoard().getActiveColor().toString())));
        }
        board.switchTurn();
        if(!board.checkAvailableMove()){
            ReplayLobby replayLobby = playerLobby.getReplayLobby();
            replayLobby.addGame(game);
            gameLobby.endGame(Message.info(String.format("%s has no available moves",gameLobby.getBoard().getActiveColor().toString())));
        }
        board.switchTurn();
    }

    /**
     * Checks to see if it is the AI Player's turn
     * @return - boolean to determine the players turn
     */
    public boolean checkMyTurn(){
        return (gameLobby.getRedPlayer().equals(this) && gameLobby.getBoard().getActiveColor() == Piece.pieceColor.RED) ||
                (gameLobby.getWhitePlayer().equals(this) && gameLobby.getBoard().getActiveColor() == Piece.pieceColor.WHITE);
    }

    /**
     * Returns an arraylist containing a move chosen randomly from a list of valid moves
     * @param board the board model
     * @return an arraylist of valid moves
     */
    public Move getRandomMove(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        List<Move> jumps = getValidJumpMoves(board);
        if(jumps.size() == 0) {
            moves.addAll(getValidSimpleMoves(board));
        }
        moves.addAll(jumps);
        int randMove = (int)(Math.random() * moves.size());
        if (moves.size() > 0) {
            return moves.get(randMove);
        }
        return null;
    }

    /**
     * returns an arraylist of all valid simple moves
     * @param board the board model
     * @return an arraylist of valid simple moves
     */
    public List<Move> getValidSimpleMoves(Board board) {
        ArrayList<Move> validSimpleMoves = new ArrayList<>();
        Space[][] gameBoard = board.getBoard();
        int negOne = -1;
        for (int row = 0; row < gameBoard.length; row ++) {
            for (int col = 0; col < gameBoard.length; col++) {
                if (!(row + negOne > gameBoard.length - 1 || row + negOne < 0)) {
                    //check that this is the moving player's piece
                    if (gameBoard[row][col].getPiece() != null &&
                            gameBoard[row][col].getPieceColor() == board.getActiveColor()) {
                        //check out of bounds, adjacent diagonal right piece is opponent and there is an empty space after
                        if (!(col + 1 > gameBoard.length - 1) && gameBoard[row + negOne][col + 1].getPiece() == null) {
                            validSimpleMoves.add(new Move(new Position(row, col), new Position(row + negOne, col + 1)));
                        }
                        //check out of bounds, adjacent diagonal left piece is opponent and there is an empty space after
                        if (col - 1 >= 0 && gameBoard[row + negOne][col - 1].getPiece() == null) {
                            validSimpleMoves.add(new Move(new Position(row, col), new Position(row + negOne, col - 1)));
                        }
                    }
                }
                if (gameBoard[row][col].getPiece() != null && gameBoard[row][col].getPieceType() == Piece.pieceType.KING) {
                    if (!(row - negOne > gameBoard.length - 1)) {
                        //check that this is the moving player's piece
                        if (gameBoard[row][col].getPiece() != null &&
                                gameBoard[row][col].getPieceColor() == board.getActiveColor()) {
                            //check out of bounds, adjacent diagonal right piece is opponent and there is an empty space after
                            if (!(col + 1 > gameBoard.length - 1) && gameBoard[row - negOne][col + 1].getPiece() == null) {
                                validSimpleMoves.add(new Move(new Position(row, col), new Position(row - negOne, col + 1)));
                            }
                            //check out of bounds, adjacent diagonal left piece is opponent and there is an empty space after
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
    public List<Move> getValidJumpMoves(Board board) {
        ArrayList<Move> validJumpMoves = new ArrayList<>();
        Space[][] gameBoard = board.getBoard();
        for (int row = 0; row < gameBoard.length; row ++) {
            for (int col = 0; col < gameBoard.length; col++) {
                if (!(row - 2 > gameBoard.length - 1 || row - 2 < 0)) {
                    //check that this is the moving player's piece
                    if (gameBoard[row][col].getPiece() != null &&
                            gameBoard[row][col].getPieceColor() == board.getActiveColor()) {
                        //check out of bounds, adjacent diagonal right piece is opponent and there is an empty space after
                        if (!(col + 2 > gameBoard.length - 1) && gameBoard[row - 1][col + 1].getPiece() != null &&
                                gameBoard[row - 1][col + 1].getPieceColor() != board.getActiveColor() &&
                                gameBoard[row - 2][col + 2].getPiece() == null) {
                            validJumpMoves.add(new Move(new Position(row, col), new Position(row - 2, col + 2)));
                        }
                        //check out of bounds, adjacent diagonal left piece is opponent and there is an empty space after
                        if (col - 2 >= 0 && gameBoard[row - 1][col - 1].getPiece() != null &&
                                gameBoard[row - 1][col - 1].getPieceColor() != board.getActiveColor() &&
                                gameBoard[row - 2][col - 2].getPiece() == null) {
                            validJumpMoves.add(new Move(new Position(row, col), new Position(row - 2, col - 2)));
                        }
                    }
                }
                if (gameBoard[row][col].getPiece() != null && gameBoard[row][col].getPieceType() == Piece.pieceType.KING) {
                    if (!(row + 2 > gameBoard.length - 1 || row + 2 < 0)) {
                        //check that this is the moving player's piece
                        if (gameBoard[row][col].getPiece() != null &&
                                gameBoard[row][col].getPieceColor() == board.getActiveColor()) {
                            //check out of bounds, adjacent diagonal right piece is opponent and there is an empty space after
                            if (!(col + 2 > gameBoard.length - 1) && gameBoard[row + 1][col + 1].getPiece() != null &&
                                    gameBoard[row + 1][col + 1].getPieceColor() != board.getActiveColor() &&
                                    gameBoard[row + 2][col + 2].getPiece() == null) {
                                validJumpMoves.add(new Move(new Position(row, col), new Position(row + 2, col + 2)));
                            }
                            //check out of bounds, adjacent diagonal left piece is opponent and there is an empty space after
                            if (col - 2 >= 0 && gameBoard[row + 1][col - 1].getPiece() != null &&
                                    gameBoard[row + 1][col - 1].getPieceColor() != board.getActiveColor() &&
                                    gameBoard[row + 2][col - 2].getPiece() == null) {
                                validJumpMoves.add(new Move(new Position(row, col), new Position(row + 2, col - 2)));
                            }
                        }
                    }
                }
            }
        }
        return validJumpMoves;
    }

    /**
     * Finds the multijump move after the ai performs a jump
     * @param move a jump move
     * @param board The board
     * @return the move that the ai must perform
     */
    public Move getMultijump(Move move, Board board) {
        Space[][] gameBoard = board.getBoard();
        if(move.getEndRow() - 2 >= 0 && gameBoard[move.getEndRow()][move.getEndCell()].getPieceColor() == Piece.pieceColor.WHITE) {
            //checks out of bounds, if next piece is an enemy piece and the space after is empty
            if (!(move.getEndCell() - 2 < 0) &&
                    gameBoard[move.getEndRow() -1 ][move.getEndCell() - 1].getPiece() != null &&
                    gameBoard[move.getEndRow() - 1][move.getEndCell() - 1].getPieceColor() != board.getActiveColor() &&
                    gameBoard[move.getEndRow() - 2][move.getEndCell() - 2].getPiece() == null) {
                return new Move(new Position(move.getEndRow(), move.getEndCell()), new Position(move.getEndRow() - 2, move.getEndCell() - 2));
            }
            //checks out of bounds, if next piece is an enemy piece and the space after is empty
            if (!(move.getEndCell() + 2 > gameBoard.length - 1) &&
                    gameBoard[move.getEndRow() - 1][move.getEndCell() + 1].getPiece() != null &&
                    gameBoard[move.getEndRow() - 1][move.getEndCell() + 1].getPieceColor() != board.getActiveColor() &&
                    gameBoard[move.getEndRow() - 2][move.getEndCell() + 2].getPiece() == null) {
                return new Move(new Position(move.getEndRow(), move.getEndCell()), new Position(move.getEndRow() - 2, move.getEndCell() + 2));
            }
        }
        if (gameBoard[move.getEndRow()][move.getEndCell()].getPieceType() == Piece.pieceType.KING &&
                ((move.getEndRow() <= gameBoard.length - 2))) {
            if (!(move.getEndCell() - 2 < 0) &&
                    gameBoard[move.getEndRow() + 1][move.getEndCell() - 1].getPiece() != null &&
                    gameBoard[move.getEndRow() + 1][move.getEndCell() - 1].getPieceColor() != board.getActiveColor() &&
                    gameBoard[move.getEndRow() + 2][move.getEndCell() - 2].getPiece() == null) {
                return new Move(new Position(move.getEndRow(), move.getEndCell()), new Position(move.getEndRow() + 2, move.getEndCell() - 2));
            }
        }
        return new Move(new Position(move.getEndRow(), move.getEndCell()), new Position(move.getEndRow() + 2, move.getEndCell() + 2));
    }
}
