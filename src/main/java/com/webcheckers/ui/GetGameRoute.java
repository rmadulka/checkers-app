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

        final String receiverName = request.queryParams("receiver");
        final Session httpSession = request.session();

        Board checkersBoard;

        Player sender = httpSession.attribute("currentUser");
        Player receiver;

//        System.out.println(sender.getName());
//        System.out.println(receiver.getName());

        Map<String, Object> vm = new HashMap<>();

        if(!sender.getInGame()) {
            receiver = playerLobby.getPlayer(receiverName);

            checkersBoard = new Board(receiver, sender);

            playerLobby.startGame(sender, receiver, checkersBoard);
            //TODO Maybe combine two lines

            vm.put("currentUser", sender);
            vm.put("redPlayer", sender);
            vm.put("whitePlayer", receiver);

        } else {
            receiver = sender.getOpponent();

            checkersBoard = sender.getCurrentBoard();

            vm.put("currentUser", sender);
            vm.put("redPlayer", receiver);
            vm.put("whitePlayer", sender);
        }

        LOG.finer("GetGameRoute is invoked.");

        vm.put("title", "Checkers Game");

        //TODO Right now active color is hardcoded
        vm.put("activeColor", Piece.pieceColor.RED);

        vm.put("viewMode", "PLAY");

        vm.put("board", checkersBoard);
//        vm.put("modeOptions", );

        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
