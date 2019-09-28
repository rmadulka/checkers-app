package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;


public class PostSigninRoute implements Route {
    static final String USER_PARAM = "username";

    static final String EXISTING_NAME = "Username is already taken";
    static final String INVALID_NAME = "Username must only contain alphanumeric characters";


    static final String CURRENT_USER = "currentUser";
    static final String USER_TAKEN = "userTaken";
    static final String VIEW_NAME = "signin.ftl";

    private static final Message LOGIN_MESSAGE = Message.info("Login Successful");

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

                Map<String, Object> homevm = new HashMap<>();
                homevm.put(CURRENT_USER, newPLayer);

                //TODO when ALAN finishers GETHOME
                homevm.put(GetHomeRoute.HOME_TITLE, "Welcome!");

                // display a user message in the Home page
                homevm.put(GetHomeRoute.MESSAGE, LOGIN_MESSAGE);

                return templateEngine.render(new ModelAndView(homevm, GetHomeRoute.VIEW_NAME));
            default:
                //This should never happen
                throw new NoSuchElementException("Invalid result of username checked");
        }
        return templateEngine.render(mv);
    }

    private ModelAndView error(Map<String, Object> vm, String message){
        vm.put(USER_TAKEN, message);
        return new ModelAndView(vm, VIEW_NAME);
    }
}
