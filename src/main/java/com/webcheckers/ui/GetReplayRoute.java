package com.webcheckers.ui;

import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GetReplayRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetReplayRoute.class.getName());
    private final TemplateEngine templateEngine;

    public static final String TITLE = "title";
    public static final String TITLE_ATTR = "Replay";
    public static final String VIEW_NAME = "replay.ftl";

    public GetReplayRoute(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("GetReplayRoute is invoked.");
        Session session = request.session();
        Player player = session.attribute("currentUser");
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE, TITLE_ATTR);
        vm.put("currentUser", player);

        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }
}
