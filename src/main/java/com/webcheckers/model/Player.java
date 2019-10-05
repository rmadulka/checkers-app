package com.webcheckers.model;

/**
 * A model for a player
 * Each player has a unique identifier (Username)
 */

public class Player {

    /** The unique identifier for a player */
    private String name;

    /** Determines if the player is supposed to be in a game */
    private boolean inGame;

    /** The opponent the player is playing against */
    private Player opponent;

    /** The board that both this player and the opponent see */
    private Board currentBoard;

    /**
     * Creates a new instance of a player
     * @param name The unique identifier for player
     */
    public Player (String name){
        this.name = name;
        this.inGame = false;
    }

    /**
     * Gets the player's username
     * @return the username
     */
    public String getName(){
        return name;
    }

    /**
     * Return the game status of the player
     * @return The player game status
     */
    public boolean getInGame() {
        return inGame;
    }

    /**
     * Sets the game status of the player
     * @param inGame Boolean that determines if a player is in a game
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    /**
     * Sets the current board
     * @param currentBoard The current board that both the opponent and this player see
     */
    public void setCurrentBoard(Board currentBoard){
        this.currentBoard = currentBoard;
    }

    /**
     * Sets the current opponent
     * @param opponent The opponent
     */
    public void setOpponent(Player opponent){
        this.opponent = opponent;
    }

    /**
     * Removes the opponent at the end of the game
     */
    public void removeOpponent(){
        this.opponent = null;
    }

    /**
     * Gets the current opponent that this player is facing
     * @return The opponent
     */
    public Player getOpponent(){
        return this.opponent;
    }

    /**
     * Gets the current board
     * @return The current board
     */
    public Board getCurrentBoard(){
        return currentBoard;
    }

    /**
     * Overrides the equals method to compare to player objects
     * @param obj Another player object
     * @return True if two players are the same
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(!(obj instanceof Player)){
            return false;
        }
        final Player that = (Player) obj;
        return this.name.equals(that.name) ;
    }

    /**
     * The player's hashcode
     * @return This player's hashcode
     */
    @Override
    public int hashCode(){
        return name.hashCode();
    }
}
