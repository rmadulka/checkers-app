package com.webcheckers.model;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Creates a row containing a list of spaces, then iterates through the row.
 */
public class Row implements Iterable<Space>{
    private int index;                            /* row index */
    private ArrayList<Space> spaceSequence;     /* sequence of spaces in a row */

    public Row(int index, Space[] spaces) {
        this.index = index;
        this.spaceSequence = new ArrayList<>();
        List<Space> spaceSequence = Arrays.asList(spaces);
        spaceSequence.addAll(spaceSequence);
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
    public ListIterator<Space> iterator() {
        return spaceSequence.listIterator();
    }
}