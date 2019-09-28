package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import static spark.Spark.halt;
import static spark.Spark.staticFileLocation;

public class PostSigninRoute implements Route {
    static final String USER_PARAM = "username";
    static final String EXISTING_NAME = "Username is already taken";
    static final String INVALID_NAME = "Username must only contain alphanumeric characters";

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

        ModelAndView mv;

        switch (playerLobby.checkUsername(username)) {
            case NAMEEXISTS:
                mv = error(vm, EXISTING_NAME);
                break;
            case ALPHA:
                mv = error(vm, INVALID_NAME);
                break;
            case VALID:
                Player newPLayer = new Player(username);
                playerLobby.addPlayer(newPLayer);

                //TODO clean up

                Map<String, Object> homevm = new HashMap<>();
                homevm.put("currentUser", newPLayer);
                homevm.put("title", "Welcome!");

                // display a user message in the Home page
                //homevm.put("message", "YOU HAVE SIGNED IN");

                return templateEngine.render(new ModelAndView(homevm, "home.ftl"));
            default:
                //This should never happen
                throw new NoSuchElementException("Invalid result of username checked");
        }
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }

    private ModelAndView error(Map<String, Object> vm, String message){
        vm.put(USER_TAKEN, message);
        return new ModelAndView(vm, VIEW_NAME);
    }
}
