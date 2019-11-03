package com.webcheckers.ui;

import com.google.gson.Gson;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostCheckTurnRoute implements Route{

    /** represents the players online, able to manage users **/
    public final PlayerLobby playerLobby;

    /**
     * intended to determine whether or not it is a user's turn
     * @param playerLobby: tracks all signed-in user's data
     */
    public PostCheckTurnRoute(PlayerLobby playerLobby){
            this.playerLobby = playerLobby;
    }

    /**
     * outputs messages determining whether or not it is currently a user's turn
     * @param request A http request
     * @param response A http response
     * @return true/false message
     */
    public Object handle(Request request, Response response){
        Session httpSession = request.session();
        Player player = httpSession.attribute("currentUser");
        GameLobby gameLobby = playerLobby.getGameLobby(player);
        Board board = gameLobby.getBoard();
        Message message;
        //TODO check when user resigns game
        //if the current player controls the active piece it is their turn
        if((gameLobby.getRedPlayer() == player && board.getActiveColor() == Piece.pieceColor.RED) ||
                (gameLobby.getWhitePlayer() == player && board.getActiveColor() == Piece.pieceColor.WHITE)){
            message = Message.info("true");
        }else{
            message = Message.info("false");
        }

        Gson gson = new Gson();
        return gson.toJson(message);

    }
}
