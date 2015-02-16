package models;

import java.util.List;

/**
 * Created by alessandro.balocco
 */
public class Knight extends Piece {

    public Knight() {

    }

    @Override
    public String getInitial() {
        return " N ";
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
        if (obj instanceof Knight) {
            Knight that = (Knight) obj;
            return (row == that.row && column == that.column);
        }
        return false;
    }

    private void executeBoardOperations(int rowIndex, int columnIndex, boolean[][] boardSpots) {
        int rowsLength = boardSpots.length;
        int columnsLength = boardSpots[0].length;

        boolean validLeftColumn = columnIndex - 2 >= 0;
        boolean validTopRow = rowIndex - 2 >= 0;
        boolean validRightColumn = columnIndex + 2 < columnsLength;
        boolean validBottomRow = rowIndex + 2 < rowsLength;

        // Left
        if (validLeftColumn) {
            if (rowIndex > 0) {
                markSpotAsTaken(rowIndex - 1, columnIndex - 2, boardSpots);
            }
            if (rowIndex < rowsLength - 2) {
                markSpotAsTaken(rowIndex + 1, columnIndex - 2, boardSpots);
            }
        }

        // Top
        if (validTopRow) {
            if (columnIndex > 0) {
                markSpotAsTaken(rowIndex - 2, columnIndex - 1, boardSpots);
            }
            if (columnIndex < columnsLength - 2) {
                markSpotAsTaken(rowIndex - 2, columnIndex + 1, boardSpots);
            }
        }

        // Right
        if (validRightColumn) {
            if (rowIndex > 0) {
                markSpotAsTaken(rowIndex - 1, columnIndex + 2, boardSpots);
            }
            if (rowIndex < rowsLength - 2) {
                markSpotAsTaken(rowIndex + 1, columnIndex + 2, boardSpots);
            }
        }

        // Bottom
        if (validBottomRow) {
            if (columnIndex > 0) {
                markSpotAsTaken(rowIndex + 2, columnIndex - 1, boardSpots);
            }
            if (columnIndex < columnsLength - 2) {
                markSpotAsTaken(rowIndex + 2, columnIndex + 1, boardSpots);
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
        boolean canTakeSpot = true;

        // Left
        if (validLeftColumn) {
            if (rowIndex > 0) {
                canTakeSpot = canTakeSpot(rowIndex - 1, columnIndex - 2, placedPieces);
            }
            if (canTakeSpot && rowIndex < rowsLength - 2) {
                canTakeSpot = canTakeSpot(rowIndex + 1, columnIndex - 2, placedPieces);
            }
        }

        // Top
        if (canTakeSpot && validTopRow) {
            if (columnIndex > 0) {
                canTakeSpot = canTakeSpot(rowIndex - 2, columnIndex - 1, placedPieces);
            }
            if (canTakeSpot && columnIndex < columnsLength - 2) {
                canTakeSpot = canTakeSpot(rowIndex - 2, columnIndex + 1, placedPieces);
            }
        }

        // Right
        if (canTakeSpot && validRightColumn) {
            if (rowIndex > 0) {
                canTakeSpot = canTakeSpot(rowIndex - 1, columnIndex + 2, placedPieces);
            }
            if (canTakeSpot && rowIndex < rowsLength - 2) {
                canTakeSpot = canTakeSpot(rowIndex + 1, columnIndex + 2, placedPieces);
            }
        }

        // Bottom
        if (canTakeSpot && validBottomRow) {
            if (columnIndex > 0) {
                canTakeSpot = canTakeSpot(rowIndex + 2, columnIndex - 1, placedPieces);
            }
            if (canTakeSpot && columnIndex < columnsLength - 2) {
                canTakeSpot = canTakeSpot(rowIndex + 2, columnIndex + 1, placedPieces);
            }
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
