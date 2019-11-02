package com.webcheckers.model;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("model-tier")
public class JumpMoveTest {
  /**
   * Friendly object
   */
  private JumpMove CuT;

  /**
   * Mock object
   */
  private Player redPlayer;

  /**
   * Mock object
   */
  private Player whitePlayer;

  /**
   * Mock object
   */
  private Board board;

  /**
   * Mock object
   */
   private Move move;

   @BeforeEach
   public void init() {
    redPlayer = mock(Player.class);
    whitePlayer = mock(Player.class);
    board = mock(Board.class);
    move = mock(Move.class);
   }

   @Test
   public void jumpMoveTest() {

   }

   @Test
   public void jumpMoveWrongColor() {

   }

   @Test
   public void jumpMoveNoCapture() {

   }

   @Test
   public void jumpMoveWrongDirection() {

   }
}
