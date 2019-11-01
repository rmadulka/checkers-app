package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class SpaceTest {

    /** Friendly Objects */
    private Space CuT;
    private Space CuT2;
    private Space CuT3;

    /** Mock objects and variables */
    private final int cellIdx = 1;
    private final boolean isDark = true;
    private final int cellIdx2 = 2;
    private Piece piece;

    /**
     * Sets up the friendly and mock objects
     */
    @BeforeEach
    public void setUp() {
        this.piece = mock(Piece.class);
        this.CuT = new Space(cellIdx, isDark);
        this.CuT2 = new Space(cellIdx2, isDark);
        this.CuT3 = new Space(cellIdx, isDark);
    }

    /**
     * Tets the getCell method
     */
    @Test
    public void testGetCell() {
        assertEquals(cellIdx, CuT.getCellIdx());
    }

    /**
     * Tests the isValid method
     */
    @Test
    public void testIsValid() {
        assertTrue(CuT.isValid());
    }

    /**
     * Tests if a piece is being placed properly
     */
    @Test
    public void testPlacePiece() {
        assertFalse(CuT.place(null));
        assertTrue(CuT.place(piece));
    }

    /**
     * Tests if a piece is removed correctly
     */
    @Test
    public void testRemovePiece (){
        CuT.place(piece);
        assertNotNull(CuT.getPiece());
        CuT.removePiece();
        assertNull(CuT.getPiece());
    }

    /**
     * Tests the equals method
     */
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
