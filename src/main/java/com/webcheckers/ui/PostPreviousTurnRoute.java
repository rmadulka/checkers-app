package com.webcheckers.ui;
import com.google.gson.Gson;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayLobby;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;


public class PostPreviousTurnRoute implements Route{

    /** A playerLobby object */
    private PlayerLobby playerLobby;

    /**
     * Creates a new instance of PostPreviousTurnRoute
     * @param playerLobby A playerlobby
     */
    public PostPreviousTurnRoute(PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
    }

    /**
     * When in replay mode, when a player selects previous, the previous game state will appear
     * @param request An http request
     * @param response An http response
     * @return A gson message
     */
    public Object handle(Request request, Response response){
        String gameId = request.queryParams("gameID");
        int gameIdInt = Integer.parseInt(gameId);
        ReplayLobby replayLobby = playerLobby.getReplayLobby();
        Game game = replayLobby.getGame(gameIdInt);
        Message message;
        if(game.getCurrentState() - 1 >= 0){
            game.changeState(-1);
            message = Message.info("true");
        }else{
            message = Message.info("false");
        }
        Gson gson = new Gson();
        return gson.toJson(message);
    }
}
