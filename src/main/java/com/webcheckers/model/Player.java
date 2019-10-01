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

    private Player opponent;

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

    public void setCurrentBoard(Board currentBoard){
        this.currentBoard = currentBoard;
    }

    public void setOpponent(Player opponent){
        this.opponent = opponent;
    }

    public void removeOpponent(){
        this.opponent = null;
    }

    public Player getOpponent(){
        return this.opponent;
    }

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
