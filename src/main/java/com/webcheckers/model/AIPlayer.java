package com.webcheckers.model;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.MoveProcessor;

import java.util.Stack;


public class AIPlayer extends Player implements Runnable{
    private static int id = 0;

    private PlayerLobby playerLobby;

    private GameLobby gameLobby;

    public AIPlayer(PlayerLobby playerLobby){
        super("Joe Mama" + id);
        id++;
        this.playerLobby = playerLobby;
    }

    @Override
    public boolean addInGameStatus(){
        turnStack = new Stack<>();
        this.inGame = false;
        this.gameLobby = playerLobby.getGameLobby(this);
        new Thread(this).start();
        return false;
    }

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

    private void makeTurn(){
        System.out.println("AI Makes a Move");

        int endRow = (int)(Math.random()*7);
        int endCell = (int)(Math.random()*7);

        Position start = findPiece();
        Position end = new Position(endRow, endCell);

        Move move = new Move(start, end);
        turnStack.push(move);
        MoveProcessor.processMoves(this, gameLobby.getBoard());
    }

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

    private boolean checkMyTurn(){
        if ((gameLobby.getRedPlayer().equals(this) && gameLobby.getBoard().getActiveColor() == Piece.pieceColor.RED) ||
                (gameLobby.getWhitePlayer().equals(this) && gameLobby.getBoard().getActiveColor() == Piece.pieceColor.WHITE)){
            return true;
        } else {
            return false;
        }
    }
}
