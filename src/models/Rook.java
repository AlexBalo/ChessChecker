package models;

import utils.BoardUtils;

import java.util.List;

/**
 * Created by alessandro.balocco
 */
public class Rook extends Piece {

    public Rook() {

    }

    @Override
    public String getIdentifier() {
        return " R ";
    }

    @Override
    public void calculateEatableSpots(int rowIndex, int columnIndex, boolean[][] boardSpots) {
        markUnavailableSpots(rowIndex, columnIndex, boardSpots);
    }

    @Override
    public boolean canPieceTakeSpot(int rowIndex, int columnIndex, boolean[][] boardSpots, List<Piece> placesPieces) {
        return canPieceTakeThisSpot(rowIndex, columnIndex, boardSpots, placesPieces);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Rook) {
            Rook that = (Rook) obj;
            return (row == that.row && column == that.column);
        }
        return false;
    }

    private void markUnavailableSpots(int rowIndex, int columnIndex, boolean[][] boardSpots) {
        int rowsLength = boardSpots.length;
        int columnsLength = boardSpots[0].length;

        // Left
        for (int i = 0; i < columnIndex; i++) {
            BoardUtils.markSpotAsTaken(rowIndex, i, boardSpots);
        }

        // Top
        for (int i = 0; i < rowIndex; i++) {
            BoardUtils.markSpotAsTaken(i, columnIndex, boardSpots);
        }

        // Right
        for (int i = columnIndex; i < columnsLength; i++) {
            BoardUtils.markSpotAsTaken(rowIndex, i, boardSpots);
        }

        // Bottom
        for (int i = rowIndex; i < rowsLength; i++) {
            BoardUtils.markSpotAsTaken(i, columnIndex, boardSpots);
        }
    }

    private boolean canPieceTakeThisSpot(int rowIndex, int columnIndex, boolean[][] boardSpots,
                                         List<Piece> placedPieces) {
        if (placedPieces.isEmpty()) {
            return true;
        }

        int rowsLength = boardSpots.length;
        int columnsLength = boardSpots[0].length;

        // Left
        for (int i = 0; i < columnIndex; i++) {
            if (!BoardUtils.canPieceTakeSpot(rowIndex, i, placedPieces)) {
                return false;
            }
        }

        // Top
        for (int i = 0; i < rowIndex; i++) {
            if (!BoardUtils.canPieceTakeSpot(i, columnIndex, placedPieces)) {
                return false;
            }
        }

        // Right
        for (int i = columnIndex; i < columnsLength; i++) {
            if (!BoardUtils.canPieceTakeSpot(rowIndex, i, placedPieces)) {
                return false;
            }
        }

        // Bottom
        for (int i = rowIndex; i < rowsLength; i++) {
            if (!BoardUtils.canPieceTakeSpot(i, columnIndex, placedPieces)) {
                return false;
            }
        }
        return true;
    }
}
