package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RowTest {

    /** Friendly Object */
    private Row CuT;

    /** Needed values */
    private static int index;
    private static Space[] space;

    /**
     * Initializes the index and space
     */
    @BeforeAll
    public static void init() {
        index = 1;
        space = new Space[7];
    }

    /**
     * Creates the Row object that we are testing
     */
    @BeforeEach
    public void setUp() {
        this.CuT = new Row(index, space);
    }

    /**
     * Tests that we can access the index
     */
    @Test
    public void testGetIndex() {
        assertEquals(index, CuT.getIndex());
    }

    /**
     * Tests that an iterator object is not null
     */
    @Test
    public void testIterator() {
        assertNotNull(CuT.iterator());
    }
}
