package com.webcheckers.ui;

import com.google.gson.Gson;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.MoveProcessor;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;


public class CheckTurnRoute implements Route{

    public final PlayerLobby playerLobby;

    public CheckTurnRoute(PlayerLobby playerLobby){
            this.playerLobby = playerLobby;
    }

    public Object handle(Request request, Response response){
        Session httpSession = request.session();
        Player currentPlayer = httpSession.attribute("currentPlayer");
        Player whitePlayer = httpSession.attribute("whitePlayer");
        Player redPlayer = httpSession.attribute("redPlayer");
        String activePiece = httpSession.attribute("activeColor");
        GameLobby gameLobby = playerLobby.getGameLobby(currentPlayer);
        Message message;
        if((currentPlayer == redPlayer && activePiece == "RED") || (currentPlayer == whitePlayer && activePiece == "WHITE")){
            message = Message.info("it is " + currentPlayer.getName() + "'s turn");
        }else{
            message = Message.error("Not your turn");
        }
        Gson gson = new Gson();
        String backupMessage = gson.toJson(message);
        return backupMessage;

    }
}
