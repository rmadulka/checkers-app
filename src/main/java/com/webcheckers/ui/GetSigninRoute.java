package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetSigninRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

    private final TemplateEngine templateEngine;

    public static final String TITLE = "title";
    public static final String TITLE_ATTR = "Signin";

    public static final String VIEW_NAME = "signin.ftl";

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSigninRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetSigninRoute is initialized.");
    }

    /**
     * Render the WebCheckers Signin page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSigninRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE, TITLE_ATTR);


        // render the View
        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }
}
