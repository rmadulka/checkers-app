package com.webcheckers.appl;
import com.webcheckers.model.Player;
import java.util.HashSet;

/**
 * Holds the list of players that successfully signed into the game
 * There should only be one instance of player lobby
 */
public class PlayerLobby {

    public enum signinErrors {VALID, NAMEEXISTS, ALPHA}

    /** List of signed-in players */
    private HashSet<Player> players;

    /**
     * Creates a new instance of a PlayerLobby
     */
    public PlayerLobby(){
        this.players = new HashSet<>();
    }

    public HashSet<Player> getPlayersUsername(){

    }

    /**
     * Adds a player to the list of signed-in players
     * @param player A successfully signed-in player
     */
    public void addPlayer(Player player){
        this.players.add(player);
    }

    /**
     * Checks if a player already exists when signing in
     * @param username A new player attempting to sign-in
     * @return True if the player already exists
     */
    public signinErrors checkUsername (String username){
        Player tempPlayer = new Player(username);
        if (this.players.contains(tempPlayer)){
            return signinErrors.NAMEEXISTS;
        } else if (!(username.matches("[a-zA-Z0-9 ]*[a-zA-Z0-9]+[a-zA-Z0-9 ]*"))){
            return signinErrors.ALPHA;
        } else {
            return signinErrors.VALID;
        }
    }
}
