package main.models;

import main.utils.BoardUtils;

import java.util.List;

/**
 * Created by alessandro.balocco
 * This class is the implementation of the QUEEN piece
 */
public class Queen extends Piece {

    public Queen() {

    }

    @Override
    public String getIdentifier() {
        return " Q ";
    }

    @Override
    public void calculateEatableSpots(int rowIndex, int columnIndex, boolean[][] boardSpots) {
        markUnavailableSpots(rowIndex, columnIndex, boardSpots);
    }

    @Override
    public boolean canPieceTakeSpot(int rowIndex, int columnIndex, boolean[][] boardSpots, List<Piece> placesPieces) {
        return canPieceTakeThisSpot(rowIndex, columnIndex, boardSpots, placesPieces);
    }

    private void markUnavailableSpots(int rowIndex, int columnIndex, boolean[][] boardSpots) {
        int rowsLength = boardSpots.length;
        int columnsLength = boardSpots[0].length;

        // DIAGONAL
        // Left
        int counter = 0;
        for (int i = columnIndex; i >= 0; i--) {
            counter++;
            int newColumnIndex = columnIndex - counter;
            int newRowUpIndex = rowIndex - counter;
            int newRowDownIndex = rowIndex + counter;

            // Left up
            if (newColumnIndex >= 0 && newRowUpIndex >= 0) {
                BoardUtils.markSpotAsTaken(newRowUpIndex, newColumnIndex, boardSpots);
            }

            // Left down
            if (newColumnIndex >= 0 && newRowDownIndex < rowsLength) {
                BoardUtils.markSpotAsTaken(newRowDownIndex, newColumnIndex, boardSpots);
            }
        }

        // Right
        counter = 0;
        for (int i = columnIndex; i < columnsLength; i++) {
            counter++;
            int newColumnIndex = columnIndex + counter;
            int newRowUpIndex = rowIndex - counter;
            int newRowDownIndex = rowIndex + counter;

            // Right up
            if (newColumnIndex < columnsLength && newRowUpIndex >= 0) {
                BoardUtils.markSpotAsTaken(newRowUpIndex, newColumnIndex, boardSpots);
            }

            // Right down
            if (newColumnIndex < columnsLength && newRowDownIndex < rowsLength) {
                BoardUtils.markSpotAsTaken(newRowDownIndex, newColumnIndex, boardSpots);
            }
        }

        // CROSS
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

        // DIAGONAL
        // Left
        int counter = 0;
        for (int i = columnIndex; i >= 0; i--) {
            counter++;
            int newColumnIndex = columnIndex - counter;
            int newRowUpIndex = rowIndex - counter;
            int newRowDownIndex = rowIndex + counter;

            // Left up
            if (newColumnIndex >= 0 && newRowUpIndex >= 0) {
                if (!BoardUtils.canPieceTakeSpot(newRowUpIndex, newColumnIndex, placedPieces)) {
                    return false;
                }
            }

            // Left down
            if (newColumnIndex >= 0 && newRowDownIndex < rowsLength) {
                if (!BoardUtils.canPieceTakeSpot(newRowDownIndex, newColumnIndex, placedPieces)) {
                    return false;
                }
            }
        }

        // Right
        counter = 0;
        for (int i = columnIndex; i < columnsLength; i++) {
            counter++;
            int newColumnIndex = columnIndex + counter;
            int newRowUpIndex = rowIndex - counter;
            int newRowDownIndex = rowIndex + counter;

            // Right up
            if (newColumnIndex < columnsLength && newRowUpIndex >= 0) {
                if (!BoardUtils.canPieceTakeSpot(newRowUpIndex, newColumnIndex, placedPieces)) {
                    return false;
                }
            }

            // Right down
            if (newColumnIndex < columnsLength && newRowDownIndex < rowsLength) {
                if (!BoardUtils.canPieceTakeSpot(newRowDownIndex, newColumnIndex, placedPieces)) {
                    return false;
                }
            }
        }

        // CROSS
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
