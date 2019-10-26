package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.MoveProcessor;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostResignRoute implements Route {

    private final PlayerLobby playerLobby;

    public PostResignRoute(PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
    }

    public Object handle(Request request, Response response){
        Session httpSession = request.session();
        Player player = httpSession.attribute("currentUser");
        GameLobby gameLobby = playerLobby.getGameLobby(player);
        //use gameLobby to end the game

        Message message;


        //TODO Set Winner/Loser in board and end game processes
        if(true){
            message = Message.info("valid");
        } else {
            //if the game failed to end
            message = Message.error("invlaid");
        }

        Gson gson = new Gson();
        String resign = gson.toJson(message);
        return resign;
    }
}