package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class PostSignoutRoute implements Route {

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;
    static final String CURRENTLY_IN_GAME = "Cannot sign-out mid game";

    public PostSignoutRoute (final TemplateEngine templateEngine, final PlayerLobby playerLobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) {
        final String currentUsername = request.queryParams("currentUser");
        final Session httpSession = request.session();
        final Map<String, Object> vm = new HashMap<>();
        Player currentPlayer = playerLobby.getPlayer(currentUsername);
        if (!currentPlayer.getInGame()) {
            playerLobby.removePlayer(currentPlayer);
            httpSession.removeAttribute(GetHomeRoute.CURRENT_USER);

            response.redirect("/");
            halt();
            return null;
        }
        /*ModelAndView mv;
        mv = error(vm, CURRENTLY_IN_GAME);
        return templateEngine.render(mv);*/
        httpSession.attribute(CURRENTLY_IN_GAME);
        response.redirect("/game");
        halt();
        return null;
    }
}
