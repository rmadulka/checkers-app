package com.webcheckers.ui;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GetReplayRoute implements Route {
    /** A logger */
    private static final Logger LOG = Logger.getLogger(GetReplayRoute.class.getName());
    /** A template engine */
    private final TemplateEngine templateEngine;

    private PlayerLobby playerLobby;

    /** strings for the vm */
    public static final String TITLE = "title";
    public static final String TITLE_ATTR = "Replay";
    public static final String VIEW_NAME = "replay.ftl";
    public static final String CURRENT_USER = "currentUser";
    public static final String GAMES = "games";

    /**
     * Creates a new instance of a replay route
     * @param templateEngine A template engine
     */
    public GetReplayRoute(TemplateEngine templateEngine, PlayerLobby playerLobby) {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    /**
     * Renders a replay screen that shows all the games played
     * @param request An http request
     * @param response An http response
     * @return The template engine
     */
    @Override
    public Object handle(Request request, Response response){
        LOG.finer("GetReplayRoute is invoked.");
        Session session = request.session();
        Player player = session.attribute("currentUser");
        ReplayLobby replayLobby = playerLobby.getReplayLobby();
        Map<String, Object> vm = new HashMap<>();
        if(replayLobby.getGames() != null) {
            ArrayList<Game> gameList = replayLobby.getGames();
            gameList.add(new Game(new Player("Joe"), new Player("Mama"), 0));
            vm.put(GAMES, gameList);
        }
        vm.put(TITLE, TITLE_ATTR);
        vm.put(CURRENT_USER, player);

        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }
}
