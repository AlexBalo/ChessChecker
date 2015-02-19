package models;

import utils.BoardUtils;

import java.util.List;

/**
 * Created by alessandro.balocco
 * This class is the implementation of the KNIGHT piece
 */
public class Knight extends Piece {

    public Knight() {

    }

    @Override
    public String getIdentifier() {
        return " N ";
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

        boolean validLeftColumn = columnIndex - 2 >= 0;
        boolean validTopRow = rowIndex - 2 >= 0;
        boolean validRightColumn = columnIndex + 2 < columnsLength;
        boolean validBottomRow = rowIndex + 2 < rowsLength;

        // Left
        if (validLeftColumn) {
            if (rowIndex > 0) {
                BoardUtils.markSpotAsTaken(rowIndex - 1, columnIndex - 2, boardSpots);
            }
            if (rowIndex < rowsLength - 1) {
                BoardUtils.markSpotAsTaken(rowIndex + 1, columnIndex - 2, boardSpots);
            }
        }

        // Top
        if (validTopRow) {
            if (columnIndex > 0) {
                BoardUtils.markSpotAsTaken(rowIndex - 2, columnIndex - 1, boardSpots);
            }
            if (columnIndex < columnsLength - 1) {
                BoardUtils.markSpotAsTaken(rowIndex - 2, columnIndex + 1, boardSpots);
            }
        }

        // Right
        if (validRightColumn) {
            if (rowIndex > 0) {
                BoardUtils.markSpotAsTaken(rowIndex - 1, columnIndex + 2, boardSpots);
            }
            if (rowIndex < rowsLength - 1) {
                BoardUtils.markSpotAsTaken(rowIndex + 1, columnIndex + 2, boardSpots);
            }
        }

        // Bottom
        if (validBottomRow) {
            if (columnIndex > 0) {
                BoardUtils.markSpotAsTaken(rowIndex + 2, columnIndex - 1, boardSpots);
            }
            if (columnIndex < columnsLength - 1) {
                BoardUtils.markSpotAsTaken(rowIndex + 2, columnIndex + 1, boardSpots);
            }
        }
    }

    private boolean canPieceTakeThisSpot(int rowIndex, int columnIndex, boolean[][] boardSpots,
                                         List<Piece> placedPieces) {
        if (placedPieces.isEmpty()) {
            return true;
        }

        int rowsLength = boardSpots.length;
        int columnsLength = boardSpots[0].length;

        boolean validLeftColumn = columnIndex - 2 >= 0;
        boolean validTopRow = rowIndex - 2 >= 0;
        boolean validRightColumn = columnIndex + 2 < columnsLength;
        boolean validBottomRow = rowIndex + 2 < rowsLength;

        // Left
        if (validLeftColumn) {
            if (rowIndex > 0) {
                if (!BoardUtils.canPieceTakeSpot(rowIndex - 1, columnIndex - 2, placedPieces)) {
                    return false;
                }
            }
            if (rowIndex < rowsLength - 1) {
                if (!BoardUtils.canPieceTakeSpot(rowIndex + 1, columnIndex - 2, placedPieces)) {
                    return false;
                }
            }
        }

        // Top
        if (validTopRow) {
            if (columnIndex > 0) {
                if (!BoardUtils.canPieceTakeSpot(rowIndex - 2, columnIndex - 1, placedPieces)) {
                    return false;
                }
            }
            if (columnIndex < columnsLength - 1) {
                if (!BoardUtils.canPieceTakeSpot(rowIndex - 2, columnIndex + 1, placedPieces)) {
                    return false;
                }
            }
        }

        // Right
        if (validRightColumn) {
            if (rowIndex > 0) {
                if (!BoardUtils.canPieceTakeSpot(rowIndex - 1, columnIndex + 2, placedPieces)) {
                    return false;
                }
            }
            if (rowIndex < rowsLength - 1) {
                if (!BoardUtils.canPieceTakeSpot(rowIndex + 1, columnIndex + 2, placedPieces)) {
                    return false;
                }
            }
        }

        // Bottom
        if (validBottomRow) {
            if (columnIndex > 0) {
                if (!BoardUtils.canPieceTakeSpot(rowIndex + 2, columnIndex - 1, placedPieces)) {
                    return false;
                }
            }
            if (columnIndex < columnsLength - 1) {
                if (!BoardUtils.canPieceTakeSpot(rowIndex + 2, columnIndex + 1, placedPieces)) {
                    return false;
                }
            }
        }
        return true;
    }
}
