package com.webcheckers.ui;

import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GetReplayRoute implements Route {
    /** A logger */
    private static final Logger LOG = Logger.getLogger(GetReplayRoute.class.getName());
    /** A template engine */
    private final TemplateEngine templateEngine;

    /** strings for the vm */
    public static final String TITLE = "title";
    public static final String TITLE_ATTR = "Replay";
    public static final String VIEW_NAME = "replay.ftl";
    public static final String CURRENT_USER = "currentUser";

    /**
     * Creates a new instance of a replay route
     * @param templateEngine A template engine
     */
    public GetReplayRoute(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Renders a replay screen that shows all the games played
     * @param request An http request
     * @param response An http response
     * @return The template engine
     * @throws Exception
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("GetReplayRoute is invoked.");
        Session session = request.session();
        Player player = session.attribute("currentUser");
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE, TITLE_ATTR);
        vm.put(CURRENT_USER, player);

        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }
}
