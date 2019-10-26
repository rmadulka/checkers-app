package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;


import static spark.Spark.halt;

public class PostSignoutRoute implements Route {

    /** A PlayerLobby object */
    private final PlayerLobby playerLobby;
    /** Message that appears when a player signs out mid game */
    static final Message IN_GAME_MSG = Message.error("Cannot sign-out mid game");

    /**
     * Creates a new instance of PostSignoutRoute
     * @param playerLobby A PlayerLobby object
     */
    public PostSignoutRoute (final PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
    }

    /**
     * Redirects the user to the home page when they signout and removes them from the list of sign-in users
     * @param request A http request
     * @param response A http response
     * @return null
     */
    @Override
    public Object handle(Request request, Response response) {
        final String currentUsername = request.queryParams("currentUser");
        final Session httpSession = request.session();
        Player currentPlayer = playerLobby.getPlayer(currentUsername);
        if (!currentPlayer.getInGame()) {
            playerLobby.removePlayer(currentPlayer);
            httpSession.removeAttribute(GetHomeRoute.CURRENT_USER);

            response.redirect("/");
            halt();
            return null;
        }
        httpSession.attribute(GetHomeRoute.MESSAGE_ATR, IN_GAME_MSG);
        response.redirect("/game");
        halt();
        return null;
    }
}
