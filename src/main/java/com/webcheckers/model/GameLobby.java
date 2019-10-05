package com.webcheckers.model;

public class GameLobby {

    private Player redPlayer;
    private Player whitePlayer;
    private Board board;

    public GameLobby(Player redPlayer, Player whitePlayer){
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new Board(redPlayer, whitePlayer);
        init();
    }

    /**
     * Sets the statuses of both players to be in game
     */
    public void init(){
        this.redPlayer.setInGame(true);
        this.whitePlayer.setInGame(true);

        this.redPlayer.setOpponent(redPlayer);
        this.whitePlayer.setOpponent(whitePlayer);

        /*this.redPlayer.setCurrentBoard(board); Most likely not needed
        this.whitePlayer.setCurrentBoard(board);*/
    }

    public Board getBoard(){
        return this.board;
    }
}
