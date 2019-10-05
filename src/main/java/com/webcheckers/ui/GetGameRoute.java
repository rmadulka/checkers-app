package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.GameLobby;
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

        Player player = httpSession.attribute("currentUser");
        Player opponent;

        Map<String, Object> vm = new HashMap<>();

        if(!player.getInGame()) {
            opponent = playerLobby.getPlayer(receiverName);

            checkersBoard = playerLobby.startGame(player, opponent);

            vm.put("currentUser", player);
            vm.put("redPlayer", player);
            vm.put("whitePlayer", opponent);

        } else {
            GameLobby gameLobby = playerLobby.getGameLobby(player);

            assert gameLobby != null : "GameLobby is null"; //Should never happen

            opponent = gameLobby.getOpponent(player);
            checkersBoard = gameLobby.getBoard();

            vm.put("currentUser", player);
            vm.put("redPlayer", opponent);
            vm.put("whitePlayer", player);
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
