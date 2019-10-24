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
    private static final String TITLE_HEAD_TAG = "<title>Web Checkers | "  + TITLE + "</title>";
    private static final String TITLE_H1_TAG = "<h1>Web Checkers | " + TITLE + "</h1>";
    private static final String PLAYERS_ONLINE = "<h5>0 </h5>";
    private static final String NUM_PLAYERS = "0";
    private final TemplateEngine engine = new FreeMarkerEngine();



    @Test
    public void test_default_home_page(){
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetHomeRoute.VIEW_NAME);
        vm.put(GetHomeRoute.HOME_TITLE, TITLE);
        vm.put(GetHomeRoute.PLAYERS_ONLINE, PLAYERS_ONLINE);
        vm.put(GetHomeRoute.NUM_PLAYERS, PLAYERS_ONLINE);
        final String viewHtml = engine.render(modelAndView);
        assertTrue(viewHtml.contains(TITLE_H1_TAG), "Title heading exists");
        assertTrue(viewHtml.contains(TITLE_HEAD_TAG), "Title head exists");
        assertTrue(viewHtml.contains(PLAYERS_ONLINE), "Online player count exists");
        assertTrue(viewHtml.contains(NUM_PLAYERS), "Num players exists");


    }

    public void test_error_message(){
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetHomeRoute.VIEW_NAME);
        vm.put(GetHomeRoute.HOME_TITLE, TITLE);
        vm.put(GetHomeRoute.PLAYERS_ONLINE, PLAYERS_ONLINE);
        vm.put(GetHomeRoute.NUM_PLAYERS, PLAYERS_ONLINE);
        final String viewHtml = engine.render(modelAndView);
        assertTrue(viewHtml.contains(TITLE_HEAD_TAG), "Title head tag exists.");
        assertTrue(viewHtml.contains(TITLE_H1_TAG), "Title heading tag exists.");
    }
}
