package com.webcheckers.ui;
import com.google.gson.Gson;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
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

    public Object handle(Request request, Response response){
        Session httpSession = request.session();
        Player player = httpSession.attribute("currentUser");
        GameLobby gameLobby = playerLobby.getGameLobby(player);
        ArrayList<Board> gameMoves = gameLobby.getGameMoves();
        return null;
    }
}
