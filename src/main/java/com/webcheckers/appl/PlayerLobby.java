package com.webcheckers.appl;
import com.webcheckers.model.Board;
import com.webcheckers.model.GameLobby;
import com.webcheckers.model.Player;
import java.util.HashSet;

/**
 * Holds the list of players that successfully signed into the game
 * There should only be one instance of player lobby
 */
public class PlayerLobby {

    /**
     * Elements that show the validity of a username
     */
    public enum signinErrors {VALID, NAMEEXISTS, ALPHA}

    /** List of signed-in players */
    private HashSet<Player> players;

    /**
     * Creates a new instance of a PlayerLobby
     */
    public PlayerLobby(){
        this.players = new HashSet<>();
    }

    /**
     * Gets the list of signed-in players
     * @return The list of players
     */
    public HashSet<Player> getPlayers(){
        return this.players;
    }

    /**
     * Gets a player based on the players name
     * @param name Players name
     * @return The player if it exists in the hash set
     */
    public Player getPlayer(String name) {
        for (Player i : players){
            if(i.getName().equals(name)){
                return i;
            }
        }
        return null;
    }

    /**
     * Gets the number of players currently signed-in
     * @return The number of players
     */
    public int getNumPlayers (){
        return this.players.size();
    }

    /**
     * Sets the statuses of both players to be in game
     * @param redPlayer The red player
     * @param whitePlayer The white player
     * @return The board both players are playing on
     */
    public Board startGame(Player redPlayer, Player whitePlayer){
        GameLobby gameLobby = new GameLobby(redPlayer, whitePlayer);
        return gameLobby.getBoard();
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
     * @param name A new player attempting to sign-in
     * @return True if the player already exists
     */
    public signinErrors checkUsername (String name){
        Player tempPlayer = new Player(name);
        if (this.players.contains(tempPlayer)){
            return signinErrors.NAMEEXISTS;
        } else if (!(name.matches("[a-zA-Z0-9 ]*[a-zA-Z0-9]+[a-zA-Z0-9 ]*"))){
            return signinErrors.ALPHA;
        } else {
            return signinErrors.VALID;
        }
    }
}
