package com.webcheckers.appl;

import com.webcheckers.model.Game;

import java.util.ArrayList;


public class ReplayLobby {

    /** Stores all of the completed games **/
    ArrayList<Game> games;

    /**
     * Maintains all of the completed Games that are available to be replayed on the Replay page
     */
    public ReplayLobby() {

    }

    /**
     * Gets the games that have been completed
     * @return
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
}
