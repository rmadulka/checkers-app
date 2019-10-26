package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  //Welcome message
  static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  static final String HOME_TITLE_TEXT = "Welcome!";

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;

  //Values for the view-model
  static final String CURRENT_USER = "currentUser";
  static final String HOME_TITLE = "title";
  static final String PLAYERS_ONLINE = "playersOnline";
  static final String NUM_PLAYERS = "numPlayers";
  public static final String MESSAGE = "message";
  static final String VIEW_NAME = "home.ftl";

  //Message String for session attribute
  public static final String MESSAGE_ATR = "message";


  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerLobby = playerLobby;
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
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
    LOG.finer("GetHomeRoute is invoked.");
    Map<String, Object> vm = new HashMap<>();
    //Get Session and attributes
    final Session httpSession = request.session();
    Player currentUser = httpSession.attribute("currentUser");

    //Displays a new message if there is one
    Message.displayMessage(httpSession, vm, WELCOME_MSG);

    //If the currentUser is clicked on at any point, send them to the game
    if (currentUser != null && currentUser.getInGame()){
      response.redirect("/game");
      halt();
      return null;
    }

    //generate view-model for home page
    vm.put(HOME_TITLE, HOME_TITLE_TEXT);
    vm.put(PLAYERS_ONLINE, this.playerLobby.getPlayers());
    vm.put(NUM_PLAYERS, this.playerLobby.getNumPlayers());
    vm.put(CURRENT_USER, currentUser);

    // render the View
    return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
  }
}
