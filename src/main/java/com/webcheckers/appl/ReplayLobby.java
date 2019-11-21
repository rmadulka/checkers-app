package com.webcheckers.appl;

import com.webcheckers.model.Game;

import java.util.ArrayList;


public class ReplayLobby {

    /** Stores all of the completed games **/
    private ArrayList<Game> games;

    /**
     * Maintains all of the completed Games that are available to be replayed on the Replay page
     */
    public ReplayLobby() {
        this.games = new ArrayList<>();
    }

    /**
     * Gets the games that have been completed
     * @return The games
     */
    public ArrayList<Game> getGames(){
        return games;
    }

    /**
     * Adds a completed game to the games array
     * @param game a completed game ready to be replayed
     */
    public void addGame(Game game){
        games.add(game);
    }

    /**
     * Gets the game based on the game id
     * @param id The game id
     * @return The game
     */
    public Game getGame(int id) {
        for (Game game : games) {
            if(game.getId() == id){
                return game;
            }
        }
        return null;
    }
}
