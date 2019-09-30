package com.webcheckers.model;

import java.util.ListIterator;

/**
 * model of the board for the boardview ui
 */

 public class Board {//implements Iterable<Row> {
     private static final int SIZE = 8;
     private Player white;
     private Player red;
     private Space[][] board;

     public Board(Player white, Player red) {
         this.white = white;
         this.red = red;
         init();
     }

     private void init() {
         this.board = new Space[SIZE][SIZE];

         for (int row = 0; row < SIZE; row++) {
             for (int col = 0; col < SIZE; col++) {
                 if (row % 2 == 0) {
                     if (col % 2 == 0) board[row][col] = new Space(col, false);
                     else board[row][col] = new Space(col, true);
                 } else {
                     if (col % 2 == 1) board[row][col] = new Space(col, false);
                     else board[row][col] = new Space(col, true);
                 }
             }
         }
     }

     private void populate() {
         //TODO: implement a method to place/remove a piece in Space.java
     }

     public Space[][] getBoard() {
         return this.board;
     }

    /*public ListIterator<Row> iterator() {

    }*/
 }