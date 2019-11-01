package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class PieceTest {

    /** Piece type objects */
    private static Piece.pieceType pieceType;
    private static Piece.pieceColor pieceColor;

    /** Friendly objects */
    private Piece CuT;
    private Piece CuT2;

    /**
     * Initializes the piece attributes
     */
    @BeforeAll
    public static void init() {
        pieceType = Piece.pieceType.SINGLE;
        pieceColor = Piece.pieceColor.WHITE;
    }

    /**
     * Sets up the piece friendly objects
     */
    @BeforeEach
    public void setUp() {
        this.CuT = new Piece(pieceType, pieceColor);
        this.CuT2 = new Piece(pieceType, pieceColor);
    }

    /**
     * Tests that we can access the piece type correctly
     */
    @Test
    public void testGetType() {
        assertEquals(pieceType, CuT.getType());
    }

    /**
     * Tests that we can convert a single piece into a king
     */
    @Test
    public void testConvertToKing() {
        CuT.convertToKing(CuT);
        assertEquals(Piece.pieceType.KING,CuT.getType());
    }

    /**
     * Tests that we can access the color of the piece
     */
    @Test
    public void testGetColor() {
        assertEquals(pieceColor, CuT.getColor());
    }

    /**
     * Tests the equals method when comparing two piece objects
     */
    @Test
    public void testEquals() {
        assertTrue(CuT.equals(CuT2));
        CuT2.convertToKing(CuT2);
        assertFalse(CuT.equals(CuT2));
    }
}
