package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import com.webcheckers.util.MoveProcessor;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostProposedMoveRoute implements Route {

    /** A player lobby */
    private final PlayerLobby playerLobby;

    /**
     * Creates an instance of PostProposedMoveRoute
     * @param playerLobby A playerLobby object
     */
    public PostProposedMoveRoute(PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
    }

    /**
     * Validates a players move if it is a valid move or not
     * Move gets added to the stack of moves if it is a valid move
     * @param request An http request
     * @param response An http response
     * @return The proposed move
     */
    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        Player player = httpSession.attribute("currentUser");
        GameLobby gameLobby = playerLobby.getGameLobby(player);
        Board board = gameLobby.getBoard();
        String data = request.queryParams("actionData");
        Gson gson = new Gson();
        Move move = gson.fromJson(data, Move.class);
        Message message;

        if(MoveProcessor.validateMove(move, board, player)){
            player.addMove(move);
            message = Message.info("Move is Valid");
        } else {
            //TODO more than one error message
            message = Message.error("Invalid Move");
        }

        String proposedMove = gson.toJson(message);
        return proposedMove;
    }
}