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
    public static final String VIEW_NAME = "signin.ftl";

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

    /**
     * @throws NoSuchElementException
     *    when an invalid result is returned after checking a username
     */

    public String handle(Request request, Response response) {

        //Get Session
        final Session httpSession = request.session();

        //Create the view-model for when there is an invalid username
        final Map<String, Object> vm = new HashMap<>();
        vm.put(GetSigninRoute.TITLE, GetSigninRoute.TITLE_ATTR);

        //Get the username from the HTML form
        final String username = request.queryParams(USER_PARAM);

        ModelAndView mv;

        //Checks for valid username
        switch (playerLobby.checkUsername(username)) {
            case NAMEEXISTS:
                mv = error(vm, EXISTING_NAME);
                break;
            case ALPHA:
                mv = error(vm, INVALID_NAME);
                break;
            case VALID:
                //if valid username, then a new player is made and added to the lobby
                Player newPlayer = new Player(username);
                playerLobby.addPlayer(newPlayer);

                httpSession.attribute(GetHomeRoute.CURRENT_USER, newPlayer);

                //redirect back to home after successful signin
                response.redirect("/");
                halt();
                return null;
            default:
                //This should never happen
                throw new NoSuchElementException("Invalid result of username checked");
        }
        //re-render the signin page
        return templateEngine.render(mv);
    }

    /**
     * Deals with any invalid username
     * @param vm - view-model to be re-rendered
     * @param message - error message
     * @return model and view to be rendered
     */
    private ModelAndView error(Map<String, Object> vm, String message){
        vm.put(USER_TAKEN, message);
        return new ModelAndView(vm, VIEW_NAME);
    }
}
