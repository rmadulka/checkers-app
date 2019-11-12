package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostExitGameRoute implements Route {

    /** represents the players online, able to manage users **/
    private final PlayerLobby playerLobby;

    private ReplayLobby replayLobby;

    /**
     * Used to give functionality to the "Exit" button
     * @param playerLobby
     */
    public PostExitGameRoute(PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
    }

    /**
     * Used to fully end the gameLobby the game is being held in and informing the user that the game is exiting
     * @param request the HTTP request
     * @param response the HTTP response
     * @return exiting message
     */
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
