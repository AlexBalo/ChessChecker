package models;

import utils.PieceUtils;

import java.util.List;

/**
 * Created by alessandro.balocco
 */
public class King extends Piece {

    public King() {

    }

    @Override
    public String getIdentifier() {
        return " K ";
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
        if (obj instanceof King) {
            King that = (King) obj;
            return (row == that.row && column == that.column);
        }
        return false;
    }

    private void markUnavailableSpots(int rowIndex, int columnIndex, boolean[][] boardSpots) {
        int rowsLength = boardSpots.length;
        int columnsLength = boardSpots[0].length;

        boolean validLeftColumn = columnIndex - 1 >= 0;
        boolean validTopRow = rowIndex - 1 >= 0;
        boolean validRightColumn = columnIndex + 1 < columnsLength;
        boolean validBottomRow = rowIndex + 1 < rowsLength;

        // Top left
        if (validLeftColumn && validTopRow) {
            PieceUtils.markSpotAsTaken(rowIndex - 1, columnIndex - 1, boardSpots);
        }

        // Top
        if (validTopRow) {
            PieceUtils.markSpotAsTaken(rowIndex - 1, columnIndex, boardSpots);
        }

        // Top right
        if (validTopRow && validRightColumn) {
            PieceUtils.markSpotAsTaken(rowIndex - 1, columnIndex + 1, boardSpots);
        }

        // Right
        if (validRightColumn) {
            PieceUtils.markSpotAsTaken(rowIndex, columnIndex + 1, boardSpots);
        }

        // Bottom right
        if (validBottomRow && validRightColumn) {
            PieceUtils.markSpotAsTaken(rowIndex + 1, columnIndex + 1, boardSpots);
        }

        // Bottom
        if (validBottomRow) {
            PieceUtils.markSpotAsTaken(rowIndex + 1, columnIndex, boardSpots);
        }

        // Bottom left
        if (validLeftColumn && validBottomRow) {
            PieceUtils.markSpotAsTaken(rowIndex + 1, columnIndex - 1, boardSpots);
        }

        // Left
        if (validLeftColumn) {
            PieceUtils.markSpotAsTaken(rowIndex, columnIndex - 1, boardSpots);
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
            canTakeSpot = PieceUtils.canTakeSpot(rowIndex - 1, columnIndex - 1, placedPieces);
        }

        // Top
        if (canTakeSpot && validTopRow) {
            canTakeSpot = PieceUtils.canTakeSpot(rowIndex - 1, columnIndex, placedPieces);
        }

        // Top right
        if (canTakeSpot && validTopRow && validRightColumn) {
            canTakeSpot = PieceUtils.canTakeSpot(rowIndex - 1, columnIndex + 1, placedPieces);
        }

        // Right
        if (canTakeSpot && validRightColumn) {
            canTakeSpot = PieceUtils.canTakeSpot(rowIndex, columnIndex + 1, placedPieces);
        }

        // Bottom right
        if (canTakeSpot && validBottomRow && validRightColumn) {
            canTakeSpot = PieceUtils.canTakeSpot(rowIndex + 1, columnIndex + 1, placedPieces);
        }

        // Bottom
        if (canTakeSpot && validBottomRow) {
            canTakeSpot = PieceUtils.canTakeSpot(rowIndex + 1, columnIndex, placedPieces);
        }

        // Bottom left
        if (canTakeSpot && validLeftColumn && validBottomRow) {
            canTakeSpot = PieceUtils.canTakeSpot(rowIndex + 1, columnIndex - 1, placedPieces);
        }

        // Left
        if (canTakeSpot && validLeftColumn) {
            canTakeSpot = PieceUtils.canTakeSpot(rowIndex, columnIndex - 1, placedPieces);
        }
        return canTakeSpot;
    }
}
