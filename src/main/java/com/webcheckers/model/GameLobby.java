package com.webcheckers.model;

public class GameLobby {
    /** represents the player who moves the red checker pieces **/
    private Player redPlayer;
    /** represents the player who moves the white checker pieces **/
    private Player whitePlayer;
    /** represents the checkerboard model**/
    private Board board;

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
        init();
    }

    /**
     * Sets the statuses of both players to be in game
     */
    public void init(){
        this.redPlayer.setInGame(true);
        this.whitePlayer.setInGame(true);

        /*this.redPlayer.setOpponent(redPlayer);
        this.whitePlayer.setOpponent(whitePlayer);*/

        /*this.redPlayer.setCurrentBoard(board); Most likely not needed
        this.whitePlayer.setCurrentBoard(board);*/
    }

    /**
     * Gets the current board
     * @return the board's layout
     */
    public Board getBoard(){
        return this.board;
    }

    public Player getOpponent(Player player) {
        if(player.equals(redPlayer)){
            return whitePlayer;
        } else if (player.equals(whitePlayer)) {
            return redPlayer;
        }
        return null;
    }
}
