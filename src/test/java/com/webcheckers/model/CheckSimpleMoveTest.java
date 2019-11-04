package com.webcheckers.model;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CheckSimpleMoveTest {
    private CheckSimpleMove CuT;

    /**
     * Mock board object
     */
    private static Board board;

    /**
     * Grid of Spaces
     */
    private static Space[][] grid;

    /**
     * Set up Objects
     */
    @BeforeEach
    public void init() {
        CuT = new CheckSimpleMove();
        board = mock(Board.class);
        when(board.getBoard()).thenReturn(new Space[8][8]);
        grid = board.getBoard();
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                grid[r][c] = new Space(c, true);
            }
        }
    }

    /**
     * Test a lone Red single Piece
     */
    @Test
    public void testSinglePieceRed(){

        when(board.getActiveColor()).thenReturn(Piece.pieceColor.RED);

        grid[2][3].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));

        assertTrue(CuT.checkMoves(null, board));
    }

    /**
     * Test a blocked Red single Piece
     */
    @Test
    public void testSinglePieceBlockedRed(){
        when(board.getActiveColor()).thenReturn(Piece.pieceColor.RED);

        grid[2][3].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
        grid[3][4].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
        grid[3][2].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));

        assertFalse(CuT.checkMoves(null, board));
    }

    /**
     * Test a lone White single Piece
     */
    @Test
    public void testSinglePieceWhite(){

        when(board.getActiveColor()).thenReturn(Piece.pieceColor.WHITE);

        grid[2][3].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));

        assertTrue(CuT.checkMoves(null, board));
    }

    /**
     * Test a blocked single white piece
     */
    @Test
    public void testSinglePieceBlockedWhite(){
        when(board.getActiveColor()).thenReturn(Piece.pieceColor.WHITE);

        grid[2][3].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
        grid[1][4].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
        grid[1][2].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));

        assertFalse(CuT.checkMoves(null, board));
    }

    /**
     * Test a half-blocked Red King Piece
     */
    @Test
    public void testKingPieceRedForwardBlock(){

        when(board.getActiveColor()).thenReturn(Piece.pieceColor.RED);

        grid[2][3].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
        grid[3][4].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
        grid[3][2].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));

        assertTrue(CuT.checkMoves(null, board));
    }

    /**
     * Test a completely blocked Red King Piece
     */
    @Test
    public void testKingPieceRedAllBlock(){

        when(board.getActiveColor()).thenReturn(Piece.pieceColor.RED);

        grid[2][3].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
        grid[3][4].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
        grid[3][2].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
        grid[1][4].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
        grid[1][2].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));

        assertFalse(CuT.checkMoves(null, board));
    }

    /**
     * Test a half blocked White King Piece
     */
    @Test
    public void testKingPieceWhiteForwardBlock(){

        when(board.getActiveColor()).thenReturn(Piece.pieceColor.WHITE);

        grid[2][3].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.WHITE));
        grid[1][4].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
        grid[1][2].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));

        assertTrue(CuT.checkMoves(null, board));
    }

    /**
     * Test a completely blocked White Kign Piece
     */
    @Test
    public void testKingPieceWhiteAllBlock(){

        when(board.getActiveColor()).thenReturn(Piece.pieceColor.WHITE);

        grid[2][3].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.WHITE));
        grid[3][4].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
        grid[3][2].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
        grid[1][4].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
        grid[1][2].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));

        assertFalse(CuT.checkMoves(null, board));
    }

    /**
     * Test a King Piece on the edge
     */
    @Test
    public void testKingPieceRedEdge(){

        when(board.getActiveColor()).thenReturn(Piece.pieceColor.RED);

        grid[0][3].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.RED));
        grid[1][4].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));
        grid[1][2].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE));

        assertFalse(CuT.checkMoves(null, board));
    }

}
