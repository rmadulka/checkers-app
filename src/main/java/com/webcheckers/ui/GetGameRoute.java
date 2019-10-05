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

    static final String RED_PLAYER = "redPlayer";

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

        Player player = httpSession.attribute(GetHomeRoute.CURRENT_USER);
        Player opponent;

        Map<String, Object> vm = new HashMap<>();

        System.out.println(player);

        if(!player.getInGame()) {
            opponent = playerLobby.getPlayer(receiverName);

            GameLobby gameLobby = playerLobby.startGame(player, opponent);

            checkersBoard = gameLobby.getBoard();

            vm.put("redPlayer", player);
            vm.put("whitePlayer", opponent);

        } else {
            GameLobby gameLobby = playerLobby.getGameLobby(player);

            assert gameLobby != null : "GameLobby is null"; //Should never happen

            opponent = gameLobby.getOpponent(player);
            checkersBoard = gameLobby.getBoard();

            vm.put("redPlayer", opponent);
            vm.put("whitePlayer", player);
        }

        BoardView boardView = new BoardView(player, checkersBoard);

        LOG.finer("GetGameRoute is invoked.");

        vm.put("currentUser", player);

        vm.put("title", "Checkers Game");

//        for(int row = 0; row < 8;  row++) {
//            for(int col = 0; col <8; col++) {
//                System.out.print(boardView.rows.get(row).spaceSequence.get(col));
//            }
//            System.out.println("");
//        }

        vm.put("board", boardView);

        //TODO Right now active color is hardcoded
        vm.put("activeColor", Piece.pieceColor.RED);

        vm.put("viewMode", "PLAY");
//        vm.put("modeOptions", );

        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
