package models;

import java.util.List;

/**
 * Created by alessandro.balocco
 * This is an abstract class. It is extended by every piece and contains method to calculate
 * piece information
 */
public abstract class Piece {

    /**
     * The row index of the piece
     */
    protected int row;
    /**
     * The column index of the piece
     */
    protected int column;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Get the identifier of the piece
     *
     * @return a String that identify the piece on the board
     */
    public abstract String getInitial();

    /**
     * This method calculates the cells that can be eaten by the piece. It is used to determine
     * whether or not a piece can be placed on a certain spot
     *
     * @param rowIndex    the index of the row of the considered piece
     * @param columnIndex the index of the column of the considered piece
     * @param boardSpots  a boolean matrix indicating whether spots are free or occupied
     */
    public abstract void calculateEatableSpots(int rowIndex, int columnIndex, boolean[][] boardSpots);

    /**
     * This method calculates if a piece can take a specific spot depending on the pieces that are
     * already places on the boards and a boolean matrix of availabilities
     *
     * @param rowIndex      the index of the row the be evaluated
     * @param columnIndex   the index of the column the be evaluated
     * @param boardSpots    the matrix of available spots
     * @param placesPieces  the already places Pieces
     *
     * @return true if the suggested spot is available and can be occupied
     */
    public abstract boolean canPieceTakeSpot(int rowIndex, int columnIndex, boolean[][] boardSpots, List<Piece> placesPieces);
}
