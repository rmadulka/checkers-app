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

public class BackupMoveRoute implements Route{

    private final PlayerLobby playerLobby;

    public BackupMoveRoute(PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
    }

    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        Player player = httpSession.attribute("currentUser");
        GameLobby gameLobby = playerLobby.getGameLobby(player);
        Message message;
        Stack<Move> validatedMoves = player.getTurnStack();
        if(!validatedMoves.isEmpty()){
           Move lastMove = validatedMoves.pop();
           //TODO Specify what type of move has been undone
           message = Message.info("A ____ move has been undone");
        }else{
            message = Message.error("No moves have been made to undo");
        }
        Gson gson = new Gson();
        String backupMessage = gson.toJson(message);
        return backupMessage;


        //check if the player has made a valid move for their turn
        //access a stack that stores all validated moves for the player's turn, .pop() the stack


    }

}
