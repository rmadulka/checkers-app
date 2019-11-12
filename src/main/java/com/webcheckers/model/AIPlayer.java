package com.webcheckers.model;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.MoveProcessor;

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
        super("Joe Mama" + id);
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
        //TODO - when game is running
        while(true){
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

        int endRow = (int)(Math.random()*8);
        int endCell = (int)(Math.random()*8);

        Position start = findPiece();
        Position end = new Position(endRow, endCell);

        Move move = new Move(start, end);
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
}
