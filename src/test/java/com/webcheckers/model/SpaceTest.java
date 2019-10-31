package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpaceTest {

    private Space CuT;
    private final int cellIdx = 1;
    private final boolean isDark = true;
    private Piece piece;

    @BeforeEach
    public void setUp() {
        this.piece = mock(Piece.class);
        this.CuT = new Space(cellIdx, isDark);
    }

    @Test
    public void testGetCell() {
        assertEquals(cellIdx, CuT.getCellIdx());
    }

    @Test
    public void testIsValid() {
        assertTrue(CuT.isValid());
    }

    @Test
    public void testPlacePiece() {
        assertFalse(CuT.place(null));
        assertTrue(CuT.place(piece));
    }

    @Test
    public void testByeBye (){
        
    }

}
