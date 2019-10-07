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

import com.webcheckers.util.Message;
import spark.*;

import static spark.Spark.halt;

public class GetGameRoute implements Route {

    /**
     * Enum for the type of view
     */
    public enum viewMode {
        PLAY,
        SPECTATOR,
        AI
    }

    /** Names of the view model values */
    static final String RED_PLAYER = "redPlayer";
    static final String WHITE_PLAYER = "whitePlayer";
    static final String BOARD_VIEW = "board";
    static final String ACTIVE_COLOR = "activeColor";
    static final String VIEW_MODE = "viewMode";
    static final String GENERIC_MESSAGE = "message";
    static final String VIEW_NAME = "game.ftl";

    /** Error message when a player that is currently in a game is selected */
    static final Message IN_GAME_MSG = Message.error("Selected player currently in game");

    /** A template engine object */
    private final TemplateEngine templateEngine;
    /** A player lobby object */
    private final PlayerLobby playerLobby;
    /** A log message */
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    /**
     * Creates an instance of a GetGameRoute
     * @param templateEngine A template engine object
     * @param playerLobby A player lobby object (there is only one created)
     */
    public GetGameRoute(final TemplateEngine templateEngine, final PlayerLobby playerLobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = playerLobby;
        LOG.config("GetGameRoute is initialized.");
    }

    /**
     * Renders the game page
     * @param request An HTTP request
     * @param response An HTTP response
     * @return A rendered game page
     */
    @Override
    public Object handle(Request request, Response response) {

        final String receiverName = request.queryParams("receiver");
        final Session httpSession = request.session();

        Board checkersBoard;

        Player player = httpSession.attribute(GetHomeRoute.CURRENT_USER);
        Player opponent;

        Map<String, Object> vm = new HashMap<>();

        if(!player.getInGame()) {
            opponent = playerLobby.getPlayer(receiverName);
            if (opponent.getInGame()) {
               // vm.put(IN_GAME, IN_GAME_MSG);
                httpSession.attribute(GENERIC_MESSAGE, IN_GAME_MSG);
                response.redirect("/");
                halt();
                return null;
            }

            GameLobby gameLobby = playerLobby.startGame(player, opponent);
            checkersBoard = gameLobby.getBoard();
            vm.put(RED_PLAYER, player);
            vm.put(WHITE_PLAYER, opponent);

        } else {
            GameLobby gameLobby = playerLobby.getGameLobby(player);

            assert gameLobby != null : "GameLobby is null"; //Should never happen

            opponent = gameLobby.getOpponent(player);
            checkersBoard = gameLobby.getBoard();
            vm.put(RED_PLAYER, opponent);
            vm.put(WHITE_PLAYER, player);
        }


        BoardView boardView = new BoardView(player, checkersBoard);

        LOG.finer("GetGameRoute is invoked.");

        vm.put(GetHomeRoute.CURRENT_USER, player);

        vm.put(GetHomeRoute.HOME_TITLE, "Checkers Game");

        vm.put(BOARD_VIEW, boardView);

        //TODO Right now active color is hardcoded
        vm.put(ACTIVE_COLOR, Piece.pieceColor.RED);

        vm.put(VIEW_MODE, viewMode.PLAY);
//        vm.put("modeOptions", );

        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));

    }
}
