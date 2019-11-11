package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayLobby;
import com.webcheckers.model.*;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetReplayGameRoute implements Route{

    static final String GAMES = "games";
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
        String gameId = request.queryParams("id");
        int gameIdInt = Integer.parseInt(gameId);
        ReplayLobby replayLobby = playerLobby.getReplayLobby();
        Game game = replayLobby.getGame(gameIdInt);
        ArrayList<BoardState> boardState = game.getBoardStates();
        BoardView boardView = new BoardView(player, new Board(game.getWhitePlayer(), game.getRedPlayer()));
        System.out.println(boardState.get(0).getActiveColor());
        vm.put(VIEW_MODE, GetGameRoute.viewMode.REPLAY);
        vm.put(GetHomeRoute.CURRENT_USER, player);
        vm.put(GetHomeRoute.HOME_TITLE, "Checkers Game");
        vm.put(GetGameRoute.RED_PLAYER, game.getRedPlayer());
        vm.put(GetGameRoute.WHITE_PLAYER, game.getWhitePlayer());
        vm.put(GetGameRoute.ACTIVE_COLOR, boardState.get(game.getCurrentState()).getActiveColor());
        vm.put(BOARD_VIEW, boardView);

        final Map<String, Object> modeOptions = new HashMap<>(2);
        modeOptions.put("hasNext", true);
        modeOptions.put("hasPrevious", true);
        vm.put("modeOptionsAsJSON", new Gson().toJson(modeOptions));


        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));


    }
}
