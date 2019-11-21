package com.webcheckers.model;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("Model-tier")
public class AIPlayerTest {

    /** Friendly Object */
    private AIPlayer CuT;

    /** static */
    private static PlayerLobby playerLobby;
    private static Player red;
    private static Player white;
    private static Board board;
    private static GameLobby realGameLobby;

    /** Mock objects */
    private GameLobby gameLobby;
    private Board mockBoard;
    private Player mockRed;
    private Player mockWhite;
    private ReplayLobby mockReplay;

    /**
     * Sets up the mock and friendly objects before each test
     */
    @BeforeEach
    public void setUp() {
        playerLobby = mock(PlayerLobby.class);
        red = new Player("Foo");
        //this needs to be the name of the AI
        white = new Player("Joseph 'Joe' Mama 0");
        board = new Board(red, white);
        this.CuT = new AIPlayer(playerLobby);
        this.gameLobby = mock(GameLobby.class);
        this.mockBoard = mock(Board.class);
        this.mockRed = mock(Player.class);
        this.mockWhite = mock(Player.class);
        this.mockReplay = mock(ReplayLobby.class);
    }

    /**
     * Helper function to clear the board to create test cases
     * @param board the board
     * @return An unpopulated game
     */
    private Space[][] clearBoard(Board board){
        Space[][] game = board.getBoard();
        for(int i = 0; i < game.length; i ++) {
            for (int j = 0; j < game.length; j ++) {
                game[i][j].removePiece();
            }
        }
        return game;
    }

    /**
     * Tests that a single move turn can be made
     */
    @Test
    public void testMakeTurn() {
        realGameLobby = new GameLobby(red, white);
        when(playerLobby.getGameLobby(CuT)).thenReturn(realGameLobby);
        realGameLobby.getBoard().switchTurn();
        CuT.addInGameStatus();
        CuT.makeTurn();
        assertEquals(0, CuT.turnStack.size());
    }

