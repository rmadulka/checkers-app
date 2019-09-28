package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class PostSigninRoute implements Route {
    static final String USER_PARAM = "username";

    static final String VIEW_NAME = "game_form.ftl";

    static final String USER_TAKEN = "UserTaken";

    private final TemplateEngine templateEngine;


    PostSigninRoute(TemplateEngine templateEngine, PlayerLobby playerlobby) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
    }


    public String handle(Request request, Response response) {

        final Map<String, Object> vm = new HashMap<>();

        final String username = request.queryParams(USER_PARAM);

        if (true) {
            //TODO
            vm.put(USER_TAKEN, "username is taken");
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        } else {
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }
    }
}
