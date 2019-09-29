package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  //Welcome message
  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;

  //Values for the view-model
  static final String CURRENT_USER = "currentUser";
  static final String HOME_TITLE = "title";
  private static final String PLAYERS_ONLINE = "playersOnline";
  private static final String NUM_PLAYERS = "numPlayers";
  static final String MESSAGE = "message";
  static final String VIEW_NAME = "home.ftl";

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

    //Get Session and attributes
    final Session httpSession = request.session();
    Player currentUser = httpSession.attribute("currentUser");

    //create view-model for home page
    Map<String, Object> vm = new HashMap<>();
    vm.put(HOME_TITLE, "Welcome!");
    vm.put(PLAYERS_ONLINE, this.playerLobby.getPlayers());
    vm.put(NUM_PLAYERS, this.playerLobby.getPlayers().size());
    // display a user message in the Home page
    vm.put(MESSAGE, WELCOME_MSG);
    vm.put(CURRENT_USER, currentUser);

    // render the View
    return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
  }
}
