package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.appl.GameLobby;
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
        REPLAY
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

        //Get Session and Parameters
        final String receiverName = request.queryParams("receiver");
        final Session httpSession = request.session();
        GameLobby gameLobby;
        Board checkersBoard;

        Player player = httpSession.attribute(GetHomeRoute.CURRENT_USER);
        Player opponent;

        //Create new view-model
        Map<String, Object> vm = new HashMap<>();

        //If a player clicks on someone in the list
        if(!player.getInGame()) {
            opponent = playerLobby.getPlayer(receiverName);

            //If the opponent is in a game, return home w/ error message
            if (opponent.getInGame()) {
                httpSession.attribute(GENERIC_MESSAGE, IN_GAME_MSG);

                response.redirect("/");
                halt();
                return null;
            }

            //If the opponent is available, create a new game
            gameLobby = playerLobby.startGame(player, opponent);
            checkersBoard = gameLobby.getBoard();
            vm.put(RED_PLAYER, player);
            vm.put(WHITE_PLAYER, opponent);

        //When the opponent is clicked on, set up the game values
        } else {
            gameLobby = playerLobby.getGameLobby(player);
            //assert gameLobby != null : "GameLobby is null"; //Should never happen
            opponent = gameLobby.getOpponent(player);
            checkersBoard = gameLobby.getBoard();
            if(gameLobby.getWhitePlayer().equals(player)) {
                vm.put(RED_PLAYER, opponent);
                vm.put(WHITE_PLAYER, player);
            } else if (gameLobby.getRedPlayer().equals(player)){
                vm.put(RED_PLAYER, player);
                vm.put(WHITE_PLAYER, opponent);
            }
        }

        LOG.finer("GetGameRoute is invoked.");

        //New, separate boardViews for each player
        BoardView boardView = new BoardView(player, checkersBoard);

        //load vm values
        vm.put(GetHomeRoute.CURRENT_USER, player);
        vm.put(GetHomeRoute.HOME_TITLE, "Checkers Game");
        vm.put(BOARD_VIEW, boardView);
        final Map<String, Object> modeOptions = new HashMap<>(2);
        //TODO implement a Replay button at the end of a game to navigate to the replay ui
        if(gameLobby.getIsGameOver()) {           //this if statement is flawed as it bypasses the entire gameover screen,
            modeOptions.put("hasNext", true);     //have the replay vm attached to a button directing the user to the replaymode
            modeOptions.put("hasPrevious", true);
            response.redirect("/replay/game");
            halt();
            return null;
        } else {
            vm.put(VIEW_MODE, viewMode.PLAY);
            modeOptions.put("isGameOver", gameLobby.getIsGameOver());
            modeOptions.put("gameOverMessage", gameLobby.getGameOverMessageAsString());
        }
        vm.put(ACTIVE_COLOR, checkersBoard.getActiveColor());

        vm.put("modeOptionsAsJSON", new Gson().toJson(modeOptions));

        Message.displayMessage(httpSession, vm,null, "messageSignout");

        //render game view
        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));

    }
}
