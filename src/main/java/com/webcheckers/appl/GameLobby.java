package com.webcheckers.appl;

import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import java.util.Date;
import java.util.logging.Logger;

public class GameLobby {
    private static final Logger LOG = Logger.getLogger(GameLobby.class.getName());
    /** represents the player who moves the red checker pieces **/
    public Player redPlayer;
    /** represents the player who moves the white checker pieces **/
    public Player whitePlayer;
    /** whether or not the red player is in the GameLobby */
    private boolean redPlayerPresent;
    /** whether or not the white player is in the GameLobby */
    private boolean whitePlayerPresent;
    /** represents the checkerboard model**/
    private Board board;
    /** determines if the game is over */
    private boolean isGameOver;
    /** game over message */
    private Message gameOverMessage = null;
    /** A game object */
    private Game game;


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
        this.game = new Game(redPlayer, whitePlayer);
        BoardState boardState = new BoardState(board.getActiveColor());
        boardState.constructState(board);
        game.addGameState(boardState);
        LOG.config("GameLobby created for [" + redPlayer.getName() + "] [" + whitePlayer.getName() + "]" );
    }

    /**
     * Sets the statuses of both players to be in game
     */
    public void init(){
        redPlayerPresent = this.redPlayer.addInGameStatus();
        whitePlayerPresent = this.whitePlayer.addInGameStatus();
    }

    /**
     * Sets the date in which the game ended
     * Sets the gameEnded to true to show that a game has ended
     * @param reason The message based on the reason the game ended
     */
    public void endGame(Message reason){
        LOG.finer("Game over");
        game.setEndDate(new Date());
        isGameOver = true;
        gameOverMessage = reason;
    }
    /**
     * Gets the current board
     * @return the board's layout
     */
    public Board getBoard(){
        return this.board;
    }

    /**
     * Gets the game
     * @return The game
     */
    public Game getGame(){
        return game;
    }

    /**
     * Sets the board to a new value (used for testing purposes)
     * @param board updated board layout
     */
    public void setBoard(Board board){
        this.board = board;
    }

    /**
     * Sets the IsGameOver status to a new value (used for testing purposes)
     * @param gameOver updated game status
     */
    public void setIsGameOver(boolean gameOver){
        this.isGameOver = gameOver;
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

    /**
     * Used to get a boolean value indicating the game status
     * @return isGameOver current value
     */
    public boolean getIsGameOver(){
        return isGameOver;
    }

    /**
     * Intended to return a string that describes the game over message once it is updated from its initial value of null
     * @return string of the gameOverMessage
     */
    public String getGameOverMessageAsString(){
        if (gameOverMessage == null) {
            return null;
        }
        return gameOverMessage.getText();
    }

    /**
     * Gets the player controlling the red pieces
     * @return redplayer Player object
     */
    public Player getRedPlayer(){
        return redPlayer;
    }

    /**
     * Gets the player controlling the white pieces
     * @return whitePlayer Player object
     */
    public Player getWhitePlayer(){
        return whitePlayer;
    }

    /**
     * Removes a specified player from the game, typically once a player has resigned or the game is over
     * @param player specified player to remove
     */
    public void removePlayer(Player player){

        if (player.equals(redPlayer)) {
            redPlayer.removeInGameStatus();
            redPlayerPresent = false;
            LOG.finer("redPlayer removed from GameLobby");
        } else if (player.equals(whitePlayer)) {
            whitePlayer.removeInGameStatus();
            whitePlayerPresent = false;
            LOG.finer("whitePlayer removed from GameLobby");
        }
    }

    /**
     * Checks if both players have left the game
     * @return boolean whether both players are present
     */
    public boolean playersEmpty(){
        return !(redPlayerPresent || whitePlayerPresent);
    }
}
