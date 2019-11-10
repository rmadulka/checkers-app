package com.webcheckers.model;

import java.util.ArrayList;

public class Game {

    /** Represents the player who controls the red pieces */
    private Player redPlayer;
    /** Represents the player who controls the white pieces */
    private Player whitePlayer;
    /** Tracks and lists the moves made by each player to be replayed **/
    private ArrayList<BoardState> boardStates = new ArrayList<>();
    /** unique game id */
    private int id;

    /**
     * Represents a recorded game that is ready to be replayed
     * @param redPlayer player who controls the red pieces
     * @param whitePlayer player who controls the white pieces
     * @param id unique game id
     */
    public Game(Player redPlayer, Player whitePlayer, int id){
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.id = id;
    }

    /**
     * gets the total moves made and submitted by both players
     * @return boardStates
     */
    public ArrayList<BoardState> getBoardStates(){
        return boardStates;
    }

    /**
     * Adds a completed turn into the boardState array
     * @param boardState completed move
     */
    public void addGameState(BoardState boardState){
        boardStates.add(boardState);
    }

    /**
     * gets the unique game id
     * @return unique game id
     */
    public int getId(){
        return id;
    }

    /**
     * gets the red player
     * @return red player
     */
    public Player getRedPlayer(){
        return redPlayer;
    }

    /**
     * gets the white player
     * @return white player
     */
    public Player getWhitePlayer(){
        return whitePlayer;
    }
}
