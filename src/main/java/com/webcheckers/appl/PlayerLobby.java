package com.webcheckers.appl;
import com.webcheckers.model.Player;
import java.util.HashSet;

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
}
