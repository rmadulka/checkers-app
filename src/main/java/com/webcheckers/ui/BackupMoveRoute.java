package com.webcheckers.ui;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.MoveValidation;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
public class BackupMove {

    private final PlayerLobby playerLobby;

    public BackupMoveRoute(PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
    }

    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        Player player = httpSession.attribute("currentUser");
        GameLobby gameLobby = playerLobby.getGameLobby(player);
        Board board = gameLobby.getBoard();
        if(!board.getValidatedMoves().isEmpty()){

        }
        //check if the player has made a valid move for their turn
        //access a stack that stores all validated moves, .pop() the stack
        //


    }

}
