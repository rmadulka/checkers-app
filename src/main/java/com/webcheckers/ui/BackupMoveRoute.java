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
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.Stack;

public class BackupMoveRoute {

    private final PlayerLobby playerLobby;

    public BackupMoveRoute(PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
    }

    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        Player player = httpSession.attribute("currentUser");
        GameLobby gameLobby = playerLobby.getGameLobby(player);

        Stack<Move> validatedMoves = player.getTurnStack();
        if(!validatedMoves.isEmpty()){
           Move lastMove = validatedMoves.pop();
           //TODO Specify what type of move has been undone
           return Message.info("A ____ move has been undone");
        }
        return Message.error("No moves have been made to undo");


        //check if the player has made a valid move for their turn
        //access a stack that stores all validated moves, .pop() the stack


    }

}
