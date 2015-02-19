package tests;

import junit.framework.TestCase;
import main.models.Piece;
import main.models.Queen;

import java.util.ArrayList;
import java.util.List;

public class QueenTest extends TestCase {

    private boolean[][] board;
    private Queen queen;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        board = new boolean[5][5];
        queen = new Queen();
        queen.setRow(2);
        queen.setColumn(2);
    }

    public void testQueenMarkSpots() {
        queen.calculateEatableSpots(queen.getRow(), queen.getColumn(), board);

        assertTrue(board[0][0]);
        assertTrue(board[0][2]);
        assertTrue(board[0][4]);
        assertTrue(board[1][1]);
        assertTrue(board[1][2]);
        assertTrue(board[1][3]);
        assertTrue(board[2][0]);
        assertTrue(board[2][1]);
        assertTrue(board[2][3]);
        assertTrue(board[2][4]);
        assertTrue(board[3][1]);
        assertTrue(board[3][2]);
        assertTrue(board[3][3]);
        assertTrue(board[4][0]);
        assertTrue(board[4][2]);
        assertTrue(board[4][4]);

        assertFalse(board[0][1]);
        assertFalse(board[0][3]);
        assertFalse(board[1][0]);
        assertFalse(board[1][4]);
        assertFalse(board[3][0]);
        assertFalse(board[3][4]);
        assertFalse(board[4][1]);
        assertFalse(board[4][3]);
    }

    public void testQueenCanTakeSpotSuccess() {
        List<Piece> pieces = new ArrayList<Piece>();
        Piece tmp = new Queen();
        tmp.setRow(0);
        tmp.setColumn(1);
        pieces.add(tmp);
        assertTrue(queen.canPieceTakeSpot(queen.getRow(), queen.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(1);
        tmp.setColumn(4);
        pieces.add(tmp);
        assertTrue(queen.canPieceTakeSpot(queen.getRow(), queen.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(3);
        tmp.setColumn(4);
        pieces.add(tmp);
        assertTrue(queen.canPieceTakeSpot(queen.getRow(), queen.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(4);
        tmp.setColumn(1);
        pieces.add(tmp);
        assertTrue(queen.canPieceTakeSpot(queen.getRow(), queen.getColumn(), board, pieces));
    }

    public void testQueenCanTakeSpotFailure() {
        List<Piece> pieces = new ArrayList<Piece>();
        Piece tmp = new Queen();
        tmp.setRow(0);
        tmp.setColumn(0);
        pieces.add(tmp);
        assertFalse(queen.canPieceTakeSpot(queen.getRow(), queen.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(1);
        tmp.setColumn(3);
        pieces.add(tmp);
        assertFalse(queen.canPieceTakeSpot(queen.getRow(), queen.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(2);
        tmp.setColumn(4);
        pieces.add(tmp);
        assertFalse(queen.canPieceTakeSpot(queen.getRow(), queen.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(4);
        tmp.setColumn(0);
        pieces.add(tmp);
        assertFalse(queen.canPieceTakeSpot(queen.getRow(), queen.getColumn(), board, pieces));
    }
}