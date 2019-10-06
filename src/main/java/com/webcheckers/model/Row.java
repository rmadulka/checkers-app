package com.webcheckers.model;

import java.util.*;

/**
 * Creates a row containing a list of spaces, then iterates through the row.
 */
public class Row implements Iterable<Space>{
    private int index;                            /* row index */
    private ArrayList<Space> spaceSequence;     /* sequence of spaces in a row */

    public Row(int index, Space[] spaces) {
        this.index = index;
        this.spaceSequence = new ArrayList<>();
        spaceSequence.addAll(Arrays.asList(spaces));
    }

    /**
     * index of the row
     * @return row index: 0 -7
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * returns a list iterator that iterates through the space sequence.
     * @return listIterator
     */
    public Iterator<Space> iterator() {
        return spaceSequence.iterator();
    }
}
