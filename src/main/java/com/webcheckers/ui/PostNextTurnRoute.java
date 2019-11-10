package com.webcheckers.ui;
import com.google.gson.Gson;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.Space;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import java.util.ArrayList;

public class PostNextTurnRoute implements Route {

    private PlayerLobby playerLobby;

    public PostNextTurnRoute(PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
    }

    //TODO find a way to specify which game is being reviewed
    public Object handle(Request request, Response response){
        Session httpSession = request.session();
        Player player = httpSession.attribute("currentUser");
        ReplayLobby replayLobby = playerLobby.getReplayLobby();
        Game game = replayLobby.getGames().get(0); //hard coded for now
        //TODO review the boardStates and write conditions on if we can go backwards or forwards w/out OOB error
        //(SAME APPLIES TO POSTPREVIOUSTURNROUTE)
        return null;
    }
}
