package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class PostSigninRoute implements Route {
    static final String USER_PARAM = "username";

    static final String VIEW_NAME = "signin.ftl";

    static final String USER_TAKEN = "userTaken";

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;


    PostSigninRoute(TemplateEngine templateEngine, PlayerLobby playerlobby) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.playerLobby = playerlobby;
        this.templateEngine = templateEngine;
    }


    public String handle(Request request, Response response) {

        final Map<String, Object> vm = new HashMap<>();
        vm.put(GetSigninRoute.TITLE, "Signin");

        final String username = request.queryParams(USER_PARAM);

        Player newPLayer = new Player(username);

        boolean userIsTaken = playerLobby.checkUsername(newPLayer);

        if (userIsTaken) {
            vm.put(USER_TAKEN, "username is taken");
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        } else {
            playerLobby.addPlayer(newPLayer);

            //TODO
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }
    }
}
