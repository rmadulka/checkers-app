package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebServerTest {

    /** Friendly Object */
    private WebServer CuT;

    /** Mock Objects */
    private PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private TemplateEngine engine;

    /**
     * Initializes mock and and friendly objects
     */
    @BeforeEach
    public void setUp() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        CuT = new WebServer(engine, new Gson(),playerLobby);
    }

    /**
     * Calls the initialize method in webserver
     */
    @Test
    public void testInitialize() {
        CuT.initialize();
    }
}
