package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpaceTest {

    private Space CuT;
    private Space CuT2;
    private Space CuT3;
    private final int cellIdx = 1;
    private final boolean isDark = true;
    private final int cellIdx2 = 2;
    private Piece piece;

    @BeforeEach
    public void setUp() {
        this.piece = mock(Piece.class);
        this.CuT = new Space(cellIdx, isDark);
        this.CuT2 = new Space(cellIdx2, isDark);
        this.CuT3 = new Space(cellIdx, isDark);
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
    public void testRemovePiece (){
        CuT.place(piece);
        assertNotNull(CuT.getPiece());
        CuT.removePiece();
        assertNull(CuT.getPiece());
    }

    @Test
    public void testEquals() {
        assertNotEquals(true, CuT.equals(piece));
        assertNotEquals(true, CuT.equals(CuT2));
        assertTrue(CuT.equals(CuT3));
        CuT.place(piece);
        CuT3.place(piece);
        assertTrue(CuT.equals(CuT3));
    }
}
