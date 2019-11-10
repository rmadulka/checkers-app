package com.webcheckers.appl;
import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

/**
 * Holds the list of players that successfully signed into the game
 * There should only be one instance of player lobby
 */
public class PlayerLobby {
    private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());
    /**
     * Elements that show the validity of a username
     */
    public enum signinErrors {VALID, NAMEEXISTS, ALPHA}

    /** List of signed-in players */
    private HashSet<Player> players;

    /** An array list of GameLobbies */
    private ArrayList<GameLobby> currentGames;

    /**
     * Creates a new instance of a PlayerLobby
     */
    public PlayerLobby(){
        this.players = new HashSet<>();
        this.currentGames = new ArrayList<>();
        LOG.config("PlayerLobby Initialized");
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
            if(i.getName() != null && i.getName().equals(name)){
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
    public GameLobby startGame(Player redPlayer, Player whitePlayer){
        if (players.contains(redPlayer) && players.contains(whitePlayer)) {
            GameLobby gameLobby = new GameLobby(redPlayer, whitePlayer);
            currentGames.add(gameLobby);
            LOG.finer("GameLobby added to PlayerLobby");
            return gameLobby;
        }
        return null;
    }

    /**
     * Gets GameLobby based on the player name
     * @param player A player
     * @return The GameLobby that the player is in
     */
    public GameLobby getGameLobby(Player player) {
        for (GameLobby games : currentGames){
            //Player opponent = games.getOpponent(other);
            if(games.getRedPlayer().equals(player) || games.getWhitePlayer().equals(player)) {
                return games;
            }
        }
        return null;
    }

    /**
     * Adds a player to the list of signed-in players
     * @param player A successfully signed-in player
     */
    public void addPlayer(Player player){
        this.players.add(player);
    }

    /**
     * Removes a player from the list of signed-in players
     * @param player A player that wants to sign-out
     */
    public void removePlayer(Player player) {
        this.players.remove(player);
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

    /**
     * Removes a GameLobby object from the list when a player resigns or a game ends
     * @param gameLobby The GameLobby in which a player has resigned or a game has ended
     */
    public void removeGameLobby(GameLobby gameLobby) {
        this.currentGames.remove(gameLobby);
        LOG.finer("Removed GameLobby from PlayerLobby");
    }
}