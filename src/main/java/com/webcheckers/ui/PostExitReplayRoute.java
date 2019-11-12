package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;


public class PostExitReplayRoute implements Route {

    /**
     * Used to let the user return back to the home screen when in on the replay page
     */
    public PostExitReplayRoute(){

    }

    /**
     * Used to inform the ajax calls that the user has exited the replay
     * @param request the HTTP request
     * @param response the HTTP response
     * @return exiting message
     */
    public Object handle(Request request, Response response){
        Message message = Message.info("Exiting Game");
        return new Gson().toJson(message);
    }
}
