package com.webcheckers.appl;
import com.webcheckers.model.Player;
import java.util.HashSet;

/**
 * Holds the list of players that successfully signed into the game
 * There should only be one instance of player lobby
 */
public class PlayerLobby {

    /** List of signed-in players */
    private HashSet<Player> players;

    /**
     * Creates a new instance of a PlayerLobby
     */
    public PlayerLobby(){
        this.players = new HashSet<>();
    }

    /**
     * Adds a player to the list of signed-in players
     * @param player A successfully signed-in player
     */
    public void addPlayer(Player player){
        players.add(player);
    }

    /**
     * Checks if a player already exists when signing in
     * @param player A new player attempting to sign-in
     * @return True if the player already exists
     */
    public boolean checkUsername (Player player){
        return players.contains(player);
    }
}
