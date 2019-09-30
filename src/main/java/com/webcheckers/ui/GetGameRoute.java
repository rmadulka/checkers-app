package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public class GetGameRoute implements Route {

    private final TemplateEngine templateEngine;
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    public GetGameRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        LOG.config("GetGameRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {

        final Player sender = request.attribute("sender");
        final Player receiver = request.attribute("receiver");

        System.out.println("GOT THAT SHIT");

        LOG.finer("GetGameRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Checkers");

        vm.put("currentUser", sender);
        vm.put("redPlayer", sender);
        vm.put("whitePlayer", receiver);

        vm.put("activeColor", Piece.pieceColor.RED);

//        vm.put("viewMode", );
//        vm.put("modeOptions", );

        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
