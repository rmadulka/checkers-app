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
        vm.put(BOARD_VIEW, boardView);
        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));


    }
}
