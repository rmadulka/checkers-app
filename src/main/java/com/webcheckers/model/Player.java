package com.webcheckers.model;

/**
 * A model for a player
 * Each player has a unique identifier (Username)
 */

public class Player {

    /** The unique identifier for a player */
    private String username;

    /**
     * Creates a new instance of a player
     * @param username The unique identifier for player
     */
    public Player (String username){
        this.username = username;
    }

    /**
     * Gets the player's username
     * @return the username
     */
    public String getUsername(){
        return username;
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
        return this.username.equals(that.username) ;
    }

    /**
     * The player's hashcode
     * @return This player's hashcode
     */
    @Override
    public int hashCode(){
        return this.hashCode();
    }
}
