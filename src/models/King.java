package models;

import utils.BoardUtils;

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
            BoardUtils.markSpotAsTaken(rowIndex - 1, columnIndex - 1, boardSpots);
        }

        // Top
        if (validTopRow) {
            BoardUtils.markSpotAsTaken(rowIndex - 1, columnIndex, boardSpots);
        }

        // Top right
        if (validTopRow && validRightColumn) {
            BoardUtils.markSpotAsTaken(rowIndex - 1, columnIndex + 1, boardSpots);
        }

        // Right
        if (validRightColumn) {
            BoardUtils.markSpotAsTaken(rowIndex, columnIndex + 1, boardSpots);
        }

        // Bottom right
        if (validBottomRow && validRightColumn) {
            BoardUtils.markSpotAsTaken(rowIndex + 1, columnIndex + 1, boardSpots);
        }

        // Bottom
        if (validBottomRow) {
            BoardUtils.markSpotAsTaken(rowIndex + 1, columnIndex, boardSpots);
        }

        // Bottom left
        if (validLeftColumn && validBottomRow) {
            BoardUtils.markSpotAsTaken(rowIndex + 1, columnIndex - 1, boardSpots);
        }

        // Left
        if (validLeftColumn) {
            BoardUtils.markSpotAsTaken(rowIndex, columnIndex - 1, boardSpots);
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

        // Top left
        if (validLeftColumn && validTopRow) {
            if (!BoardUtils.canPieceTakeSpot(rowIndex - 1, columnIndex - 1, placedPieces)) {
                return false;
            }
        }

        // Top
        if (validTopRow) {
            if (!BoardUtils.canPieceTakeSpot(rowIndex - 1, columnIndex, placedPieces)) {
                return false;
            }
        }

        // Top right
        if (validTopRow && validRightColumn) {
            if (!BoardUtils.canPieceTakeSpot(rowIndex - 1, columnIndex + 1, placedPieces)) {
                return false;
            }
        }

        // Right
        if (validRightColumn) {
            if (!BoardUtils.canPieceTakeSpot(rowIndex, columnIndex + 1, placedPieces)) {
                return false;
            }
        }

        // Bottom right
        if (validBottomRow && validRightColumn) {
            if (!BoardUtils.canPieceTakeSpot(rowIndex + 1, columnIndex + 1, placedPieces)) {
                return false;
            }
        }

        // Bottom
        if (validBottomRow) {
            if (!BoardUtils.canPieceTakeSpot(rowIndex + 1, columnIndex, placedPieces)) {
                return false;
            }
        }

        // Bottom left
        if (validLeftColumn && validBottomRow) {
            if (!BoardUtils.canPieceTakeSpot(rowIndex + 1, columnIndex - 1, placedPieces)) {
                return false;
            }
        }

        // Left
        if (validLeftColumn) {
            if (!BoardUtils.canPieceTakeSpot(rowIndex, columnIndex - 1, placedPieces)) {
                return false;
            }
        }
        return true;
    }
}
