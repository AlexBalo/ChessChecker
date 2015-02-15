package models;

import java.util.List;

/**
 * Created by alessandro.balocco
 */
public abstract class Piece {

    protected int row;
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

    public abstract String getInitial();

    public abstract void calculateEatableSpots(int rowPosition, int columnPosition, boolean[][] boardSpots);

    public abstract boolean canPieceTakeSpot(int rowPosition, int columnPosition, boolean[][] boardSpots, List<Piece> placesPieces);
}
