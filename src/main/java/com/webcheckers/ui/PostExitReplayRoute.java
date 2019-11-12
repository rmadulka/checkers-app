package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static spark.Spark.halt;

public class PostExitReplayRoute implements Route {
    /** represents the players online, able to manage users **/
    private final PlayerLobby playerLobby;

    /**
     * Used to let the user return back to the home screen when in on the replay page
     * @param playerLobby represents the players online, able to manage users
     */
    public PostExitReplayRoute(PlayerLobby playerLobby){
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
        String gameId = request.queryParams("id");
        int gameIdInt = Integer.parseInt(gameId);
        ReplayLobby replayLobby = playerLobby.getReplayLobby();
        Game game = replayLobby.getGame(gameIdInt);
        game.resetState();
        gameLobby.removePlayer(player);

        if (gameLobby.playersEmpty()){
            playerLobby.removeGameLobby(gameLobby);
        }
        Message message = Message.info("Exiting Game");
        return new Gson().toJson(message);
    }
}
