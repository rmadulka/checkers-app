package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import spark.ModelAndView;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PostSignoutRouteTest {

    private PostSignoutRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private PlayerLobby playerLobby;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        playerLobby = mock(PlayerLobby.class);
        CuT = new PostSignoutRoute(playerLobby);
    }

    @Test
    public void newPostSignOut(){
        HashSet<Player> players = new HashSet<>();

        players.add(mock(Player.class));
        players.add(mock(Player.class));
        players.add(mock(Player.class));

        when(playerLobby.getPlayers()).thenReturn(players);
        CuT.handle(request, response);

    }




}
