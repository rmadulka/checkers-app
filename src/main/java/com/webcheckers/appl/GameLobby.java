package com.webcheckers.appl;

import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.util.Message;

import java.util.logging.Logger;

public class GameLobby {
    private static final Logger LOG = Logger.getLogger(GameLobby.class.getName());
    /** represents the player who moves the red checker pieces **/
    public Player redPlayer;
    /** represents the player who moves the white checker pieces **/
    public Player whitePlayer;

    private boolean redPlayerPresent;

    private boolean whitePlayerPresent;
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
        LOG.config("GameLobby created for [" + redPlayer.getName() + "] [" + whitePlayer.getName() + "]" );
    }

    /**
     * Sets the statuses of both players to be in game
     */
    public void init(){
        this.redPlayer.setInGame(true);
        this.whitePlayer.setInGame(true);
        this.redPlayerPresent = true;
        this.whitePlayerPresent = true;
    }

    //TODO Fix to have multiple reasons along with winner/Loser
    //TODO perhaps an enum with RESIGN, NO MOVES, NO PIECES
    public void endGame(Message reason){
        LOG.finer("Game over");
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

    public boolean getIsGameOver(){
        return isGameOver;
    }

    public String getGameOverMessageAsString(){
        if (gameOverMessage == null) {
            return null;
        }
        return gameOverMessage.getText();
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
            redPlayerPresent = false;
            LOG.finer("redPlayer removed from GameLobby");
        } else if (player.equals(whitePlayer)) {
            whitePlayer.setInGame(false);
            whitePlayerPresent = false;
            LOG.finer("whitePlayer removed from GameLobby");
        }
    }

    public boolean playersEmpty(){
        return !(redPlayerPresent || whitePlayerPresent);
    }
}
