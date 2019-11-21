package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.BoardState;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

@Tag("UI-tier")

public class GetReplayGameRouteTest {

    static final String VIEW_MODE = "viewMode";
    static final String VIEW_NAME = "game.ftl";
    static final String BOARD_VIEW = "board";

    // mock objects
    private GetReplayGameRoute CuT;
    private PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Response response;
    private Player player;


    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        player = mock(Player.class);

        CuT = new GetReplayGameRoute(engine, playerLobby);
    }

    /**
     * Tests the initial layout of the GetReplayGameRoute
     */
    @Test
    public void new_session(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        player = new Player("joe mama");
        Player player2 = new Player("mike hawk");
        ReplayLobby replayLobby = new ReplayLobby();
        Game game = new Game(player, player2);
        Board board = new Board(player, player2);
        BoardState boardState = new BoardState(board.getActiveColor());
        boardState.constructState(board);
        game.addGameState(boardState);
        replayLobby.addGame(game);

        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute("currentUser")).thenReturn(player);
        when(request.queryParams("gameID")).thenReturn(String.valueOf(game.getId()));
        when(playerLobby.getReplayLobby()).thenReturn(replayLobby);

        CuT.handle(request, response);
        testHelper.assertViewModelAttribute(VIEW_MODE, GetGameRoute.viewMode.REPLAY);
        testHelper.assertViewModelAttribute(GetHomeRoute.CURRENT_USER, player);
        testHelper.assertViewModelAttribute(GetHomeRoute.HOME_TITLE, "Checkers Game");
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, player);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, player2);
        testHelper.assertViewModelAttribute(GetGameRoute.ACTIVE_COLOR, board.getActiveColor());
        testHelper.assertViewModelAttribute(BOARD_VIEW, game.getBoardStates().get(game.getCurrentState()));
    }

    /**
     * Tests whether the enabling of the previous and next buttons
     */
    @Test
    public void enabling_buttons(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        player = new Player("joe mama");
        Player player2 = new Player("mike hawk");
        ReplayLobby replayLobby = new ReplayLobby();
        Game game = new Game(player, player2);
        Board board = new Board(player, player2);
        BoardState boardState = new BoardState(board.getActiveColor());
        boardState.constructState(board);
        game.addGameState(boardState);
        game.addGameState(boardState);
        replayLobby.addGame(game);
        game.changeState(1);

        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(session.attribute("currentUser")).thenReturn(player);
        when(request.queryParams("gameID")).thenReturn(String.valueOf(game.getId()));
        when(playerLobby.getReplayLobby()).thenReturn(replayLobby);

        CuT.handle(request, response);

    }
}
