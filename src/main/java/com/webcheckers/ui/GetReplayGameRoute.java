package com.webcheckers.ui;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import com.webcheckers.model.Space;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetReplayGameRoute implements Route{

    static final String VIEW_MODE = "viewMode";
    static final String VIEW_NAME = "game.ftl";
    static final String BOARD_VIEW = "board";
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    public GetReplayGameRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = playerLobby;
        //
        LOG.config("GetReplayMode is initialized.");
    }

    public Object handle(Request request, Response response){
        Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();
        Player player = httpSession.attribute("currentUser");
        GameLobby gameLobby = playerLobby.getGameLobby(player);
        ArrayList<Board> gameMoves = gameLobby.getGameMoves();
        BoardView boardView = new BoardView(player, gameMoves.get(0));
        vm.put(VIEW_MODE, GetGameRoute.viewMode.REPLAY);
        vm.put(GetHomeRoute.CURRENT_USER, player);
        vm.put(GetHomeRoute.HOME_TITLE, "Checkers Game");
        vm.put(GetGameRoute.RED_PLAYER, gameLobby.getRedPlayer());
        vm.put(GetGameRoute.WHITE_PLAYER, gameLobby.getWhitePlayer());
        vm.put(GetGameRoute.ACTIVE_COLOR, gameLobby.getBoard().getActiveColor());
        vm.put(BOARD_VIEW, boardView);
        System.out.println(vm.entrySet());
        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));


    }
}
