package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostExitGameRoute implements Route {

    private final PlayerLobby playerLobby;

    public PostExitGameRoute(PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
    }

    public Object handle(Request request, Response response){
        Session httpSession = request.session();
        Player player = httpSession.attribute("currentUser");
        GameLobby gameLobby = playerLobby.getGameLobby(player);

        gameLobby.removePlayer(player);

        if (gameLobby.playersEmpty()){
            playerLobby.removeGameLobby(gameLobby);
        }

        Message message = Message.info("Exiting Game");

        return new Gson().toJson(message);
    }

}