    /**
     * Tests the inner while loop of the makeTurn method
     */
    @Test
    public void testInvalidTurn() {
        realGameLobby = new GameLobby(red, white);
        when(playerLobby.getGameLobby(CuT)).thenReturn(realGameLobby);
        realGameLobby.getBoard().switchTurn();
        clearBoard(realGameLobby.getBoard());
        Piece red = new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED);
        Piece white = new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE);
        Space[][] game = realGameLobby.getBoard().getBoard();
        game[4][1].place(red);
        game[2][1].place(red);
        game[6][6].place(red);
        game[5][0].place(white);
        CuT.addInGameStatus();
        CuT.makeTurn();
        assertEquals(0, CuT.turnStack.size());
    }

    /**
     * Tests the end game condition when there are no remaining pieces in the makeTurn method
     */
    @Test
    public void testEndGameNoPieces() {
        realGameLobby = new GameLobby(red, white);
        when(playerLobby.getGameLobby(CuT)).thenReturn(realGameLobby);
        when(playerLobby.getReplayLobby()).thenReturn(mockReplay);
        realGameLobby.getBoard().switchTurn();
        clearBoard(realGameLobby.getBoard());
        Piece red = new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED);
        Piece white = new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE);
        Space[][] game = realGameLobby.getBoard().getBoard();
        game[4][1].place(red);
        game[5][0].place(white);
        CuT.addInGameStatus();
        CuT.makeTurn();
        assertEquals(0, CuT.turnStack.size());
    }

    /**
     * Tests end game condition where there are no moves for the AI
     */
    @Test
    public void testEndGameNoMoves() {
        realGameLobby = new GameLobby(red, white);
        when(playerLobby.getGameLobby(CuT)).thenReturn(realGameLobby);
        when(playerLobby.getReplayLobby()).thenReturn(mockReplay);
        realGameLobby.getBoard().switchTurn();
        clearBoard(realGameLobby.getBoard());
        Piece red = new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED);
        Piece white = new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE);
        Space[][] game = realGameLobby.getBoard().getBoard();
        game[6][7].place(white);
        game[5][6].place(red);
        game[4][5].place(red);
        CuT.addInGameStatus();
        CuT.makeTurn();
        assertEquals(0, CuT.turnStack.size());
    }

    /**
     * Checks that the AI knows when it is their turn
     */
    @Test
    public void testCheckMyTurn() {
        when(playerLobby.getGameLobby(CuT)).thenReturn(gameLobby);
        when(gameLobby.getRedPlayer()).thenReturn(mockRed);
        when(gameLobby.getWhitePlayer()).thenReturn(mockWhite);
        when(gameLobby.getBoard()).thenReturn(mockBoard);
        when(mockBoard.getActiveColor()).thenReturn(Piece.pieceColor.RED);
        CuT.addInGameStatus();
        assertFalse(CuT.checkMyTurn());
    }

    /**
     * Tests that a single and a jump move can be selected
     */
    @Test
    public void testGetRandomMove () {
        Space[][] game = board.getBoard();
        board.switchTurn();
        assertNotNull(CuT.getRandomMove(board));
        game[5][4].removePiece();
        game[4][5].place(new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED));
        assertNull(game[3][4].getPiece());
        Move move = CuT.getRandomMove(board);
        Piece piece = game[5][6].getPiece();
        game[move.getEndRow()][move.getEndCell()].place(piece);
        assertNotNull(game[3][4].getPiece());
    }

    /**
     * Tests that all possible simple moves, including a king jump are found
     */
    @Test
    public void testSimpleMove() {
        Space[][] game = board.getBoard();
        board.switchTurn();
        game[0][1].removePiece();
        game[1][0].removePiece();
        game[1][2].removePiece();
        game[0][1].place(new Piece(Piece.pieceType.KING, Piece.pieceColor.WHITE));
        List<Move> move = CuT.getValidSimpleMoves(board);
        assertEquals(9, move.size());
    }

    /**
     * Tests that forward jump moves can be found
     */
    @Test
    public void testJumpMove() {
        Space[][] game = board.getBoard();
        board.switchTurn();
        Piece piece = new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED);
        game[4][1].place(piece);
        game[4][3].place(piece);
        game[4][5].place(piece);
        List<Move> move = CuT.getValidJumpMoves(board);
        assertEquals(6, move.size());
    }

    /**
     * Tests that backwards jump moves can be found
     */
    @Test
    public void testKingJumps() {
        Space[][] game = board.getBoard();
        board.switchTurn();
        game[0][1].removePiece();
        game[2][3].removePiece();
        Piece piece = new Piece(Piece.pieceType.KING, Piece.pieceColor.WHITE);
        game[0][1].place(piece);
        List<Move> move;
        move = CuT.getValidJumpMoves(board);
        assertEquals(1, move.size());
        game[0][3].removePiece();
        game[2][1].removePiece();
        game[2][5].removePiece();
        game[0][3].place(piece);
        game[7][6].removePiece();
        game[7][6].place(piece);
        move = CuT.getValidJumpMoves(board);
        assertEquals(3, move.size());
    }

    /**
     * Tests multijumps with a single piece
     */
    @Test
    public void testMultiJump() {
        Piece red = new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED);
        Piece white = new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.WHITE);
        Space[][] game = clearBoard(board);
        game[4][1].place(red);
        game[2][1].place(red);
        game[3][2].place(white);
        game[3][0].place(white);
        board.switchTurn();
        Move jump = new Move (new Position(5, 0), new Position(3, 2));
        Move move;
        move = CuT.getMultijump(jump, board);
        assertEquals(0, move.getEndCell());
        assertEquals(1, move.getEndRow());
        Move jump2 = new Move (new Position(5, 2), new Position(3, 0));
        move = CuT.getMultijump(jump2, board);
        assertEquals(2, move.getEndCell());
        assertEquals(1, move.getEndRow());
    }

    /**
     * Tests that a king piece can perform a multijump backwards
     */
    @Test
    public void testKingMultiJump() {
        Piece red = new Piece(Piece.pieceType.SINGLE, Piece.pieceColor.RED);
        Piece white = new Piece(Piece.pieceType.KING, Piece.pieceColor.WHITE);
        Space[][] game = clearBoard(board);
        game[4][1].place(red);
        game[3][2].place(white);
        game[3][0].place(white);
        board.switchTurn();
        Move jump = new Move (new Position(1, 0), new Position(3, 2));
        Move move;
        move = CuT.getMultijump(jump, board);
        assertEquals(0, move.getEndCell());
        assertEquals(5, move.getEndRow());
        Move jump2 = new Move (new Position(1, 2), new Position(3, 0));
        move = CuT.getMultijump(jump2, board);
        assertEquals(2, move.getEndCell());
        assertEquals(5, move.getEndRow());
    }
}
