package models;

import java.util.List;

/**
 * Created by alessandro.balocco
 */
public class King extends Piece {

    public King() {

    }

    @Override
    public String getInitial() {
        return " K ";
    }

    @Override
    public void calculateEatableSpots(int rowIndex, int columnIndex, boolean[][] boardSpots) {
        executeBoardOperations(rowIndex, columnIndex, boardSpots);
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
        if (obj instanceof King) {
            King that = (King) obj;
            return (row == that.row && column == that.column);
        }
        return false;
    }

    private void executeBoardOperations(int rowIndex, int columnIndex, boolean[][] boardSpots) {
        int rowsLength = boardSpots.length;
        int columnsLength = boardSpots[0].length;

        boolean validLeftColumn = columnIndex - 1 >= 0;
        boolean validTopRow = rowIndex - 1 >= 0;
        boolean validRightColumn = columnIndex + 1 < columnsLength;
        boolean validBottomRow = rowIndex + 1 < rowsLength;

        // Top left
        if (validLeftColumn && validTopRow) {
            markSpotAsTaken(rowIndex - 1, columnIndex - 1, boardSpots);
        }

        // Top
        if (validTopRow) {
            markSpotAsTaken(rowIndex - 1, columnIndex, boardSpots);
        }

        // Top right
        if (validTopRow && validRightColumn) {
            markSpotAsTaken(rowIndex - 1, columnIndex + 1, boardSpots);
        }

        // Right
        if (validRightColumn) {
            markSpotAsTaken(rowIndex, columnIndex + 1, boardSpots);
        }

        // Bottom right
        if (validBottomRow && validRightColumn) {
            markSpotAsTaken(rowIndex + 1, columnIndex + 1, boardSpots);
        }

        // Bottom
        if (validBottomRow) {
            markSpotAsTaken(rowIndex + 1, columnIndex, boardSpots);
        }

        // Bottom left
        if (validLeftColumn && validBottomRow) {
            markSpotAsTaken(rowIndex + 1, columnIndex - 1, boardSpots);
        }

        // Left
        if (validLeftColumn) {
            markSpotAsTaken(rowIndex, columnIndex - 1, boardSpots);
        }
    }

    private boolean canPieceTakeThisSpot(int rowIndex, int columnIndex, boolean[][] boardSpots,
                                         List<Piece> placedPieces) {
        if (placedPieces.isEmpty()) {
            return true;
        }

        int rowsLength = boardSpots.length;
        int columnsLength = boardSpots[0].length;

        boolean validLeftColumn = columnIndex - 1 >= 0;
        boolean validTopRow = rowIndex - 1 >= 0;
        boolean validRightColumn = columnIndex + 1 < columnsLength;
        boolean validBottomRow = rowIndex + 1 < rowsLength;
        boolean canTakeSpot = true;

        // Top left
        if (validLeftColumn && validTopRow) {
            canTakeSpot = canTakeSpot(rowIndex - 1, columnIndex - 1, placedPieces);
        }

        // Top
        if (canTakeSpot && validTopRow) {
            canTakeSpot = canTakeSpot(rowIndex - 1, columnIndex, placedPieces);
        }

        // Top right
        if (canTakeSpot && validTopRow && validRightColumn) {
            canTakeSpot = canTakeSpot(rowIndex - 1, columnIndex + 1, placedPieces);
        }

        // Right
        if (canTakeSpot && validRightColumn) {
            canTakeSpot = canTakeSpot(rowIndex, columnIndex + 1, placedPieces);
        }

        // Bottom right
        if (canTakeSpot && validBottomRow && validRightColumn) {
            canTakeSpot = canTakeSpot(rowIndex + 1, columnIndex + 1, placedPieces);
        }

        // Bottom
        if (canTakeSpot && validBottomRow) {
            canTakeSpot = canTakeSpot(rowIndex + 1, columnIndex, placedPieces);
        }

        // Bottom left
        if (canTakeSpot && validLeftColumn && validBottomRow) {
            canTakeSpot = canTakeSpot(rowIndex + 1, columnIndex - 1, placedPieces);
        }

        // Left
        if (canTakeSpot && validLeftColumn) {
            canTakeSpot = canTakeSpot(rowIndex, columnIndex - 1, placedPieces);
        }
        return canTakeSpot;
    }

    private void markSpotAsTaken(int rowIndex, int columnIndex, boolean[][] boardSpots) {
        boardSpots[rowIndex][columnIndex] = true;
    }

    private boolean canTakeSpot(int rowIndex, int columnIndex, List<Piece> placesPieces) {
        for (Piece piece : placesPieces) {
            if (piece.getRow() == rowIndex && piece.getColumn() == columnIndex) {
                return false;
            }
        }
        return true;
    }
}
