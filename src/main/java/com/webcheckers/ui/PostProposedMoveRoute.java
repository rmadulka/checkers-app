package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.MoveProcessor;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostProposedMoveRoute implements Route {

    private final PlayerLobby playerLobby;

    public PostProposedMoveRoute(PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
    }


    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        Player player = httpSession.attribute("currentUser");

        GameLobby gameLobby = playerLobby.getGameLobby(player);
        Board board = gameLobby.getBoard();

        String data = request.queryParams("actionData");
        Gson gson = new Gson();
        Move move = gson.fromJson(data, Move.class);

        Message message;


        if(MoveProcessor.validateSimpleMove(move, board)){
            //TODO law of demeter
            player.getTurnStack().push(move);
            message = Message.info("valid");
        } else {
            //TODO more than one error message
            message = Message.error("invlaid");
        }

        String proposedMove = gson.toJson(message);
        return proposedMove;
    }
}