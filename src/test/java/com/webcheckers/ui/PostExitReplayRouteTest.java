package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.*;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
public class PostExitReplayRouteTest {
    // mock objects
    private PostExitReplayRoute CuT;
    private Request request;
    private Session session;
    private Response response;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        CuT = new PostExitReplayRoute();
    }

    /**
     * Tests whether an exit out of a replay game is properly executed
     */
    @Test
    public void exit(){
        Message m = Message.info("Exiting Game");
        assertEquals(CuT.handle(request, response), new Gson().toJson(m));

    }
}