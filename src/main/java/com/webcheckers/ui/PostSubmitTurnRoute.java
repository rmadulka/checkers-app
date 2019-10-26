package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.MoveProcessor;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static spark.Spark.halt;

public class PostSubmitTurnRoute implements Route {

    private final PlayerLobby playerLobby;

    public PostSubmitTurnRoute(PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
    }

    public Object handle(Request request, Response response){
        Session httpSession = request.session();
        Player player = httpSession.attribute("currentUser");
        GameLobby gameLobby = playerLobby.getGameLobby(player);
        Board board = gameLobby.getBoard();

        Message message;

        //TODO Check validity - jump moves available
        if(true){
            MoveProcessor.processMoves(player, board);
            message = Message.info("valid");
            player.getTurnStack().removeAllElements();
        } else {
            //TODO more than one error message
            message = Message.error("invlaid");
        }

        Gson gson = new Gson();
        String submitMove = gson.toJson(message);
        return submitMove;
    }

}
