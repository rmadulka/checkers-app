package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class PositionTest {

    /** final ints */
    private final int row = 1;
    private final int col = 2;

    /** Friendly Objects */
    private Position CuT;

    /**
     * Creates a new position object
     */
    @BeforeEach
    public void setUp() {
        this.CuT = new Position(row, col);
    }

    /**
     * Tests that we can access the row
     */
    @Test
    public void testGetRow() {
        assertEquals(row, CuT.getRow());
    }

    /**
     * Tests the we can access the column
     */
    @Test
    public void testGetCell() {
        assertEquals(col, CuT.getCell());
    }
}
