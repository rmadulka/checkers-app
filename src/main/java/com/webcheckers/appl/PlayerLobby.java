package com.webcheckers.appl;
import com.webcheckers.model.Player;
import java.util.HashSet;

public class PlayerLobby {

    HashSet<Player> players;

    public PlayerLobby(){
        this.players = new HashSet<>();
    }

    public void addPlayer(Player player){
        players.add(player);
    }
}
