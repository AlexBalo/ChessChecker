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
    public void calculateEatableSpots(int rowPosition, int columnPosition, boolean[][] boardSpots) {
        executeBoardOperations(rowPosition, columnPosition, boardSpots);
    }

    @Override
    public boolean canPieceTakeSpot(int rowPosition, int columnPosition, boolean[][] boardSpots, List<Piece> placesPieces) {
        return canPieceTakeThisSpot(rowPosition, columnPosition, boardSpots, placesPieces);
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

    private void executeBoardOperations(int rowPosition, int columnPosition, boolean[][] boardSpots) {
        int rowsLength = boardSpots.length;
        int columnsLength = boardSpots[0].length;

        // Left
        for (int i = 0; i < columnPosition; i++) {
            markSpotAsTaken(rowPosition, i, boardSpots);
        }

        // Top
        for (int i = 0; i < rowPosition; i++) {
            markSpotAsTaken(i, columnPosition, boardSpots);
        }

        // Right
        for (int i = columnPosition; i < columnsLength; i++) {
            markSpotAsTaken(rowPosition, i, boardSpots);
        }

        // Bottom
        for (int i = rowPosition; i < rowsLength; i++) {
            markSpotAsTaken(i, columnPosition, boardSpots);
        }
    }

    private boolean canPieceTakeThisSpot(int rowPosition, int columnPosition, boolean[][] boardSpots,
                                         List<Piece> placedPieces) {
        if (placedPieces.isEmpty()) {
            return true;
        }

        int rowsLength = boardSpots.length;
        int columnsLength = boardSpots[0].length;
        boolean canTakeSpot = true;

        // Left
        for (int i = 0; i < columnPosition; i++) {
            canTakeSpot = canTakeSpot(rowPosition, i, placedPieces);
            if (!canTakeSpot) {
                return false;
            }
        }

        // Top
        for (int i = 0; i < rowPosition; i++) {
            canTakeSpot = canTakeSpot(i, columnPosition, placedPieces);
            if (!canTakeSpot) {
                return false;
            }
        }

        // Right
        for (int i = columnPosition; i < columnsLength; i++) {
            canTakeSpot = canTakeSpot(rowPosition, i, placedPieces);
            if (!canTakeSpot) {
                return false;
            }
        }

        // Bottom
        for (int i = rowPosition; i < rowsLength; i++) {
            canTakeSpot = canTakeSpot(i, columnPosition, placedPieces);
            if (!canTakeSpot) {
                return false;
            }
        }
        return true;
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
