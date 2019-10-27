package com.webcheckers.appl;

import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;

public class GameLobby {
    /** represents the player who moves the red checker pieces **/
    public Player redPlayer;
    /** represents the player who moves the white checker pieces **/
    public Player whitePlayer;
    /** represents the checkerboard model**/
    private Board board;

    private boolean isGameOver;

    private Message gameOverMessage = null;

    /**
     * Constructs a GameLobby to keep track of the players in a current game
     * @param redPlayer
     *      player who controls the red pieces
     * @param whitePlayer
     *      player who controls the white pieces
     */
    public GameLobby(Player redPlayer, Player whitePlayer){
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new Board(whitePlayer, redPlayer);
        this.isGameOver = false;
        init();
    }

    /**
     * Sets the statuses of both players to be in game
     */
    public void init(){
        this.redPlayer.setInGame(true);
        this.whitePlayer.setInGame(true);
    }

    public void endGame(Message reason){
        isGameOver = true;
        gameOverMessage = reason;

        //TODO Shouldnt happen until player hits exit
    }
    /**
     * Gets the current board
     * @return the board's layout
     */
    public Board getBoard(){
        return this.board;
    }

    /**
     * Finds the opponent based on the player
     * @param player The player we use to find the opponent to this player
     * @return The opponent
     */
    public Player getOpponent(Player player) {
        if(player.equals(redPlayer)){
            return whitePlayer;
        } else if (player.equals(whitePlayer)) {
            return redPlayer;
        }
        return null;
    }

    public boolean getIsGameOver(){
        return isGameOver;
    }

    public Message getGameOverMessage(){
        return gameOverMessage;
    }

    public Player getRedPlayer(){
        return redPlayer;
    }

    public Player getWhitePlayer(){
        return whitePlayer;
    }

    public void removePlayer(Player player){
        if (player.equals(redPlayer)) {
            redPlayer.setInGame(false);
            redPlayer = null;
        } else if (player.equals(whitePlayer)) {
            whitePlayer.setInGame(false);
            whitePlayer = null;
        }
    }

    public boolean playersEmpty(){
        return redPlayer == null && whitePlayer == null;
    }
}
