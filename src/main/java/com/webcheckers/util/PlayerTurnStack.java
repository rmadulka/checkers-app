package com.webcheckers.util;

import com.webcheckers.model.Move;

import java.util.Stack;

public class PlayerTurnStack {

    private Stack<Move> validatedMoves;

    public PlayerTurnStack(){

    }

    public Move addValidatedMove(Move move){
        return validatedMoves.push(move);
    }

    public Stack getValidatedMoves(){
        return validatedMoves;
    }
}
