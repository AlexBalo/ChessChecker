package tests;

import junit.framework.TestCase;
import main.models.Bishop;
import main.models.Piece;

import java.util.ArrayList;
import java.util.List;

public class BishopTest extends TestCase {

    private boolean[][] board;
    private Bishop bishop;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        board = new boolean[5][5];
        bishop = new Bishop();
        bishop.setRow(2);
        bishop.setColumn(2);
    }

    public void testBishopMarkSpots() {
        bishop.calculateEatableSpots(bishop.getRow(), bishop.getColumn(), board);

        assertTrue(board[0][0]);
        assertTrue(board[0][4]);
        assertTrue(board[1][1]);
        assertTrue(board[1][3]);
        assertTrue(board[3][1]);
        assertTrue(board[3][3]);
        assertTrue(board[4][0]);
        assertTrue(board[4][4]);

        assertFalse(board[0][1]);
        assertFalse(board[0][2]);
        assertFalse(board[0][3]);
        assertFalse(board[1][0]);
        assertFalse(board[1][2]);
        assertFalse(board[1][4]);
        assertFalse(board[2][0]);
        assertFalse(board[2][1]);
        assertFalse(board[2][3]);
        assertFalse(board[2][4]);
        assertFalse(board[3][0]);
        assertFalse(board[3][2]);
        assertFalse(board[3][4]);
        assertFalse(board[4][1]);
        assertFalse(board[4][2]);
        assertFalse(board[4][3]);
    }

    public void testBishopCanTakeSpotSuccess() {
        List<Piece> pieces = new ArrayList<Piece>();
        Bishop tmp = new Bishop();
        tmp.setRow(2);
        tmp.setColumn(1);
        pieces.add(tmp);
        assertTrue(bishop.canPieceTakeSpot(bishop.getRow(), bishop.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(1);
        tmp.setColumn(2);
        pieces.add(tmp);
        assertTrue(bishop.canPieceTakeSpot(bishop.getRow(), bishop.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(2);
        tmp.setColumn(3);
        pieces.add(tmp);
        assertTrue(bishop.canPieceTakeSpot(bishop.getRow(), bishop.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(3);
        tmp.setColumn(2);
        pieces.add(tmp);
        assertTrue(bishop.canPieceTakeSpot(bishop.getRow(), bishop.getColumn(), board, pieces));
    }

    public void testBishopCanTakeSpotFailure() {
        List<Piece> pieces = new ArrayList<Piece>();
        Bishop tmp = new Bishop();
        tmp.setRow(1);
        tmp.setColumn(1);
        pieces.add(tmp);
        assertFalse(bishop.canPieceTakeSpot(bishop.getRow(), bishop.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(1);
        tmp.setColumn(3);
        pieces.add(tmp);
        assertFalse(bishop.canPieceTakeSpot(bishop.getRow(), bishop.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(3);
        tmp.setColumn(1);
        pieces.add(tmp);
        assertFalse(bishop.canPieceTakeSpot(bishop.getRow(), bishop.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(3);
        tmp.setColumn(3);
        pieces.add(tmp);
        assertFalse(bishop.canPieceTakeSpot(bishop.getRow(), bishop.getColumn(), board, pieces));
    }
}