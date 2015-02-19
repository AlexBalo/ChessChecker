package tests;

import junit.framework.TestCase;
import main.models.Knight;
import main.models.Piece;

import java.util.ArrayList;
import java.util.List;

public class KnightTest extends TestCase {

    private boolean[][] board;
    private Knight knight;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        board = new boolean[5][5];
        knight = new Knight();
        knight.setRow(2);
        knight.setColumn(2);
    }

    public void testKnightMarkSpots() {
        knight.calculateEatableSpots(knight.getRow(), knight.getColumn(), board);

        assertTrue(board[0][1]);
        assertTrue(board[0][3]);
        assertTrue(board[1][0]);
        assertTrue(board[1][4]);
        assertTrue(board[3][0]);
        assertTrue(board[3][4]);
        assertTrue(board[4][1]);
        assertTrue(board[4][3]);

        assertFalse(board[0][0]);
        assertFalse(board[0][2]);
        assertFalse(board[0][4]);
        assertFalse(board[1][1]);
        assertFalse(board[1][2]);
        assertFalse(board[1][3]);
        assertFalse(board[2][0]);
        assertFalse(board[2][1]);
        assertFalse(board[2][3]);
        assertFalse(board[2][4]);
        assertFalse(board[3][1]);
        assertFalse(board[3][2]);
        assertFalse(board[3][3]);
        assertFalse(board[4][0]);
        assertFalse(board[4][2]);
        assertFalse(board[4][4]);
    }

    public void testKnightCanTakeSpotSuccess() {
        List<Piece> pieces = new ArrayList<Piece>();
        Piece tmp = new Knight();
        tmp.setRow(2);
        tmp.setColumn(1);
        pieces.add(tmp);
        assertTrue(knight.canPieceTakeSpot(knight.getRow(), knight.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(1);
        tmp.setColumn(2);
        pieces.add(tmp);
        assertTrue(knight.canPieceTakeSpot(knight.getRow(), knight.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(2);
        tmp.setColumn(3);
        pieces.add(tmp);
        assertTrue(knight.canPieceTakeSpot(knight.getRow(), knight.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(3);
        tmp.setColumn(2);
        pieces.add(tmp);
        assertTrue(knight.canPieceTakeSpot(knight.getRow(), knight.getColumn(), board, pieces));
    }

    public void testKnightCanTakeSpotFailure() {
        List<Piece> pieces = new ArrayList<Piece>();
        Piece tmp = new Knight();
        tmp.setRow(0);
        tmp.setColumn(1);
        pieces.add(tmp);
        assertFalse(knight.canPieceTakeSpot(knight.getRow(), knight.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(1);
        tmp.setColumn(4);
        pieces.add(tmp);
        assertFalse(knight.canPieceTakeSpot(knight.getRow(), knight.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(4);
        tmp.setColumn(3);
        pieces.add(tmp);
        assertFalse(knight.canPieceTakeSpot(knight.getRow(), knight.getColumn(), board, pieces));

        pieces = new ArrayList<Piece>();
        tmp.setRow(3);
        tmp.setColumn(0);
        pieces.add(tmp);
        assertFalse(knight.canPieceTakeSpot(knight.getRow(), knight.getColumn(), board, pieces));
    }
}