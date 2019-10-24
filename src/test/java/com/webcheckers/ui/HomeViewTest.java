package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.ModelAndView;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;
@Tag("UI-tier")
public class HomeViewTest {
    private static final String TITLE = "the_title";
    private static final String TITLE_HEAD_TAG = "<title>Web Checkers| "  + TITLE + "</title>";
    private static final String TITLE_H1_TAG = "<h1>Web Checkers | the_title</h1>";
    private static final String TITLE2 = "MyTitle";
    private static final String TITLE2_HEAD_TAG = "<title>" + TITLE2 + "</title>";
    private static final String TITLE_H2_TAG = "<h2>" + TITLE2 + "</title>";
    private static final String PLAYERS_ONLINE = "<h5>0 </h5>";
    private static final String NUM_PLAYERS = "0";
    private final TemplateEngine engine = new FreeMarkerEngine();



    @Test
    public void check_home_page(){
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetHomeRoute.VIEW_NAME);
        vm.put(GetHomeRoute.HOME_TITLE, TITLE);
        vm.put(GetHomeRoute.PLAYERS_ONLINE, PLAYERS_ONLINE);
        vm.put(GetHomeRoute.NUM_PLAYERS, PLAYERS_ONLINE);
        final String viewHtml = engine.render(modelAndView);
        System.out.println(viewHtml);
        assertTrue(viewHtml.contains(TITLE_H1_TAG), "Title heading exists");
        assertTrue(viewHtml.contains(PLAYERS_ONLINE), "Online player count exists");
        assertTrue(viewHtml.contains(NUM_PLAYERS), "Num players exists");


    }
}
