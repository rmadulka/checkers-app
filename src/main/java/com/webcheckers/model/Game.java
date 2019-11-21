package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Date;

public class Game {

    /** Represents the player who controls the red pieces */
    private Player redPlayer;
    /** Represents the player who controls the white pieces */
    private Player whitePlayer;
    /** Tracks and lists the moves made by each player to be replayed **/
    private ArrayList<BoardState> boardStates = new ArrayList<>();
    /** unique game id */
    private int id;
    /** used to given a unique id for each Game */
    private static int idCount = 0;
    /** acts as an index for the boardStates array, tracking what boardState is currently on display **/
    private int currentState;

    private Date endDate = null;

    /**
     * Represents a recorded game that is ready to be replayed
     * @param redPlayer player who controls the red pieces
     * @param whitePlayer player who controls the white pieces
     */
    public Game(Player redPlayer, Player whitePlayer){
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.id = idCount;
        idCount++;
        this.currentState = 0;
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

    /**
     * updates the boardState on display as the index used to track the boardStates is incremented
     * @param i value that indicates whether or not we increment forwards or backwards throughout the boardState arrayList
     */
    public void changeState(int i){
        currentState += i;
    }


    /**
     * Once the user exits their replay, currentState returns back to zero so that when the user returns to a replay they
     * start off the first boardState
     */
    public void resetState(){
        currentState = 0;
    }

    /**
     * gets the currentState index value
     * @return currentState
     */
    public int getCurrentState(){
        return currentState;
    }


    /**
     * sets the end date, called once the game has been finalized
     * @param date date game ended
     */
    public void setEndDate(Date date){
        this.endDate = date;
    }

    /**
     * gets the end date the game was finished
     * @return string type of end date (for replay.ftl)
     */
    public String getEndDate(){
        return endDate.toString();
    }


}
