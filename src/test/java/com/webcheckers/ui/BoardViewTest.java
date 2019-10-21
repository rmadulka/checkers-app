package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;

import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import com.webcheckers.model.Row;
import com.webcheckers.model.Space;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BoardViewTest {
    private Player red;
    private Player white;
    private Board testBoard;
    private BoardView CuT;


    @BeforeEach
    void testSetup() {
        red = Mockito.mock(Player.class);
        white = Mockito.mock(Player.class);
        testBoard = new Board(white, red);
        CuT = new BoardView(white, testBoard);
    }

    @Test
    void iteratorTest() {
        int redRow = 0;
        Iterator<Row> redRows = CuT.iterator();
        while (redRows.hasNext()) {
            int redCol = 0;
            Row rowRed = redRows.next();
            Iterator<Space> redSpaces = rowRed.iterator();
            while (redSpaces.hasNext()) {
                Space redSpace = redSpaces.next();
                assertEquals(testBoard.getBoard()[redRow][redCol], redSpace);
                redCol++;
            }
            redRow++;
        }
        CuT = new BoardView(red, testBoard);
        int whiteRow = 7;
        Iterator<Row> whiteRows = CuT.iterator();
        while (whiteRows.hasNext()) {
            int whiteCol = 7;
            Row rowWhite = whiteRows.next();
            Iterator<Space> whiteSpaces = rowWhite.iterator();
            while (whiteSpaces.hasNext()) {
                Space whiteSpace = whiteSpaces.next();
                assertEquals(testBoard.getBoard()[whiteRow][whiteCol], whiteSpace);
                whiteCol--;
            }
            whiteRow--;
        }
    }

    @Test
    void getWhitePlayerTest() {
        Assertions.assertSame(CuT.getWhite(), white);
    }

    @Test
    void getRedPlayerTest() {
        Assertions.assertSame(CuT.getRed(), red);
    }
}
