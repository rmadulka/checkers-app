package com.webcheckers.ui;


import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import java.util.Stack;

public class BackupMoveRoute implements Route{

    /** represents the players online, able to manage users **/
    private final PlayerLobby playerLobby;

    /**
     * Intended to add functionality to the Backup button by undoing the most recent turn made by the user
     * @param playerLobby: given playerLobby object
     */
    public BackupMoveRoute(PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
    }

    /**
     * Intended to post a message informing the user whether their backup move undid a specific move or if there has been
     * no move made
     * @param request
     * @param response
     * @return message informing the user of the backup status
     */
    public Object handle(Request request, Response response) {
        Message message = Message.error("No moves have been made to undo");
        Session httpSession = request.session();
        Player player = httpSession.attribute("currentUser");
        Stack<Move> validatedMoves = player.getTurnStack();
        if(!validatedMoves.isEmpty()){
            Move lastMove = validatedMoves.pop();
            if(lastMove.isJumpMove()){
                message = Message.info("A Jump Move has been undone");
            }
            else if(!lastMove.isJumpMove()){
                message = Message.info("A Simple Move has been undone");
            }
        }
        Gson gson = new Gson();
        return gson.toJson(message);


        //check if the player has made a valid move for their turn
        //access a stack that stores all validated moves for the player's turn, .pop() the stack


    }

}
