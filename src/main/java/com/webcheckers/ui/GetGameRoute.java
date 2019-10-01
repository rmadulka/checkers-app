package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;

import spark.*;

public class GetGameRoute implements Route {

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    public GetGameRoute(final TemplateEngine templateEngine, final PlayerLobby playerLobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = playerLobby;
        LOG.config("GetGameRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {

        final Session httpSession = request.session();

        final String receiverName = request.queryParams("receiver");


        Player sender = httpSession.attribute("currentUser");
        Player receiver = playerLobby.getPlayer(receiverName);

        Board checkersBoard = new Board(receiver, sender);

        LOG.finer("GetGameRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Checkers Game");

        vm.put("currentUser", sender);
        vm.put("redPlayer", sender);
        vm.put("whitePlayer", receiver);

        vm.put("activeColor", Piece.pieceColor.RED);

        vm.put("viewMode", "PLAY");

        vm.put("board", checkersBoard);
//        vm.put("modeOptions", );

        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
