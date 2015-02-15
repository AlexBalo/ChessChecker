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
    public void calculateEatableSpots(int rowPosition, int columnPosition, boolean[][] boardSpots) {
        executeBoardOperations(rowPosition, columnPosition, boardSpots);
    }

    @Override
    public boolean canPieceTakeSpot(int rowPosition, int columnPosition, boolean[][] boardSpots, List<Piece> placesPieces) {
        return canPieceTakeSpotThisSpot(rowPosition, columnPosition, boardSpots, placesPieces);
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

    private void executeBoardOperations(int rowPosition, int columnPosition, boolean[][] boardSpots) {
        int rowsLength = boardSpots.length;
        int columnsLength = boardSpots[0].length;

        boolean validLeftColumn = columnPosition - 1 >= 0;
        boolean validTopRow = rowPosition - 1 >= 0;
        boolean validRightColumn = columnPosition + 1 < columnsLength;
        boolean validBottomRow = rowPosition + 1 < rowsLength;

        // Top left
        if (validLeftColumn && validTopRow) {
            markSpotAsTaken(rowPosition - 1, columnPosition - 1, boardSpots);
        }

        // Top
        if (validTopRow) {
            markSpotAsTaken(rowPosition - 1, columnPosition, boardSpots);
        }

        // Top right
        if (validTopRow && validRightColumn) {
            markSpotAsTaken(rowPosition - 1, columnPosition + 1, boardSpots);
        }

        // Right
        if (validRightColumn) {
            markSpotAsTaken(rowPosition, columnPosition + 1, boardSpots);
        }

        // Bottom right
        if (validBottomRow && validRightColumn) {
            markSpotAsTaken(rowPosition + 1, columnPosition + 1, boardSpots);
        }

        // Bottom
        if (validBottomRow) {
            markSpotAsTaken(rowPosition + 1, columnPosition, boardSpots);
        }

        // Bottom left
        if (validLeftColumn && validBottomRow) {
            markSpotAsTaken(rowPosition + 1, columnPosition - 1, boardSpots);
        }

        // Left
        if (validLeftColumn) {
            markSpotAsTaken(rowPosition, columnPosition - 1, boardSpots);
        }
    }

    private boolean canPieceTakeSpotThisSpot(int rowPosition, int columnPosition, boolean[][] boardSpots,
                                             List<Piece> placedPieces) {
        if (placedPieces.isEmpty()) {
            return true;
        }

        int rowsLength = boardSpots.length;
        int columnsLength = boardSpots[0].length;

        boolean validLeftColumn = columnPosition - 1 >= 0;
        boolean validTopRow = rowPosition - 1 >= 0;
        boolean validRightColumn = columnPosition + 1 < columnsLength;
        boolean validBottomRow = rowPosition + 1 < rowsLength;
        boolean canTakeSpot = true;

        // Top left
        if (validLeftColumn && validTopRow) {
            canTakeSpot = canTakeSpot(rowPosition - 1, columnPosition - 1, placedPieces);
        }

        // Top
        if (canTakeSpot && validTopRow) {
            canTakeSpot = canTakeSpot(rowPosition - 1, columnPosition, placedPieces);
        }

        // Top right
        if (canTakeSpot && validTopRow && validRightColumn) {
            canTakeSpot = canTakeSpot(rowPosition - 1, columnPosition + 1, placedPieces);
        }

        // Right
        if (canTakeSpot && validRightColumn) {
            canTakeSpot = canTakeSpot(rowPosition, columnPosition + 1, placedPieces);
        }

        // Bottom right
        if (canTakeSpot && validBottomRow && validRightColumn) {
            canTakeSpot = canTakeSpot(rowPosition + 1, columnPosition + 1, placedPieces);
        }

        // Bottom
        if (canTakeSpot && validBottomRow) {
            canTakeSpot = canTakeSpot(rowPosition + 1, columnPosition, placedPieces);
        }

        // Bottom left
        if (canTakeSpot && validLeftColumn && validBottomRow) {
            canTakeSpot = canTakeSpot(rowPosition + 1, columnPosition - 1, placedPieces);
        }

        // Left
        if (canTakeSpot && validLeftColumn) {
            canTakeSpot = canTakeSpot(rowPosition, columnPosition - 1, placedPieces);
        }
        return canTakeSpot;
    }

    private void markSpotAsTaken(int rowPosition, int columnPosition, boolean[][] boardSpots) {
        boardSpots[rowPosition][columnPosition] = true;
    }

    private boolean canTakeSpot(int rowPosition, int columnPosition, List<Piece> placesPieces) {
        for (Piece piece : placesPieces) {
            if (piece.getRow() == rowPosition && piece.getColumn() == columnPosition) {
                return false;
            }
        }
        return true;
    }
}
