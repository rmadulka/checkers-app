package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import spark.ModelAndView;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class GetSigninRouteTest {

    /** friendly object */
    private GetSigninRoute CuT;


    /** Mock Objects */
    private Request request;
    private Session session;
    private TemplateEngine engine;
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
        engine = mock(TemplateEngine.class);

        CuT = new GetSigninRoute(engine);
    }

    /**
     * Tests that the CuT shows signin view when the new page is loaded
     */
    @Test
    public void newSignIn() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetSigninRoute.TITLE, GetSigninRoute.TITLE_ATTR);
        testHelper.assertViewName(GetSigninRoute.VIEW_NAME);
    }
}