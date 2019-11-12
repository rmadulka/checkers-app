package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.BoardState;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.MoveProcessor;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostResignRoute implements Route {

    /** represents the players online, able to manage users **/
    private final PlayerLobby playerLobby;

    /**
     * Intended to inform the user of their opponent's resignation after they have hit "Resign"
     * @param playerLobby
     */
    public PostResignRoute(PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
    }

    /**
     * Used to inform the user that a user has resigned
     * @param request the HTTP request
     * @param response the HTTP response
     * @return a message informing the user that their opponent has resigned
     */
    public Object handle(Request request, Response response){
        Session httpSession = request.session();
        Player player = httpSession.attribute("currentUser");
        GameLobby gameLobby = playerLobby.getGameLobby(player);
        Game game = gameLobby.getGame();
        ReplayLobby replayLobby = playerLobby.getReplayLobby();
        Message message;

        //Will fail if game is over
        if(!gameLobby.getIsGameOver()){
            //TODO other means of game ending
            gameLobby.endGame(Message.info(String.format("%s has resigned",player.getName())));
            replayLobby.addGame(game);
            message = Message.info("Game has ended");
        } else {

            //if the game failed to end
            message = Message.error("Game has already ended");
        }

        Gson gson = new Gson();
        return gson.toJson(message);
    }
}
