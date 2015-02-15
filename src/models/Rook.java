package models;

import java.util.List;

/**
 * Created by alessandro.balocco
 */
public class Rook extends Piece {

    public Rook() {

    }

    @Override
    public String getInitial() {
        return " R ";
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
        if (obj instanceof Rook) {
            Rook that = (Rook) obj;
            return (row == that.row && column == that.column);
        }
        return false;
    }

    private void executeBoardOperations(int rowIndex, int columnIndex, boolean[][] boardSpots) {
        int rowsLength = boardSpots.length;
        int columnsLength = boardSpots[0].length;

        // Left
        for (int i = 0; i < columnIndex; i++) {
            markSpotAsTaken(rowIndex, i, boardSpots);
        }

        // Top
        for (int i = 0; i < rowIndex; i++) {
            markSpotAsTaken(i, columnIndex, boardSpots);
        }

        // Right
        for (int i = columnIndex; i < columnsLength; i++) {
            markSpotAsTaken(rowIndex, i, boardSpots);
        }

        // Bottom
        for (int i = rowIndex; i < rowsLength; i++) {
            markSpotAsTaken(i, columnIndex, boardSpots);
        }
    }

    private boolean canPieceTakeThisSpot(int rowIndex, int columnIndex, boolean[][] boardSpots,
                                         List<Piece> placedPieces) {
        if (placedPieces.isEmpty()) {
            return true;
        }

        int rowsLength = boardSpots.length;
        int columnsLength = boardSpots[0].length;
        boolean canTakeSpot = true;

        // Left
        for (int i = 0; i < columnIndex; i++) {
            canTakeSpot = canTakeSpot(rowIndex, i, placedPieces);
            if (!canTakeSpot) {
                return false;
            }
        }

        // Top
        for (int i = 0; i < rowIndex; i++) {
            canTakeSpot = canTakeSpot(i, columnIndex, placedPieces);
            if (!canTakeSpot) {
                return false;
            }
        }

        // Right
        for (int i = columnIndex; i < columnsLength; i++) {
            canTakeSpot = canTakeSpot(rowIndex, i, placedPieces);
            if (!canTakeSpot) {
                return false;
            }
        }

        // Bottom
        for (int i = rowIndex; i < rowsLength; i++) {
            canTakeSpot = canTakeSpot(i, columnIndex, placedPieces);
            if (!canTakeSpot) {
                return false;
            }
        }
        return true;
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
