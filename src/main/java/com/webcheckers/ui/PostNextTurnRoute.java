package com.webcheckers.ui;
import com.google.gson.Gson;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayLobby;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.ArrayList;

public class PostNextTurnRoute implements Route {

    /** A playerlobby */
    private PlayerLobby playerLobby;

    /**
     * Creates a new instance of PostNextTurnRoute
     * @param playerLobby A playerlobby
     */
    public PostNextTurnRoute(PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
    }

    /**
     * When in replay mode, when the next button is hit, the next board state will appear
     * @param request an http request
     * @param response an http response
     * @return A gson message to update the page
     */
    public Object handle(Request request, Response response){
        String gameId = request.queryParams("gameID");
        int gameIdInt = Integer.parseInt(gameId);
        ReplayLobby replayLobby = playerLobby.getReplayLobby();
        Game game = replayLobby.getGame(gameIdInt);
        ArrayList<BoardState> boardStates = game.getBoardStates();
        Message message;
        if(game.getCurrentState() + 1 < boardStates.size()){
            game.changeState(1);
            message = Message.info("true");
        }else{
            message = Message.info("false");
        }
        Gson gson = new Gson();
        return gson.toJson(message);
    }
}
