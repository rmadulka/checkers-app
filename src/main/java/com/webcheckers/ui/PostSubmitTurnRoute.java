package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.BoardState;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import com.webcheckers.util.MoveProcessor;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static spark.Spark.halt;

public class PostSubmitTurnRoute implements Route {

    /** represents the players online, able to manage users **/
    private final PlayerLobby playerLobby;

    /**
     * Intended to inform the user that their move(s) have been validated or invalidated
     * @param playerLobby
     */
    public PostSubmitTurnRoute(PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
    }

    /**
     * Calls validation methods in order to output the correct invalid or valid message
     * @param request the HTTP request
     * @param response the HTTP response
     * @return valid/invalid message
     */
    public Object handle(Request request, Response response){
        Session httpSession = request.session();
        Player player = httpSession.attribute("currentUser");
        GameLobby gameLobby = playerLobby.getGameLobby(player);
        Board board = gameLobby.getBoard();
        BoardState boardState = new BoardState(board.getActiveColor());
        Game game = gameLobby.getGame();
        ReplayLobby replayLobby = playerLobby.getReplayLobby();
        Message message;
        if(MoveProcessor.validateTurn(player.getTurnStack(), board) && !gameLobby.getIsGameOver()){
            MoveProcessor.processMoves(player, board);
            message = Message.info("Valid Turn");
            //EndGame Conditions
            if(board.checkNoAvailiablePieces()){
                replayLobby.addGame(game);
                gameLobby.endGame(Message.info(String.format("%s has no remaining pieces",board.getActiveColor().toString())));
            } else if(!board.checkAvailableMove()){
                replayLobby.addGame(game);
                gameLobby.endGame(Message.info(String.format("%s has no available moves",board.getActiveColor().toString())));
            }
            boardState.constructState(board);
            game.addGameState(boardState);
            player.getTurnStack().removeAllElements();


        } else {
            //TODO more than one error message
            message = Message.error("Invalid: There is a jump move available");
        }

        Gson gson = new Gson();
        return gson.toJson(message);
    }

}
