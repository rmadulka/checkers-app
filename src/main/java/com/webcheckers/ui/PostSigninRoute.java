package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import static spark.Spark.*;


public class PostSigninRoute implements Route {

    //Parameters retrieved in the handle method
    static final String USER_PARAM = "username";

    //Values used in the view-model
    static final String USER_TAKEN = "userTaken";
    static final String VIEW_NAME = "signin.ftl";

    //Error messages
    static final String EXISTING_NAME = "Username is already taken";
    static final String INVALID_NAME = "Username must only contain alphanumeric characters";

    //Successful Login Message
    private static final Message LOGIN_MESSAGE = Message.info("Login Successful");

    //Attributes
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;


    /**
     * The constructor for the {@code POST /signin} route handler.
     *
     * @param playerlobby
     *    {@Link GameCenter} that holds over statistics
     * @param templateEngine
     *    template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the {@code gameCenter} or {@code templateEngine} parameter is null
     */
    PostSigninRoute(TemplateEngine templateEngine, PlayerLobby playerlobby) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.playerLobby = playerlobby;
        this.templateEngine = templateEngine;
    }


    public String handle(Request request, Response response) {

        final Session httpSession = request.session();

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
                Player newPlayer = new Player(username);
                playerLobby.addPlayer(newPlayer);

                httpSession.attribute("currentUser", newPlayer);

//                Map<String, Object> homevm = new HashMap<>();
//                homevm.put("title", "Welcome!");
//                // display a user message in the Home page
//                homevm.put("currentUser", newPlayer);


                response.redirect("/");
                halt();
                return null;
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
