package com.webcheckers.ui;

import com.google.gson.Gson;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class CheckTurnRoute implements Route{

    public final PlayerLobby playerLobby;

    /**
     * intended to check whether or not it is a user's turn
     * @param playerLobby: tracks all signed-in user's data
     */
    public CheckTurnRoute(PlayerLobby playerLobby){
            this.playerLobby = playerLobby;
    }

    public Object handle(Request request, Response response){
        Session httpSession = request.session();
        Player player = httpSession.attribute("currentUser");
        GameLobby gameLobby = playerLobby.getGameLobby(player);
        Message message;
        //if the current player controls the active piece it is their turn
        if((gameLobby.getRedPlayer() == player && currentPiece == red || gameLobby.getWhitePlayer() == player && currentPiece == white)){
            message = Message.info("true");
        }else{
            message = Message.info("false");
        }
        Gson gson = new Gson();
        return gson.toJson(message);

    }
}
