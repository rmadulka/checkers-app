package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class MoveTest {

    /** Friendly object */
    private Move CuT;
    private Move CuT2;
    private Move CuT3;

    /** Mock objects */
    private static Position start;
    private static Position end;

    private static Position endJump;
    private static Position endJumpInvalid;

    /**
     * Sets test values for the start and end positions
     */
    @BeforeAll
    public static void init() {
        start = new Position(1, 1);
        end = new Position(2, 2);
        endJump = new Position(3, 3);
        endJumpInvalid = new Position (1, 3);
    }

    /**
     * Sets up the friendly object
     */
    @BeforeEach
    public void setUp() {
        this.CuT = new Move(start, end);
        this.CuT2 = new Move(start, endJump);
        this.CuT3 = new Move(start, endJumpInvalid);
    }

    /**
     * Tests that we get the correct start position
     */
    @Test
    public void testGetStart() {
        assertEquals(start, CuT.getStart());
        assertNotEquals(end, CuT.getStart());
    }

    /**
     * Tests that we get the correct end position
     */
    @Test
    public void testGetEnd() {
        assertEquals(end, CuT.getEnd());
        assertNotEquals(start, CuT.getEnd());
    }

    /**
     * Tests that we get the correct start row
     */
    @Test
    public void testGetStartRow() {
        assertEquals(start.getRow(), CuT.getStartRow());
        assertNotEquals(end.getRow(), CuT.getStartRow());
    }

    /**
     * Tests that we get the correct start cell value
     */
    @Test
    public void testGetStartCell() {
        assertEquals(start.getCell(), CuT.getStartCell());
        assertNotEquals(end.getCell(), CuT.getStartCell());
    }

    /**
     * Tests that we get the correct end row
     */
    @Test
    public void testGetEndRow() {
        assertEquals(end.getRow(), CuT.getEndRow());
        assertNotEquals(start.getRow(), CuT.getEndRow());
    }

    /**
     * Tests that we get the correct start cell
     */
    @Test
    public void testGetEndCell() {
        assertEquals(end.getCell(), CuT.getEndCell());
        assertNotEquals(start.getCell(), CuT.getEndCell());
    }

    /**
     * Tests that a move is a jump move
     */
    @Test
    public void testIsJumpMove() {
        assertFalse(CuT.isJumpMove());
        assertTrue(CuT2.isJumpMove());
        assertFalse(CuT3.isJumpMove());
    }
}
