package com.webcheckers.model;

import java.util.Stack;

/**
 * A model for a player
 * Each player has a unique identifier (Username)
 */

public class Player {

    /** The unique identifier for a player */
    protected String name;

    /** Determines if the player is supposed to be in a game */
    protected boolean inGame;

    /** The stack of moves */
    protected Stack<Move> turnStack = null;

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

//    /**
//     * Sets the game status of the player
//     * @param inGame Boolean that determines if a player is in a game
//     */
//    public void setInGame(boolean inGame) {
//        this.turnStack = new Stack<>();
//        this.inGame = inGame;
//    }

    public boolean addInGameStatus(){
        this.turnStack = new Stack<>();
        this.inGame = true;
        return true;
    }

    public void removeInGameStatus(){
        this.inGame = false;
    }

    /**
     * Adds a move to the turn stack
     * @param move - Move to be added
     */
    public void addMove(Move move){
        turnStack.push(move);
    }

    /**
     * The stack of made moves
     * Allows for back up moves
     * @return The stack of moves
     */
    public Stack<Move> getTurnStack(){
        return turnStack;
    }

    /**
     * Sets the value to a new type of stack (used for testing)
     * @param stack new move stack
     */
    public void setTurnStack(Stack<Move> stack){
        turnStack = stack;
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
