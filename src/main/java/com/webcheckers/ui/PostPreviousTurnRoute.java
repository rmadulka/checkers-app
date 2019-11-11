package com.webcheckers.ui;
import com.google.gson.Gson;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayLobby;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.ArrayList;

public class PostPreviousTurnRoute implements Route{

    private PlayerLobby playerLobby;

    public PostPreviousTurnRoute(PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
    }

    public Object handle(Request request, Response response){
        Session httpSession = request.session();
        Message message;
        ReplayLobby replayLobby = playerLobby.getReplayLobby();
        //TODO implement gameId so that the game currently being replayed can be retrieved within the game arrayList
        Game game = replayLobby.getGames().get(0); //hard coded for now
        ArrayList<BoardState> boardStates = game.getBoardStates();
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
